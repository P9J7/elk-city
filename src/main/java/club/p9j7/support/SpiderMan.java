package club.p9j7.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpiderMan {
    private final LianjiaSpider lianjiaSpider;
    private final AqiSpider aqiSpider;

    @Autowired
    public SpiderMan(LianjiaSpider lianjiaSpider, AqiSpider aqiSpider) {
        this.lianjiaSpider = lianjiaSpider;
        this.aqiSpider = aqiSpider;
    }

    public void crawlHouse(String area) {
        Spider.create(lianjiaSpider).addUrl("https://gz.lianjia.com/ershoufang/" + area + "/").addPipeline(lianjiaSpider.lianjiaPipeline).thread(5).run();
    }

    public void crwalAqi(String city, String month) {
        String requestString = null;
        try {
            requestString = aqiSpider.encryptRequest(city, month);
        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        Request request = new Request("https://www.aqistudy.cn/historydata/api/historyapi.php");
        Map<String, Object> map = new HashMap<>();
        map.put("hd", requestString);
        request.setMethod(HttpConstant.Method.POST);
        request.setRequestBody(HttpRequestBody.form(map, "utf-8"));
        Spider.create(aqiSpider).addRequest(request).addPipeline(aqiSpider.aqiPipeline).thread(5).run();
    }
}
