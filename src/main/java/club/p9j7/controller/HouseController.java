package club.p9j7.controller;

import club.p9j7.model.House;
import club.p9j7.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HouseController {
    @Autowired
    HouseService houseService;

    @RequestMapping("/")
    public String index() {
        return "elkcity";
    }

    @RequestMapping("/show")
    public String indexBj() {
        return "show";
    }

    @GetMapping(value = "/test")
    public ModelAndView test(HttpServletRequest req) {
        // UserEntity userEntity = getCurrentUser(req);
        House house = new House();
        house.setTitle("王五");
        house.setCityName("广州");
        ModelAndView mv = new ModelAndView();
        mv.addObject("house", house);
        mv.setViewName("show");
        return mv;
    }

}
