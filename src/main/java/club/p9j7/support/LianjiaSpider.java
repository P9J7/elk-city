package club.p9j7.support;

import club.p9j7.model.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

@Component
public class LianjiaSpider implements PageProcessor {
    public final Pipeline lianjiaPipeline;
    @Autowired
    public LianjiaSpider(Pipeline lianjiaPipeline) {
        this.lianjiaPipeline = lianjiaPipeline;
    }
    //正则匹配某个房子的链接   https://gz.lianjia.com/ershoufang/108400227459.html
    private static final String urlDetail = "https://gz\\.lianjia\\.com/ershoufang/\\d+\\.html";
    //正则匹配首页链接   https://gz.lianjia.com/ershoufang/tianhe/
    private static final String urlBase = "https://gz\\.lianjia\\.com/ershoufang(/[a-z]+)?/$";
    //正则匹配翻页链接   https://gz.lianjia.com/ershoufang/tianhe/pg2
    private  static final String urlIndex = "https://gz\\.lianjia\\.com/ershoufang(/[a-z]+)?/pg\\d+/";

    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5").setRetryTimes(3).setSleepTime(100).setTimeOut(10000);
    private static Logger logger = LoggerFactory.getLogger(LianjiaSpider.class);
    @Override
    public void process(Page page) {
        if (page.getUrl().regex(urlBase).match()) {
            List<String> pgList = new ArrayList<>();
            for (int i = 2; i < 101; i++) {
                pgList.add(page.getUrl().toString() + "pg" + i + "/");
            }
            page.addTargetRequests(pgList);
        }

        if (page.getUrl().regex(urlIndex).match()) {
            logger.debug("爬取列表页");
            //logger.info(page.getHtml().toString());
            //xpath得到列表页上所有房子的链接,加入到后续爬取队列
            page.addTargetRequests(page.getHtml().xpath("//div[@class=title]/a/@href").all());
        }

        //如果当前爬取的为某个房子页面
        if (page.getUrl().regex(urlDetail).match()) {
            logger.debug("爬取详情页");
            Html pageHtml = page.getHtml();
            House house = new House();
            house.setCode(page.getUrl().toString().replaceAll("\\D",""));
            house.setUrl(page.getUrl().get());
            house.setTitle(pageHtml.xpath("//div[@class='content']/div[@class='title']/h1[@class='main']/@title").toString());
            house.setSubtitle(pageHtml.xpath("//div[@class='content']/div[@class='title']/div[@class='sub']/@title").toString());
            house.setFavcount(Integer.parseInt(pageHtml.xpath("//div[@class='action']/span[@class='count']/text(0)").toString()));
            house.setPrice(Double.parseDouble(pageHtml.xpath("//div[@class='content']/div[@class='price ']/span[@class='total']/text(0)").toString()));
            house.setUnitprice(Double.parseDouble(pageHtml.xpath("//div[@class='content']/div[@class='price ']//span[@class='unitPriceValue']/text(0)").toString().replaceAll("\"","")));
            house.setRoomMainInfo(pageHtml.xpath("//div[@class='houseInfo']/div[@class='room']/div[@class='mainInfo']/text(0)").toString());
            house.setRoomSubInfo(pageHtml.xpath("//div[@class='houseInfo']/div[@class='room']/div[@class='subInfo']/text(0)").toString());
            house.setRoomMainType(pageHtml.xpath("//div[@class='houseInfo']/div[@class='type']/div[@class='mainInfo']/text(0)").toString());
            house.setRoomSubType(pageHtml.xpath("//div[@class='houseInfo']/div[@class='type']/div[@class='subInfo']/text(0)").toString());
            house.setAreaMainInfo(pageHtml.xpath("//div[@class='houseInfo']/div[@class='area']/div[@class='mainInfo']/text(0)").toString());
            house.setAreaSubInfo(pageHtml.xpath("//div[@class='houseInfo']/div[@class='area']/div[@class='subInfo']/text(0)").toString());
            house.setCommunityName(pageHtml.xpath("//div[@class='communityName']/a/text(0)").toString());
            house.setAreaName(pageHtml.xpath("//div[@class='areaName']/span[@class='info']/allText()").toString());
            page.putField("house",house);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
