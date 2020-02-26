package com.sulin.etl;

import com.sulin.common.HBaseUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapred.TableReduce;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sulin
 * @time 2020-02-20
 * 岗位信息清洗
 */
public class JobInfoCleanDriver {

    public static class JobInfoCleanMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] data = value.toString().split(Const.SPILT_CHARACTER);
            //装载成List 方便写出数据
            List<String> dataList = new ArrayList<>();
            for (String datum : data) {
                dataList.add(datum);
            }
            if (data.length != Const.DATA_LENGTH) {
                saveNoiseData(value);
                return;
            }
            //对公司的人数规模清洗
            int minCompanySize = 0;
            int maxCompanySize = 0;
            if (!"".equals(data[Const.COMPANY_SIZE_INDEX]) && !"null".equals(data[Const.COMPANY_SIZE_INDEX])) {//不是空进行数据切分
                //System.out.println(line);
                if (data[Const.COMPANY_SIZE_INDEX].indexOf("-") != -1) {
                    String[] temp_spilt = data[Const.COMPANY_SIZE_INDEX].substring(0, data[Const.COMPANY_SIZE_INDEX].length() - 1).split("-");
                    minCompanySize = Integer.parseInt(temp_spilt[0]);
                    maxCompanySize = Integer.parseInt(temp_spilt[1]);
                } else {
                    //其他情况  少于50人  大于1000人
                    if (data[Const.COMPANY_SIZE_INDEX].startsWith("少于")) {
                        maxCompanySize = UtilTool.string2Int(data[Const.COMPANY_SIZE_INDEX]);
                    } else {
                        minCompanySize = UtilTool.string2Int(data[Const.COMPANY_SIZE_INDEX]);
                    }
                }
            }
            //工作地点
            String city = data[Const.CITY_INDEX].split("-")[0];
            //薪资 为空的数据为脏数据
            if ("".equals(data[Const.SALARY_INDEX])) {
                saveNoiseData(value);
                return;
            }
            //薪资单位不同意 需要清洗成统一的万/元
            Double[] salarys = UtilTool.calculateSalary(data[Const.SALARY_INDEX]);
            double minSalary = salarys[0];
            double maxSalary = salarys[1];

            //发布时间 时间没有年 补上
            System.out.println(data[Const.TIME_INDEX]);
            String time = UtilTool.perfectTime(data[Const.TIME_INDEX]);

            dataList.set(Const.COMPANY_SIZE_INDEX, minCompanySize + "");
            dataList.add(Const.COMPANY_SIZE_INDEX + 1, maxCompanySize + "");

            dataList.set(Const.CITY_INDEX, city);

            dataList.set(Const.SALARY_INDEX, minSalary + "");
            dataList.add(Const.SALARY_INDEX + 1, maxSalary + "");

            dataList.set(Const.TIME_INDEX, time);
            String joinStr = String.join(Const.SPILT_CHARACTER, dataList);
            context.write(new Text(joinStr), NullWritable.get());
        }

        /***
         * 写出脏数据
         * @param text
         */
        public void saveNoiseData(Text text) {
            //TODO 写出脏数据
            System.out.println(text.toString());
        }
    }

    public static class JobInfoCleanReduce extends TableReducer<Text, NullWritable, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            //数据全在key里面  封装成HBase 需要的put 添加数
            String[] jobs = key.toString().split(Const.SPILT_CHARACTER);
            //放入rowkey
            Put put = new Put(Bytes.toBytes(HBaseUtil.generateRowKey(jobs[Const.KEY_INDEX], jobs[Const.NAME_INDEX], jobs[Const.TIME_INDEX])));
            //岗位信息
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("key"), Bytes.toBytes(jobs[Const.KEY_INDEX]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(jobs[Const.NAME_INDEX]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("city"), Bytes.toBytes(jobs[Const.CITY_INDEX]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("year"), Bytes.toBytes(jobs[Const.YEAR_INDEX]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("edu"), Bytes.toBytes(jobs[Const.EDU_INDEX]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("number"), Bytes.toBytes(jobs[Const.NUMBER_INDEX]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("time"), Bytes.toBytes(jobs[Const.TIME_INDEX]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("minSalary"), Bytes.toBytes(jobs[Const.SALARY_INDEX]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("maxSalary"), Bytes.toBytes(jobs[Const.SALARY_INDEX + 1]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("job_url"), Bytes.toBytes(jobs[Const.JOB_URL_INDEX + 1]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("job_info"), Bytes.toBytes(jobs[Const.JOB_INFO_INDEX + 1]));
            //公司信息
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("company_name"), Bytes.toBytes(jobs[Const.COMPANY_NAME_INDEX + 1]));
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("company_field"), Bytes.toBytes(jobs[Const.COMPANY_FIELD_INDEX + 1]));
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("minCompanySize"), Bytes.toBytes(jobs[Const.COMPANY_SIZE_INDEX + 1]));
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("maxCompanySize"), Bytes.toBytes(jobs[Const.COMPANY_SIZE_INDEX + 2]));
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("company_type"), Bytes.toBytes(jobs[Const.COMPANY_TYPE_INDEX + 2]));

            //写入
            context.write(NullWritable.get(), put);
            context.getCounter("JobDriver", "jobSize").increment(1L);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("param error");
            return;
        }
        Configuration configuration = new Configuration();
        //配置zookeeper的节点
        configuration.set("hbase.zookeeper.quorum", "192.168.229.101,192.168.229.102,192.168.229.103");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        Job job = Job.getInstance(configuration);
        //设置驱动类
        job.setJarByClass(JobInfoCleanDriver.class);
        job.setMapperClass(JobInfoCleanMapper.class);

        //设置map端输出数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        //设置输入路径和输出路径
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.setInputPaths(job, args[0]);

        //初始化输出到HBase
        TableMapReduceUtil.initTableReducerJob("sulin:job_info", JobInfoCleanReduce.class, job);

        //提交任务
        job.waitForCompletion(true);
    }
}
