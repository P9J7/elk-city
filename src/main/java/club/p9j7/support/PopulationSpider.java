package club.p9j7.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.*;

public class PopulationSpider implements PageProcessor {
    private static Logger logger = LoggerFactory.getLogger(PopulationSpider.class);
    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5")
            .setRetryTimes(3).setSleepTime(100).setTimeOut(10000);
    @Override
    public void process(Page page) {
        Map<String, String> cityMap = new LinkedHashMap<>();
        List<String> city = page.getHtml().xpath("//tr/td[2]/allText()").all();
        List<String> population = page.getHtml().xpath("//tr/td[3]/allText()").all();
        city.replaceAll(item -> item.replace("市", ""));
        city.remove(0);
        population.remove(0);
//        city.addAll(page.getHtml().xpath("//tr/td[2]/text(0)").all());
        logger.info(city.toString());
        logger.info(String.valueOf(city.size()));
        logger.info(population.toString());
        logger.info(String.valueOf(population.size()));
        final int[] i = {0};
        city.forEach(item -> {
            cityMap.put(item, population.get(i[0]));
            i[0]++;
        });
        logger.info("i的值竟然变成了" + String.valueOf(i[0]));
        cityMap.forEach((k,v) -> {
            System.out.println("\"" + k + '\"' + ":" + v + ",");
        });
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("开始爬取");
        Spider.create(new PopulationSpider())
                .addUrl("https://baike.baidu.com/item/%E4%B8%AD%E5%9B%BD%E5%9F%8E%E5%B8%82%E4%BA%BA%E5%8F%A3%E6%8E%92%E5%90%8D%E8%A1%A8/16620508?fr=aladdin")
                .thread(5)
                .run();
    }
}
