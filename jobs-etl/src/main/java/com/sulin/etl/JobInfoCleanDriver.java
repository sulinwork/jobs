package com.sulin.etl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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

            //dataList.remove(Const.COMPANY_SIZE_INDEX);
            dataList.set(Const.COMPANY_SIZE_INDEX, minCompanySize + "");
            dataList.add(Const.COMPANY_SIZE_INDEX + 1, maxCompanySize + "");

            dataList.set(Const.CITY_INDEX, city);

//            dataList.remove(Const.SALARY_INDEX);
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

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("param error");
            return;
        }
        Configuration configuration = new Configuration();
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

        job.setOutputFormatClass(TextOutputFormat.class);
        Path path = new Path(args[1]);
        FileSystem fs = FileSystem.get(configuration);
        if (fs.exists(path)) {
            fs.delete(path, true);
        }
        TextOutputFormat.setOutputPath(job, path);

        //提交任务
        job.waitForCompletion(true);
    }
}
