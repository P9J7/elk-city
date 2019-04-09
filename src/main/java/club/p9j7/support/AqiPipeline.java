package club.p9j7.support;

import club.p9j7.mapper.AqiMapper;
import club.p9j7.model.Aqi;
import club.p9j7.service.AqiElk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class AqiPipeline implements Pipeline {
    @Autowired
    AqiMapper aqiMapper;

    @Autowired
    AqiElk aqiElk;
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Aqi> aqiList = resultItems.get("aqiList");
        if (aqiList != null) {
            aqiMapper.insertAqiList(aqiList);
            aqiElk.saveAll(aqiList);
        }
    }
}
