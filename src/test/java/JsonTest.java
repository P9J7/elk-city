import club.p9j7.model.Aqi;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
public class JsonTest {
    @Test
    public void testArray() {
        String string = "[{\"time_point\":\"2019-02-01\",\"aqi\":77,\"pm2_5\":38,\"pm10\":103,\"so2\":27,\"no2\":25,\"co\":0.69999999999999996,\"o3\":78,\"rank\":\"195\",\"quality\":\"\\u826f\"},{\"time_point\":\"2019-02-02\",\"aqi\":195,\"pm2_5\":146,\"pm10\":213,\"so2\":24,\"no2\":45,\"co\":1.5,\"o3\":48,\"rank\":\"360\",\"quality\":\"\\u4e2d\\u5ea6\\u6c61\\u67d3\"}]";
        List<Aqi> aqiList = JSONObject.parseArray(string, Aqi.class);
        System.out.println(aqiList);
    }

    @Test
    public void testPattern() {
        String string = "fasdf[sdfasdfasdf][dsaf]";
        System.out.println(string.substring(string.indexOf("["), string.indexOf("]")+1));

    }

    @Test
    public void testCityName(){
        String url = "https://sh.lianjia.com/ershoufang/107101033946.html";
        System.out.println(url.substring(url.indexOf("/") + 2, url.indexOf(".")));
    }

    @Test
    public void testMonth() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String nowDate=format.format(new Date());
        Date minDate = format.parse("201301");
        Date maxDate = format.parse(nowDate);
        Calendar dd = Calendar.getInstance();
        dd.setTime(minDate);
        while (dd.getTime().before(maxDate)) {
            String str = format.format(dd.getTime());
            System.out.println(str);
            dd.add(Calendar.MONTH, 1);
        }
        System.out.println(nowDate);
    }

    @Test
    public void testDecimal(){
        BigDecimal bigDecimal = BigDecimal.valueOf(5.2);
        System.out.println(bigDecimal);
    }

}
