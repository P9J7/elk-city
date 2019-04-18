package club.p9j7.controller;

import club.p9j7.service.AqiElk;
import club.p9j7.service.HouseElk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    HouseElk houseElk;

    @Autowired
    AqiElk aqiElk;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/show")
    public ModelAndView showSum() {
        ModelAndView mv = new ModelAndView();
        Long houseCount = houseElk.count();
        Long aqiCount = aqiElk.count();
        mv.addObject("houseCount", houseCount);
        mv.addObject("aqiCount", aqiCount);
        mv.setViewName("show");
        return mv;
    }

    @RequestMapping("/deal/{city}")
    public ModelAndView showDeal(@PathVariable("city")String city) {
        ModelAndView mv = new ModelAndView();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("bj", "北京");
        hashMap.put("sh", "上海");
        hashMap.put("gz", "广州");
        hashMap.put("sz", "深圳");
        mv.setViewName("deal");
        mv.addObject("city", hashMap.get(city));
        mv.addObject("areaHelp", city);
        mv.addObject("statusHelp", 2);
        return mv;
    }

    @RequestMapping("/sale/{city}")
    public ModelAndView showSale(@PathVariable("city")String city) {
        ModelAndView mv = new ModelAndView();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("bj", "北京");
        hashMap.put("sh", "上海");
        hashMap.put("gz", "广州");
        hashMap.put("sz", "深圳");
        mv.setViewName("sale");
        mv.addObject("city", hashMap.get(city));
        mv.addObject("areaHelp", city);
        mv.addObject("statusHelp", 1);
        return mv;
    }


}
