package club.p9j7.support;

import club.p9j7.mapper.HouseMapper;
import club.p9j7.model.House;
import club.p9j7.service.HouseElk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class LianjiaPipeline implements Pipeline {
    @Autowired
    HouseMapper houseMapper;

    @Autowired(required = false)
    HouseElk houseElk;

    @Override
    public void process(ResultItems resultItems, Task task) {
        House house = resultItems.get("house");
        if (house != null) {
            houseMapper.insertHouse(house);
//            houseElk.save(house);
        }
    }
}
