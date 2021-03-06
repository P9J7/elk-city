import club.p9j7.Application;
import club.p9j7.controller.IndexController;
import club.p9j7.model.House;
import club.p9j7.support.HouseSpider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "dev")
public class test {
    @Autowired
    private IndexController indexController;

    @Autowired
    private HouseSpider houseSpider;

    @Autowired
    private MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;


    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testIndex() {
        indexController.index();
    }

    @Test
    public void testMVC() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testMybatis() {
        House house = new House();
        house.setId(10840024688266L);
        house.setUrl("https://gz.lianjia.com/chengjiao/108400246502.html");
        house.setTitle("测试");
        house.setFavcount(50);
        house.setCityName("gz");
        house.setPrice(93.68);
    }



}
