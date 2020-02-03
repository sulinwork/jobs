package utils;

import bean.Jobs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sulin
 * @create 2019-10-13 16:19
 */
public class HttpUtil {

    /**
     * 防止封IP
     */
    private static String[] user_Agent = {
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
            "Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
            "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
            "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
            "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
            "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0"
    };

    /**
     * 请求HTML
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String getHtml(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        int index = (int) (Math.random() * user_Agent.length);
        conn.setRequestProperty("user-agent", user_Agent[index]);
        conn.connect();
        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 提取最大页码
     *
     * @param html
     * @return
     */
    public static int getMaxPageNum(String html) {
        Document root = Jsoup.parse(html);
        String numPage = root.select("div.dw_page span.td:nth-child(3)").get(0).text();
        return string2Int(numPage);
    }

    /**
     * 字符串中提取数字
     *
     * @param str
     * @return
     */
    private static int string2Int(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return Integer.parseInt(m.replaceAll("").trim());
    }

    /**
     * 解析一级岗位信息
     *
     * @param html
     * @return
     */
    public static List<Jobs> parseHtml(String html) throws IOException, InterruptedException {
        List<Jobs> mList = new ArrayList<>();
        Document root = Jsoup.parse(html);
        Elements elements = root.select("div#resultList div.el");
        for (int i = 1; i < elements.size(); i++) {
            Element e = elements.get(i);
            Element select = e.select("p.t1>span>a").get(0);
            //岗位名字和Url
            String jobName = select.text();
            String jobUrl = select.attr("href");
            //公司名字
            String companyName = e.select("span.t2>a").get(0).text();
            //工作地址
            String jobAddr = e.select("span.t3").get(0).text();
            //工作薪资
            String salary = e.select("span.t4").get(0).text();
            //发布日期
            String putDate = e.select("span.t5").get(0).text();


            Jobs jobs = new Jobs(jobName, jobUrl, companyName, jobAddr, salary, putDate);
            parseJobInfo(jobUrl, jobs);
            mList.add(jobs);
            Thread.sleep(1000);
        }
        return mList;
    }

    /***
     * 获取二级信息
     * @param url
     * @return
     * @throws IOException
     */
    private static void parseJobInfo(String url, Jobs jobs) throws IOException {
        if (url.indexOf("https://jobs.51job.com/") == -1) {
            return;
        }
        System.out.println(url);
        //获取HTML
        Document root = Jsoup.parse(getHtml(url));
        //学历 经验要求全在 后期在清洗数据
        Elements typeE = root.select(".msg");
        String msgType = typeE.size() > 0 ? typeE.get(0).attr("title") : "";
        jobs.setMsgType(msgType);
        //任职要求
        Elements p = root.getElementsByClass("bmsg job_msg inbox").select("p");
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        for (Element e : p) {
            if (flag) {
                sb.append(e.text() + "|");
            }
            if (e.text().equals("任职要求：")) {
                flag = true;
            }
            if (e.text().equals("")) {
                flag = false;
            }
        }
        jobs.setRequirements(sb.length() > 2 ? sb.substring(0, sb.length() - 1) : sb.toString());
        //企业类型
        String companyType = root.select("p.at:nth-child(1)").get(0).text();
        jobs.setCompanyType(companyType);
        //企业人数
        String companySize = root.select("p.at:nth-child(2)").get(0).text();
        jobs.setCompanySize(companySize);
        //企业领域
        String companyField = root.select("p.at:nth-child(3").get(0).text();
        jobs.setCompanyField(companyField);

    }


    /**
     * 把数据写出到文件中
     *
     * @param mList
     * @param path
     * @throws Exception
     */
    public static void writeToTxt(List<Jobs> mList, String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new Exception("请不要输入目录！请输入具体文件");
            }
        } else {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file, true);
        for (Jobs jobs : mList) {
            System.out.println(jobs);
            fw.write(jobs.toString() + "\n");
        }
        fw.flush();
        fw.close();
    }
}
