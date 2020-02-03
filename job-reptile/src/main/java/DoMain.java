import bean.Jobs;
import utils.HttpUtil;

import java.util.List;

/**
 * @author sulin
 * @create 2019-10-13 16:33
 */
public class DoMain {
    public static void main(String[] args) throws Exception {

        String urlPage = "https://search.51job.com/list/000000,000000,0000,00,9,99," + args[0] +
                ",2," + 1 + ".html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
        String htmlPage = HttpUtil.getHtml(urlPage);
        int maxPageNumber = HttpUtil.getMaxPageNum(htmlPage);

        for (int i = Integer.parseInt(args[2]); i <= maxPageNumber; i++) {
            try {
                System.out.println("--------关键字：" + args[0] + "------页码：" + i + "-------------");
                String url = "https://search.51job.com/list/000000,000000,0000,00,9,99," + args[0] +
                        ",2," + i + ".html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
                String html = HttpUtil.getHtml(url);
                List<Jobs> jobs = HttpUtil.parseHtml(html);
                HttpUtil.writeToTxt(jobs, args[1]);
                Long waitTime = (long) (Math.random() * 2000) + 3000;
                Thread.sleep(1000 * 1);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("第" + i + "页出现异常");
            }

        }

    }
}
