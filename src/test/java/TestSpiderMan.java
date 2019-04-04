import club.p9j7.Application;
import club.p9j7.support.SpiderMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles(profiles = "dev")
public class TestSpiderMan {
    //todo 数据库house表添加区域字段，删除无用字段
    //todo 数据库aqi表添加城市字段，月份统计如何实现？？？重复记录如何防止插入？
    //todo 如何便捷的设置两个爬虫的启动参数？？？
    @Autowired
    SpiderMan spiderMan;

    @Test
    public void testAqi(){
        spiderMan.crwalAqi("石家庄","201812");
    }

    @Test
    public void testHouse(){
        spiderMan.crawlHouse("zengcheng");
    }
}
