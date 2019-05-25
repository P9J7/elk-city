package club.p9j7.support;

import club.p9j7.model.House;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LianjiaSpider implements PageProcessor {
    public final static Map<String, String> mapCity = new HashMap<>();

    static {
        mapCity.put("bj", "北京");
        mapCity.put("sh", "上海");
        mapCity.put("gz", "广州");
        mapCity.put("sz", "深圳");
        mapCity.put("cq", "重庆");
        mapCity.put("cd", "成都");
        mapCity.put("tj", "天津");
        mapCity.put("hz", "杭州");
        mapCity.put("nj", "南京");
        mapCity.put("xa", "西安");
        mapCity.put("sjz", "石家庄");
        mapCity.put("zz", "郑州");
        mapCity.put("xm", "厦门");
        mapCity.put("wh", "武汉");
        mapCity.put("nb", "宁波");
        mapCity.put("sy", "沈阳");//
        mapCity.put("ty", "太原");
        mapCity.put("hrb", "哈尔滨");//
        mapCity.put("qd", "青岛");
        mapCity.put("dl", "大连");
        mapCity.put("cc", "长春");
        mapCity.put("hk", "海口");
        mapCity.put("hf", "合肥");
        mapCity.put("fz", "福州");
        mapCity.put("nc", "南昌");
        mapCity.put("jn", "济南");
        mapCity.put("cs", "长沙");
        mapCity.put("nn", "南宁");
        mapCity.put("lz", "兰州");
        mapCity.put("gy", "贵阳");
        mapCity.put("km", "昆明");
        mapCity.put("yinchuan", "银川");
        mapCity.put("hhht", "呼和浩特");
    }

    public final Pipeline lianjiaPipeline;

    @Autowired
    public LianjiaSpider(Pipeline lianjiaPipeline) {
        this.lianjiaPipeline = lianjiaPipeline;
    }

    //正则匹配城市在售首页
    private static final String urlCitySale = "https://[a-z]{2,}\\.lianjia\\.com/ershoufang/$";
    //正则匹配城市成交首页
    private static final String urlCityDeal = "https://[a-z]{2,}\\.lianjia\\.com/chengjiao/$";
    //正则匹配在售的某个房子
    private static final String urlDetail = "https://[a-z]{2,}\\.lianjia\\.com/ershoufang/[a-zA-Z]*\\d+\\.html";
    //正则匹配成交的某个房子，部分链接不规范含有字母前缀
    private static final String urlDealDetail = "https://[a-z]{2,}\\.lianjia\\.com/chengjiao/[a-zA-Z]*\\d+\\.html";
    //正则匹配城市在售分区链接
    private static final String urlBase = "https://[a-z]{2,}\\.lianjia\\.com/ershoufang/[a-z]+\\d*/$";
    //正则匹配城市成交分区链接
    private static final String urlDealBase = "https://[a-z]{2,}\\.lianjia\\.com/chengjiao/[a-z]+\\d*/$";
    //正则匹配城市在售分页链接
    private static final String urlIndex = "https://[a-z]{2,}\\.lianjia\\.com/ershoufang/[a-z]+\\d*/pg\\d+/";
    //正则匹配城市成交分页链接
    private static final String urlDealIndex = "https://[a-z]{2,}\\.lianjia\\.com/chengjiao/[a-z]+\\d*/pg\\d+/";

    private Site site = Site.me().setCharset("UTF-8").setUserAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5").setRetryTimes(3).setSleepTime(100).setTimeOut(10000);
    private static Logger logger = LoggerFactory.getLogger(LianjiaSpider.class);

    @Override
    public void process(Page page) {
        if (page.getUrl().regex(urlCitySale).match() || page.getUrl().regex(urlCityDeal).match()) {
//            logger.info("首页入口");
            String url = page.getUrl().toString();
            List<String> areaUrl = page.getHtml().xpath("//div[@data-role=ershoufang]/div/a/@href").all();
            for (int i = 0; i < areaUrl.size(); i++) {
                areaUrl.set(i, url.substring(0, url.lastIndexOf(".") + 4).concat(areaUrl.get(i)));
            }
            page.addTargetRequests(areaUrl);
        }

        if (page.getUrl().regex(urlBase).match()) {
            int saleHouseCount = Integer.parseInt(page.getHtml().xpath("//div[@class=resultDes]/h2[@class=total]/span/text(0)").toString().trim());
            houseCount(page, saleHouseCount);
        }

        if (page.getUrl().regex(urlDealBase).match()) {
            int dealHouseCount = Integer.parseInt(page.getHtml().xpath("//div[@class=resultDes]/div[@class=total]/span/text(0)").toString().trim());
            houseCount(page, dealHouseCount);
        }

        if (page.getUrl().regex(urlIndex).match() || page.getUrl().regex(urlDealIndex).match()) {
//            logger.debug("爬取列表页");
            page.addTargetRequests(page.getHtml().xpath("//div[@class=title]/a/@href").all());
        }

        if (page.getUrl().regex(urlDetail).match() || page.getUrl().regex(urlDealDetail).match()) {
//            logger.debug("爬取详情页");
            Html pageHtml = page.getHtml();
            String url = page.getUrl().toString();
            House house = new House();
            house.setCityName(mapCity.get(url.substring(url.indexOf("/") + 2, url.indexOf("."))));
            house.setId(Long.parseLong(url.replaceAll("\\D", "")));
            house.setUrl(url);
            if (page.getUrl().regex(urlDetail).match()) {
                house.setTitle(pageHtml.xpath("//div[@class='content']/div[@class='title']/h1[@class='main']/@title").toString());
                house.setFavcount(Integer.parseInt(pageHtml.xpath("//div[@class='action']/span[@class='count']/text(0)").toString()));
                house.setPrice(Double.parseDouble(pageHtml.xpath("//div[@class='content']/div[@class='price ']/span[@class='total']/text(0)").toString()));
                house.setUnitprice(Double.parseDouble(pageHtml.xpath("//div[@class='content']/div[@class='price ']//span[@class='unitPriceValue']/text(0)").toString().replaceAll("\"", "")));
                house.setRoomMainInfo(pageHtml.xpath("//div[@class='houseInfo']/div[@class='room']/div[@class='mainInfo']/text(0)").toString());
                house.setAreaMainInfo(Double.parseDouble(pageHtml.xpath("//div[@class='houseInfo']/div[@class='area']/div[@class='mainInfo']/text(0)").toString().replaceAll("\\D{2,}", "")));
                house.setAreaSubInfo(pageHtml.xpath("//div[@class='houseInfo']/div[@class='area']/div[@class='subInfo']/text(0)").toString().split("/")[0].replaceAll("\\D+", ""));
                house.setCommunityName(pageHtml.xpath("//div[@class='communityName']/a/text(0)").toString());
                String areaName = pageHtml.xpath("//div[@class='areaName']/span[@class='info']/allText()").toString();
                //坑爹的全角空格
                house.setAreaName(pageHtml.xpath("//div[@class='areaName']/span[@class='info']/allText()").toString().split("[\\s\\p{Zs}]+")[0]);
                house.setStatus(1);
            }
            if (page.getUrl().regex(urlDealDetail).match()) {
                String cRMAM = pageHtml.xpath("//div[@class='wrapper']/text()").toString();
                house.setTitle(cRMAM);
                String[] threeInfo = cRMAM.split("[\\s\\p{Zs}]+");
                house.setCommunityName(threeInfo[0]);
                house.setRoomMainInfo(threeInfo[1]);
                //坑爹的正则匹配两次
                house.setAreaMainInfo(Double.parseDouble(threeInfo[2].replaceAll("\\D{2,}", "")));
                String date = pageHtml.xpath("//div[@class='wrapper']/span/text()").toString().split("[\\s\\p{Zs}]+")[0];
                String[] yearMonth = date.split("\\.");
                house.setDealYear(yearMonth[0]);
                house.setDealMonth(yearMonth[1]);
                house.setPrice(Double.parseDouble(pageHtml.xpath("//div[@class='price']/span[@class='dealTotalPrice']/i/text()").toString()));
                house.setUnitprice(Double.parseDouble(pageHtml.xpath("//div[@class='price']/b/text(0)").toString()));
                house.setStatus(2);
            }
            page.putField("house", house);
        }
    }

    //判断是否需要分子区爬取
    private void houseCount(Page page, int dealHouseCount) {
        if (dealHouseCount > 3000) {
            String url = page.getUrl().toString();
            List<String> subAreaList = page.getHtml().xpath("//div[@data-role=ershoufang]/div[2]/a/@href").all();
            for (int i = 0; i < subAreaList.size(); i++) {
                subAreaList.set(i, url.substring(0, url.lastIndexOf(".") + 4).concat(subAreaList.get(i)));
            }
            page.addTargetRequests(subAreaList);
        } else if (dealHouseCount > 0) {
            List<String> pgList = new ArrayList<>();
            for (int i = 1; i < (dealHouseCount / 30 + 2); i++) {
                pgList.add(page.getUrl().toString() + "pg" + i + "/");
            }
            page.addTargetRequests(pgList);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
