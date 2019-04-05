package club.p9j7.support;

import club.p9j7.model.Aqi;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.util.List;
import java.util.Map;

@Component
public class AqiSpider implements PageProcessor {
    public final Pipeline aqiPipeline;
    private final Invocable invocable;
    private Site site = Site.me().setUserAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5").setRetryTimes(3).setSleepTime(100).setTimeOut(10000);
    private static Logger logger = LoggerFactory.getLogger(AqiSpider.class);

    @Autowired
    public AqiSpider(Pipeline aqiPipeline, Invocable invocable) {
        this.aqiPipeline = aqiPipeline;
        this.invocable = invocable;
    }

    @Override
    public void process(Page page) {
        logger.debug("page:" + page.getHtml().toString());
        Map map = page.getRequest().getExtras();
        String city = (String) map.get("city");
        String response = null;
        try {
            response = (String) invocable.invokeFunction("decodeData", page.getHtml().xpath("//body/text()").toString());
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        assert response != null;
        String data = response.substring(response.indexOf("["), response.indexOf("]") + 1);
        logger.debug("aqiList:" + data);
        List<Aqi> aqiList = JSONObject.parseArray(data, Aqi.class);
        for (Aqi aqi: aqiList) {
            aqi.setCity(city);
        }
        page.putField("aqiList", aqiList);
    }

    @Override
    public Site getSite() {
        return site;
    }

    public String encryptRequest(String city, String month) throws ScriptException, NoSuchMethodException {
        return (String) invocable.invokeFunction("getEncryptedData","GETDAYDATA", city, month);
    }
}
