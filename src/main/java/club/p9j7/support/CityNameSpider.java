package club.p9j7.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class CityNameSpider implements PageProcessor {
    private static Logger logger = LoggerFactory.getLogger(CityNameSpider.class);
    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5").setRetryTimes(3).setSleepTime(100).setTimeOut(10000);


    @Override
    public void process(Page page) {
        logger.info(page.getHtml().toString());
        List<String> cityList = page.getHtml().xpath("//div[@class='all']//li/a/text()").all();
        logger.info(String.valueOf(cityList.size()));
        logger.info(cityList.toString());
        cityList.forEach(city -> System.out.println("<option>"+city+"</option>"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("开始爬取...");
        Spider.create(new CityNameSpider()).addUrl("https://www.aqistudy.cn/historydata/").thread(5).run();
    }
}
