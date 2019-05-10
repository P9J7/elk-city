package club.p9j7.support;

import club.p9j7.model.House;
import club.p9j7.service.HouseElk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class LianjiaPipeline implements Pipeline {
    @Autowired
    HouseElk houseElk;

    private final static List<House> houseList = Collections.synchronizedList(new LinkedList<>());

    @Override
    public void process(ResultItems resultItems, Task task) {
        House house = resultItems.get("house");
        if (house != null) {
            houseList.add(house);
            if (houseList.size() > 2000) {
                houseElk.saveAll(houseList);
                houseList.clear();
            }
        }
    }
}
