import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class areaTest {
    @Test
    public void gzArray(){
        String[] gzArea = {"tianhe", "yuexiu", "liwan", "haizhu", "panyu", "baiyun", "huangpugz", "conghua", "zengcheng", "huadou", "nansha"};
        String[] bjArea = {"dongcheng", "xicheng", "chaoyang", "haidian", "fengtai", "shijingshan", "tongzhou", "changping", "daxing", "yizhuangkaifaqu", "shunyi", "fangshan", "mentougou"};
        String[] shArea = {"pudong", "minhang", "baoshan", "xuhui", "putuo", "yangpu", "changning", "songjiang", "jiading", "huangpu", "jingan", "zhabei", "hongkou", "qingpu", "fengxian"};
        String[] szArea = {"luohuqu", "futianqu", "nanshanqu", "yantianqu", "baoanqu", "longgangqu", "longhuaqu"};
        List<String> gz = Arrays.asList(gzArea);
        List<String> bj = Arrays.asList(bjArea);
        List<String> sh = Arrays.asList(shArea);
        List<String> sz = Arrays.asList(szArea);
        System.out.println(gz.size());
    }

}
