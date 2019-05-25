package club.p9j7.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.management.JMException;
import javax.script.ScriptException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SpiderMan {
    private final LianjiaSpider lianjiaSpider;
    private final AqiSpider aqiSpider;
    private List<String> monthList = new ArrayList<>();
    private Map<String, List<String>> cityMonthRealMap = new HashMap<>();
    public final static List<String> cityList = new ArrayList<>(Arrays.asList("阿坝州", "安康", "阿克苏地区", "阿里地区", "阿拉善盟", "阿勒泰地区", "安庆", "安顺", "鞍山", "克孜勒苏州", "安阳", "蚌埠", "白城", "保定", "北海", "宝鸡", "北京", "毕节", "博州", "白山", "百色", "保山", "白沙", "包头", "保亭", "本溪", "巴彦淖尔", "白银", "巴中", "滨州", "亳州", "长春", "昌都", "常德", "成都", "承德", "赤峰", "昌吉州", "五家渠", "昌江", "澄迈", "重庆", "长沙", "常熟", "楚雄州", "朝阳", "沧州", "长治", "常州", "潮州", "郴州", "池州", "崇左", "滁州", "定安", "丹东", "东方", "东莞", "德宏州", "大理州", "大连", "大庆", "大同", "定西", "大兴安岭地区", "德阳", "东营", "黔南州", "达州", "德州", "儋州", "鄂尔多斯", "恩施州", "鄂州", "防城港", "佛山", "抚顺", "阜新", "阜阳", "富阳", "抚州", "福州", "广安", "贵港", "桂林", "果洛州", "甘南州", "固原", "广元", "贵阳", "甘孜州", "赣州", "广州", "淮安", "海北州", "鹤壁", "淮北", "河池", "海东地区", "邯郸", "哈尔滨", "合肥", "鹤岗", "黄冈", "黑河", "红河州", "怀化", "呼和浩特", "海口", "呼伦贝尔", "葫芦岛", "哈密地区", "海门", "海南州", "淮南", "黄南州", "衡水", "黄山", "黄石", "和田地区", "海西州", "河源", "衡阳", "汉中", "杭州", "菏泽", "贺州", "湖州", "惠州", "吉安", "金昌", "晋城", "景德镇", "金华", "西双版纳州", "九江", "吉林", "即墨", "江门", "荆门", "佳木斯", "济南", "济宁", "胶南", "酒泉", "句容", "湘西州", "金坛", "鸡西", "嘉兴", "江阴", "揭阳", "济源", "嘉峪关", "胶州", "焦作", "锦州", "晋中", "荆州", "库尔勒", "开封", "黔东南州", "克拉玛依", "昆明", "喀什地区", "昆山", "临安", "六安", "来宾", "聊城", "临沧", "娄底", "乐东", "廊坊", "临汾", "临高", "漯河", "丽江", "吕梁", "陇南", "六盘水", "拉萨", "乐山", "丽水", "凉山州", "陵水", "莱芜", "莱西", "临夏州", "溧阳", "辽阳", "辽源", "临沂", "龙岩", "洛阳", "连云港", "莱州", "兰州", "林芝", "柳州", "泸州", "马鞍山", "牡丹江", "茂名", "眉山", "绵阳", "梅州", "宁波", "南昌", "南充", "宁德", "内江", "南京", "怒江州", "南宁", "南平", "那曲地区", "南通", "南阳", "平度", "平顶山", "普洱", "盘锦", "蓬莱", "平凉", "莆田", "萍乡", "濮阳", "攀枝花", "青岛", "琼海", "秦皇岛", "曲靖", "齐齐哈尔", "七台河", "黔西南州", "清远", "庆阳", "钦州", "衢州", "泉州", "琼中", "荣成", "日喀则", "乳山", "日照", "韶关", "寿光", "上海", "绥化", "石河子", "石家庄", "商洛", "三明", "三门峡", "山南", "遂宁", "四平", "商丘", "宿迁", "上饶", "汕头", "汕尾", "绍兴", "三亚", "邵阳", "沈阳", "十堰", "松原", "双鸭山", "深圳", "朔州", "宿州", "随州", "苏州", "石嘴山", "泰安", "塔城地区", "太仓", "铜川", "屯昌", "通化", "天津", "铁岭", "通辽", "铜陵", "吐鲁番地区", "铜仁地区", "唐山", "天水", "太原", "台州", "泰州", "文昌", "文登", "潍坊", "瓦房店", "威海", "乌海", "芜湖", "武汉", "吴江", "乌兰察布", "乌鲁木齐", "渭南", "万宁", "文山州", "武威", "无锡", "温州", "吴忠", "梧州", "五指山", "西安", "兴安盟", "许昌", "宣城", "襄阳", "孝感", "迪庆州", "锡林郭勒盟", "厦门", "西宁", "咸宁", "湘潭", "邢台", "新乡", "咸阳", "新余", "信阳", "忻州", "徐州", "雅安", "延安", "延边州", "宜宾", "盐城", "宜昌", "宜春", "银川", "运城", "伊春", "云浮", "阳江", "营口", "榆林", "玉林", "伊犁哈萨克州", "阳泉", "玉树州", "烟台", "鹰潭", "义乌", "宜兴", "玉溪", "益阳", "岳阳", "扬州", "永州", "淄博", "自贡", "珠海", "湛江", "镇江", "诸暨", "张家港", "张家界", "张家口", "周口", "驻马店", "章丘", "肇庆", "中山", "舟山", "昭通", "中卫", "张掖", "招远", "资阳", "遵义", "枣庄", "漳州", "郑州", "株洲"));

    @Autowired
    public SpiderMan(LianjiaSpider lianjiaSpider, AqiSpider aqiSpider) {
        this.lianjiaSpider = lianjiaSpider;
        this.aqiSpider = aqiSpider;
    }

    public void crawlHouse(String city) {
        Spider houseSpider = Spider.create(lianjiaSpider).setScheduler(new FileCacheQueueScheduler("D:\\lianjiaSpider")).addPipeline(lianjiaSpider.lianjiaPipeline).thread(10);
        houseSpider.addUrl("https://" + city + ".lianjia.com/ershoufang/");
        houseSpider.addUrl("https://" + city + ".lianjia.com/chengjiao/");
        try {
            SpiderMonitor.instance().register(houseSpider);
        } catch (JMException e) {
            e.printStackTrace();
        }
        houseSpider.run();
    }

    public void crawlHouse() {
        Spider houseSpider = Spider.create(lianjiaSpider).setScheduler(new FileCacheQueueScheduler("D:\\AlllianjiaSpider")).addPipeline(lianjiaSpider.lianjiaPipeline).thread(5);
        LianjiaSpider.mapCity.forEach((k, v) -> {
            houseSpider.addUrl("https://" + k + ".lianjia.com/ershoufang/");
            houseSpider.addUrl("https://" + k + ".lianjia.com/chengjiao/");
        });
        try {
            SpiderMonitor.instance().register(houseSpider);
        } catch (JMException e) {
            e.printStackTrace();
        }
        houseSpider.run();
    }

    public void crwalAqi() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String nowDate = format.format(new Date());
        Date minDate = null;
        try {
            minDate = format.parse("201312");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date maxDate = null;
        try {
            maxDate = format.parse(nowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar dd = Calendar.getInstance();
        dd.setTime(minDate);
        while (dd.getTime().before(maxDate)) {
            String str = format.format(dd.getTime());
            monthList.add(str);
            dd.add(Calendar.MONTH, 1);
        }
        monthList.add(nowDate);
        for (String city : cityList) {
            List<String> monthRealList = new ArrayList<>();
            for (String monthItem : monthList) {
                try {
                    monthRealList.add(aqiSpider.encryptRequest(city, monthItem));
                } catch (ScriptException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            /**
             * 解决request不带city信息问题
             */
            cityMonthRealMap.put(city, monthRealList);
        }
        Spider airSpider = Spider.create(aqiSpider).addPipeline(aqiSpider.aqiPipeline).thread(5);
        cityMonthRealMap.forEach((key, value) -> {
            value.forEach((monthReal) -> {
                Request request = new Request("https://www.aqistudy.cn/historydata/api/historyapi.php");
                request.setMethod(HttpConstant.Method.POST);
                Map<String, Object> map = new HashMap<>();
                map.put("hd", monthReal);
                map.put("city", key);
                request.setExtras(map);
                request.setRequestBody(HttpRequestBody.form(map, "utf-8"));
                airSpider.addRequest(request);
            });
        });
        airSpider.run();
    }

    public void crwalAqi(String city, String month) throws ScriptException, NoSuchMethodException {
        String realParam = aqiSpider.encryptRequest(city, month);
        Spider airSpider = Spider.create(aqiSpider).addPipeline(aqiSpider.aqiPipeline).thread(5);
        Request request = new Request("https://www.aqistudy.cn/historydata/api/historyapi.php");
        request.setMethod(HttpConstant.Method.POST);
        Map<String, Object> map = new HashMap<>();
        map.put("hd", realParam);
        map.put("city", city);
        request.setExtras(map);
        request.setRequestBody(HttpRequestBody.form(map, "utf-8"));
        airSpider.addRequest(request);
        airSpider.run();
    }
}
