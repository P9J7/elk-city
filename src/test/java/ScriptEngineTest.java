import club.p9j7.Application;
import club.p9j7.support.AqiSpider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptException;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles(profiles = "dev")
public class ScriptEngineTest {
    @Autowired
    AqiSpider aqiSpider;
    private static Logger logger = LoggerFactory.getLogger(ScriptEngineTest.class);

    @Test
    public void testPost() throws ScriptException, NoSuchMethodException, IOException {
        String method = "GETDAYDATA";
        String city = "广州";
        String month = "201901";
        String requestString = null;
        // 调用count方法，并传入两个参数
        requestString = aqiSpider.encryptRequest(city, month);
        logger.info("requestString" + requestString);
    }
}
