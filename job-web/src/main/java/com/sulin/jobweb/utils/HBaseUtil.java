//package com.sulin.jobweb.utils;
//
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author sulin
// * @create 2019-08-28 16:48
// */
//public class HBaseUtil {
//    private Connection conn = null;
//    private Admin admin = null;
//    private final String tableName = "sulin:jobs";
//
//    public HBaseUtil() throws IOException {
//        Configuration configuration = HBaseConfiguration.create();
//        configuration.set("hbase.zookeeper.quorum", "192.168.119.101");
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
//        conn = ConnectionFactory.createConnection(configuration);
//        admin = conn.getAdmin();
//    }
//
//    public List<Jobs> queryJobsInfo(String jobType) throws IOException {
//        Table table = conn.getTable(TableName.valueOf(tableName));
//        Scan scan = new Scan();
//        int regionNo = com.sulin.common.HBaseUtil.getRegionNo(jobType);
//        scan.setStartRow(Bytes.toBytes(regionNo + ""));
//        scan.setStopRow(Bytes.toBytes(regionNo + "~"));
//        ResultScanner scanner = table.getScanner(scan);
//        List<Jobs> mList = new ArrayList<>();
//        for (Result result : scanner) {
//            String joName = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("jobName")));
//            String jobArea = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("jobArea")));
//            String minSalary = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("minSalary")));
//            String maxSalary = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("maxSalary")));
//            String workYearNeed = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("workYearNeed")));
//            String workEducation = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("workEducation")));
//            String needNum = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("needNum")));
//            String putDate = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("putDate")));
//            String url = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("url")));
//            String companyName = Bytes.toString(result.getValue(Bytes.toBytes("company"), Bytes.toBytes("companyName")));
//            String companyType = Bytes.toString(result.getValue(Bytes.toBytes("company"), Bytes.toBytes("companyType")));
//            String minCompanySize = Bytes.toString(result.getValue(Bytes.toBytes("company"), Bytes.toBytes("minCompanySize")));
//            String maxCompanySize = Bytes.toString(result.getValue(Bytes.toBytes("company"), Bytes.toBytes("maxCompanySize")));
//            String companyindustry = Bytes.toString(result.getValue(Bytes.toBytes("company"), Bytes.toBytes("companyindustry")));
//            Jobs jobs = new Jobs();
//            jobs.setJobName(joName);
//            jobs.setJobArea(jobArea);
//            jobs.setCompanyName(companyName);
//            jobs.setJobUrl(url);
//            jobs.setCompanyType(companyType);
//            jobs.setCompanySize(minCompanySize + "-" + maxCompanySize);
//            jobs.setCompanyField(companyindustry);
//            jobs.setSalary(minSalary + "-" + maxSalary + "万/元");
//            jobs.setPushDate(putDate);
//            jobs.setNeedWorkDate(workYearNeed);
//            jobs.setRecord(workEducation);
//            jobs.setNeedNum(needNum);
//            mList.add(jobs);
//        }
//        return mList;
//    }
//}
