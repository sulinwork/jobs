package com.sulin.etl;

import com.sulin.common.HBaseUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.util.Calendar;

/**
 * @author sulin
 * @create 2019-10-14 19:58
 */
public class JobDriver {
    private static class JobMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

        private String fileName;
        private MultipleOutputs<Text, NullWritable> multipleOutputs = null;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            multipleOutputs = new MultipleOutputs<>(context);
            //读取文件的名字
            FileSplit inputSplit = (FileSplit) context.getInputSplit();
            String _fileName = inputSplit.getPath().getName();
            fileName = _fileName.substring(0, _fileName.indexOf("."));
        }

        @Override
        protected void cleanup(Context context) throws IOException, InterruptedException {
            multipleOutputs.close();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //

            String line = value.toString();

            String[] jobs = line.split(",");
            //0岗位名称 岗位中不含有关键字的全部过滤掉
            if (jobs.length != 11 || !jobs

















                    [0].toUpperCase().contains(fileName.toUpperCase())) {
                goNoise(value, context);
                return;
            }
            //1公司名字
            //2企业性质
            //3企业人数 为空也不丢掉 10-50人  需要把数据拆成10 50
            int minCompanySize = 0;
            int maxCompanySize = 0;
            if (!"".equals(jobs[3]) && !"null".equals(jobs[3])) {//不是空进行数据切分
                //System.out.println(line);
                if (jobs[3].indexOf("-") != -1) {
                    String[] temp_spilt = jobs[3].substring(0, jobs[3].length() - 1).split("-");
                    minCompanySize = Integer.parseInt(temp_spilt[0]);
                    maxCompanySize = Integer.parseInt(temp_spilt[1]);
                } else {
                    //其他情况  少于50人  大于1000人
                    if (jobs[3].startsWith("少于")) {
                        maxCompanySize = UtilTool.string2Int(jobs[3]);
                    } else {
                        minCompanySize = UtilTool.string2Int(jobs[3]);
                    }
                }
            }
            //4企业所属行业
            //5工作地点
            String workArea = jobs[5].split("-")[0];
            //6薪资 为空的数据为脏数据
            if ("".equals(jobs[6])) {
                goNoise(value, context);
                return;
            }     //同意单位为 w/y
            double minSalary = 0;
            double maxSalary = 0;
            if (jobs[6].endsWith("万/月")) {
                minSalary = Double.parseDouble(jobs[6].replace("万/月", "").split("-")[0]);
                maxSalary = Double.parseDouble(jobs[6].replace("万/月", "").split("-")[1]);
            } else if (jobs[6].endsWith("千/月")) {
                minSalary = Double.parseDouble(jobs[6].replace("千/月", "").split("-")[0]) / 10;
                maxSalary = Double.parseDouble(jobs[6].replace("千/月", "").split("-")[1]) / 10;
            } else if (jobs[6].endsWith("万/年")) {
                minSalary = Double.parseDouble(jobs[6].replace("万/年", "").split("-")[0]) / 12;
                maxSalary = Double.parseDouble(jobs[6].replace("万/年", "").split("-")[1]) / 12;
            }
            //7 工作地点 | 经验要求 | 学历 | 招聘人数 | 发布时间
            if ("".equals(jobs[7]) || "null".equals(jobs[7])) {
                goNoise(value, context);
                return;
            }
            String[] msgType = jobs[7].replace("  ", "").split("\\|");
            String workYearNeed = "无";
            String workEducation = "无";
            String needNum = "无";
            for (String type : msgType) {
                if (type.contains("经验")) {
                    workYearNeed = type;
                }
                if (type.contains("大专") || type.contains("本科") || type.contains("硕士")) {
                    workEducation = type;
                }
                if (type.contains("招") && type.contains("人")) {
                    needNum = type;
                }
            }
            //8时间   往前面补上年份
            Calendar instance = Calendar.getInstance();
            String putData = instance.get(Calendar.YEAR) + "-" + jobs[8];
            //9岗位要求  去掉后面多余采集的数据
            String workRequirements = "";
            if (!"".equals(jobs[9])) {
                StringBuilder sb = new StringBuilder();
                String[] requis = jobs[9].split("\\|");
                for (String requi : requis) {
                    if (requi.charAt(0) >= '0' && requi.charAt(0) <= '9') {
                        sb.append(requi.trim());
                        sb.append("|");
                    } else {
                        break;
                    }
                }
                workRequirements = sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
            }
            //10岗位详细信息地址url

            //写出数据 到 HBase
            String realValue = fileName + "," + jobs[0] + "," + jobs[1] + "," + jobs[2] + "," + minCompanySize + "," + maxCompanySize + "," +
                    jobs[4] + "," + workArea + "," + minSalary + "," + maxSalary + "," + workYearNeed + "," + workEducation + "," +
                    needNum + "," + putData + "," + workRequirements + "," + jobs[10];
//            multipleOutputs.write(Const.NORMAL_DATA_DIR_NAME, new Text(realValue), NullWritable.get(), Const.NORMAL_DATA_DIR_NAME + "/" + Const.NORMAL_DATA_DIR_NAME);
            context.write(new Text(realValue), NullWritable.get());
//
        }

        /**
         * 写出脏数据
         *
         * @param value
         * @throws IOException
         * @throws InterruptedException
         */
        private void goNoise(Text value, Context context) throws IOException, InterruptedException {
            Counter counter = context.getCounter("JobDriver", "Noise");
            counter.increment(1L);
            //保存脏数据
            // multipleOutputs.write(Const.NOISE_DATA_DIR_NAME, value, NullWritable.get(), Const.NOISE_DATA_DIR_NAME + "/" + Const.NOISE_DATA_DIR_NAME);
            return;
        }
    }


    private static class JobCleanReduce extends TableReducer<Text, NullWritable, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            //数据全在key里面  封装成HBase 需要的put 添加数
            String[] jobs = key.toString().split(",");
            System.out.println(HBaseUtil.generateRowKey(jobs[0], jobs[1], jobs[13]));
            //放入rowkey
            Put put = new Put(Bytes.toBytes(HBaseUtil.generateRowKey(jobs[0], jobs[1], jobs[13])));
            //岗位信息
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("jobType"), Bytes.toBytes(jobs[0]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("jobName"), Bytes.toBytes(jobs[1]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("jobArea"), Bytes.toBytes(jobs[7]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("minSalary"), Bytes.toBytes(jobs[8]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("maxSalary"), Bytes.toBytes(jobs[9]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("workYearNeed"), Bytes.toBytes(jobs[10]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("workEducation"), Bytes.toBytes(jobs[11]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("needNum"), Bytes.toBytes(jobs[12]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("putDate"), Bytes.toBytes(jobs[13]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("jobRequirements"), Bytes.toBytes(jobs[14]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("url"), Bytes.toBytes(jobs[15]));
            //公司信息
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("companyName"), Bytes.toBytes(jobs[2]));
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("companyType"), Bytes.toBytes(jobs[3]));
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("minCompanySize"), Bytes.toBytes(jobs[4]));
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("maxCompanySize"), Bytes.toBytes(jobs[5]));
            put.addColumn(Bytes.toBytes("company"), Bytes.toBytes("companyindustry"), Bytes.toBytes(jobs[6]));
            //写入
            context.write(NullWritable.get(), put);
            context.getCounter("JobDriver", "jobSize").increment(1L);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.119.101,192.168.119.102,192.168.119.103");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        Job job = Job.getInstance(conf);

        //设置驱动类
        job.setJarByClass(JobDriver.class);
        job.setMapperClass(JobMapper.class);

        //设置map端输出数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);


        //设置输入输出
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.setInputPaths(job, args[0]);

//        job.setOutputFormatClass(TextOutputFormat.class);
//        FileSystem fs = FileSystem.get(conf);
//        Path path = new Path(args[1]);
//        if (fs.exists(path)) {
//            fs.delete(path, true);
//        }
        //TextOutputFormat.setOutputPath(job, path);
        TableMapReduceUtil.initTableReducerJob("sulin:jobs", JobCleanReduce.class, job);
        //设置脏数据  和  正常数据的保存的位置（多目录输出）
//        MultipleOutputs.addNamedOutput(job, Const.NOISE_DATA_DIR_NAME, TextOutputFormat.class, Text.class, NullWritable.class);
//        MultipleOutputs.addNamedOutput(job, Const.NORMAL_DATA_DIR_NAME, TextOutputFormat.class, Text.class, NullWritable.class);

        System.exit(job.waitForCompletion(true) ? 1 : 0);
    }

}
