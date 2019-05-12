package club.p9j7.controller;

import club.p9j7.model.AqiAggResult;
import club.p9j7.model.AqiResultContent;
import club.p9j7.service.AqiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class AqiRestController {
    @Autowired
    AqiService aqiService;

    @RequestMapping("/getAqiCount")
    public List<AqiResultContent> getCityAqiCount() {
        return aqiService.getCityAqiCount();
    }

    @PostMapping("/getAqiRank")
    public List<AqiResultContent> getAqiRank(String year, String month) {
        return aqiService.getAqiRank(year, month);
    }

    @PostMapping("/getAqiArea")
    public List<AqiResultContent> getAqiArea(String year, String month) {
        return aqiService.getAqiArea(year, month);
    }

    @PostMapping("getAqiCompare")
    public List<List<Double>> getAqiCompare(String city1, String city2, String date1, String date2) {
        return aqiService.getAqiCompare(city1, city2, date1, date2);
    }

    @RequestMapping("/getAqiObserve")
    public Map<String, List<String>> getAqiObserve(String city) {
       return aqiService.getAqiObserve(city);
    }

    @RequestMapping("/getPmAver")
    public List<AqiAggResult> getPmAver() {
        return aqiService.getPmAver();
    }

    @RequestMapping("/getAqiAgg")
    public List<AqiAggResult> getAqiAgg() {
        return aqiService.getAqiAgg();
    }

    @RequestMapping("/getPm2_5Agg")
    public List<AqiAggResult> getPm2_5Agg() {
        return aqiService.getPm2_5Agg();
    }

    @RequestMapping("/getPm10Agg")
    public List<AqiAggResult> getPm10Agg() {
        return aqiService.getPm10Agg();
    }
}
