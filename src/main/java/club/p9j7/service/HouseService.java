package club.p9j7.service;

import club.p9j7.model.House;
import club.p9j7.model.HouseResultContent;
import club.p9j7.repository.HouseElk;
import club.p9j7.support.LianjiaSpider;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.range.InternalRange;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class HouseService {
    @Autowired
    HouseElk houseElk;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    //系统概览城市房源数量分析
    @RequestMapping("/getCityCount")
    public List<HouseResultContent> getCityHouseCount() {
        List<HouseResultContent> cityList = new ArrayList<>();
        LianjiaSpider.mapCity.forEach((k, v) -> {
            Integer count = houseElk.countByCityName(v);
            HouseResultContent houseResultContent = new HouseResultContent(v, count);
            cityList.add(houseResultContent);
        });
        return cityList;
    }

    //在售区域分析
    @PostMapping("/getAreaCount")
    public List<HouseResultContent> getAreaCount(String cityName) {
        List<HouseResultContent> areaCountList = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("cityName", cityName))
                        .filter(QueryBuilders.matchQuery("status", 1)))
                .addAggregation(AggregationBuilders.terms("group_by_area").field("areaName").size(50)).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms stringTerms = (StringTerms) aggregations.getAsMap().get("group_by_area");
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        buckets.forEach(item -> {
            areaCountList.add(new HouseResultContent(item.getKeyAsString(), item.getDocCount()));
        });
        return areaCountList;
    }


    //在售和成交通用的户型分析
    @PostMapping("/getHouseType")
    public List<HouseResultContent> getHouseType(String cityName, Integer status) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        //只关注主要类型
        List<String> houseTypes = Arrays.asList("1室0厅", "1室1厅", "2室1厅", "2室2厅", "3室1厅", "3室2厅", "4室1厅", "4室2厅", "5室2厅");
        houseTypes.forEach((item) -> {
            Integer count = houseElk.countByCityNameAndRoomMainInfoAndStatus(cityName, item, status);
            HouseResultContent houseTypeCount = new HouseResultContent(item, count);
            houseResultContents.add(houseTypeCount);
        });
        return houseResultContents;
    }

    //成交月份分析
    @PostMapping("/getMonthCount")
    public List<HouseResultContent> getMonthCount(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        //只关注最近半年
        Map<String, List<String>> yearMonths = new LinkedHashMap<>();
        List<String> months = Arrays.asList("07", "08", "09", "10", "11", "12", "01", "02", "03", "04", "05");
        yearMonths.put("2018", months.subList(0, 6));
        yearMonths.put("2019", months.subList(6, 11));
        yearMonths.forEach((year, month) -> {
            month.forEach((monthItem) -> {
                Integer count = houseElk.countByCityNameAndDealYearAndDealMonth(cityName, year, monthItem);
                houseResultContents.add(new HouseResultContent(year + monthItem, count));
            });
        });
        return houseResultContents;
    }

    //在售区域均价分析
    @PostMapping("/getAverPrice")
    public List<HouseResultContent> getAverPrice(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("cityName", cityName))
                        .filter(QueryBuilders.matchQuery("status", 1)))
                .addAggregation(AggregationBuilders.terms("group_by_area").field("areaName").size(50)
                        .subAggregation(AggregationBuilders.avg("aver_price").field("unitprice"))).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms stringTerms = (StringTerms) aggregations.getAsMap().get("group_by_area");
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        buckets.forEach(item -> {
            InternalAvg internalAvg = (InternalAvg) item.getAggregations().getAsMap().get("aver_price");
            houseResultContents.add(new HouseResultContent(item.getKeyAsString(), internalAvg.getValue()));
        });
        Collections.sort(houseResultContents, (a, b) -> (int) ((double) b.getCount() - (double) a.getCount()));
        return houseResultContents;
    }

    //在售楼龄分析
    @PostMapping("/getConYear")
    public List<HouseResultContent> getConYear(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        List<String> yearList = Arrays.asList("1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018");
        yearList.forEach((item) -> {
            houseResultContents.add(new HouseResultContent(item, houseElk.countByCityNameAndAreaSubInfo(cityName, item)));
        });
        return houseResultContents;
    }

    //成交面积分析
    @PostMapping("/getAreaRange")
    public List<HouseResultContent> getAreaRange(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("cityName", cityName);
        MatchQueryBuilder limitStatusBuilder = QueryBuilders.matchQuery("status", 2);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termsQueryBuilder).filter(limitStatusBuilder);
        RangeAggregationBuilder rAB = AggregationBuilders.range("range_area").field("areaMainInfo").addRange(0, 50).addRange(50, 100).addRange(100, 150).addRange(150, 200).addUnboundedFrom(200);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).addAggregation(rAB).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalRange internalRange = (InternalRange) aggregations.asMap().get("range_area");
        List<InternalRange.Bucket> bucketList = internalRange.getBuckets();
        bucketList.forEach((item) -> {
            houseResultContents.add(new HouseResultContent(item.getKeyAsString(), item.getDocCount()));
        });
        return houseResultContents;
    }

    //成交价格分析
    @PostMapping("/getPriceRange")
    public List<HouseResultContent> getPriceRange(String cityName) {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("cityName", cityName);
        MatchQueryBuilder limitStatusBuilder = QueryBuilders.matchQuery("status", 2);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termsQueryBuilder).filter(limitStatusBuilder);
        RangeAggregationBuilder rAB = AggregationBuilders.range("range_price").field("price").addRange(0, 100).addRange(100, 200).addRange(200, 300).addRange(300, 400).addRange(400, 500).addRange(500, 600).addRange(600, 800).addRange(800,1000).addUnboundedFrom(1000);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).addAggregation(rAB).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalRange internalRange = (InternalRange) aggregations.asMap().get("range_price");
        List<InternalRange.Bucket> bucketList = internalRange.getBuckets();
        bucketList.forEach((item) -> {
            houseResultContents.add(new HouseResultContent(item.getKeyAsString(), item.getDocCount()));
        });
        return houseResultContents;
    }

    //城市在售整体均价分析
    @GetMapping("/getCityAverPrice")
    public List<HouseResultContent> getCityAverPrice() {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("status", 1))
                .addAggregation(AggregationBuilders.terms("city").field("cityName").size(50)
                        .subAggregation(AggregationBuilders.avg("aver_price").field("unitprice"))
                        .order(BucketOrder.aggregation("aver_price", false))).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms stringTerms = (StringTerms) aggregations.getAsMap().get("city");
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        buckets.forEach(item -> {
            InternalAvg internalAvg = (InternalAvg) item.getAggregations().getAsMap().get("aver_price");
            houseResultContents.add(new HouseResultContent(item.getKeyAsString(), internalAvg.getValue()));
        });
        return houseResultContents;
    }

    //城市在售区域均价分析
    @GetMapping("/getMaxAverPrice")
    public List<HouseResultContent> getMaxAverPrice() {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("status", 1))
                .addAggregation(AggregationBuilders.terms("city").field("cityName").size(50)
                        .subAggregation(AggregationBuilders.terms("area").field("areaName")
                                .subAggregation(AggregationBuilders.avg("aver_price").field("unitprice"))
                                .order(BucketOrder.aggregation("aver_price", false)))
                ).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms stringTerms = (StringTerms) aggregations.getAsMap().get("city");
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        buckets.forEach(item -> {
            StringTerms stringTerms1 = (StringTerms) item.getAggregations().getAsMap().get("area");
            List<StringTerms.Bucket> buckets1 = stringTerms1.getBuckets();
            InternalAvg internalAvg = (InternalAvg) buckets1.get(0).getAggregations().getAsMap().get("aver_price");
            String cityArea = item.getKeyAsString() + "." + buckets1.get(0).getKeyAsString();
            houseResultContents.add(new HouseResultContent(cityArea, internalAvg.getValue()));
        });
        houseResultContents.sort(Comparator.comparingDouble(o1 -> (double) o1.getCount()));
        Collections.reverse(houseResultContents);
        return houseResultContents;
    }

    //关注度最高的房分析
    @GetMapping("/getMaxFav")
    public List<House> getMaxFav() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .withSort(SortBuilders.fieldSort("favcount").order(SortOrder.DESC))
                .withPageable(PageRequest.of(1, 100)).build();
        List<House> houseList = elasticsearchTemplate.queryForList(searchQuery, House.class);
        AtomicInteger i = new AtomicInteger(1);
        houseList.forEach(item -> {
            item.setId(i.longValue());
            i.getAndIncrement();
        });
        return houseList;
    }

    //关注度top100户型分析
    @GetMapping("/getMaxHouseType")
    public List<HouseResultContent> getMaxHouseType() {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .withSort(SortBuilders.fieldSort("favcount").order(SortOrder.DESC))
                .withPageable(PageRequest.of(1, 100)).build();
        List<House> houseList = elasticsearchTemplate.queryForList(searchQuery, House.class);
        Map<String, List<House>> groupList = houseList.stream().collect(Collectors.groupingBy(House::getRoomMainInfo));
        groupList.forEach((k, v) -> houseResultContents.add(new HouseResultContent(k, v.size())));
        return houseResultContents;
    }

    //关注度top100房龄分析
    @GetMapping("/getMaxCon")
    public List<HouseResultContent> getMaxCon() {
        List<HouseResultContent> houseResultContents = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .withSort(SortBuilders.fieldSort("favcount").order(SortOrder.DESC))
                .withPageable(PageRequest.of(1, 100)).build();
        List<House> houseList = elasticsearchTemplate.queryForList(searchQuery, House.class);
        Map<String, List<House>> groupList = houseList.stream().collect(Collectors.groupingBy(House::getAreaSubInfo));
        groupList.forEach((k, v) -> {
            if (v.size() > 3)
                houseResultContents.add(new HouseResultContent(k, v.size()));
        });
        return houseResultContents;
    }

    //关注度面积和价格分析
    @GetMapping("/getMaxArea")
    public List<List<Double>> getMaxArea() {
        List<List<Double>> results = new ArrayList<>();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .withSort(SortBuilders.fieldSort("favcount").order(SortOrder.DESC))
                .withPageable(PageRequest.of(1, 100)).build();
        List<House> houseList = elasticsearchTemplate.queryForList(searchQuery, House.class);
        houseList.forEach(item -> {
            List<Double> list = new ArrayList<>();
            list.add(item.getAreaMainInfo());
            list.add(item.getPrice());
            results.add(list);
        });
        return results;
    }

    //城市各个户型在售均价比较
    @GetMapping("/getHouseCompare")
    public List<List<String>> getHouseCompare() {
        List<List<String>> results = new ArrayList<>();
        List th = Arrays.asList("product", "1室1厅", "2室1厅", "2室2厅", "3室1厅", "3室2厅", "4室2厅");
        results.add(th);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("status", 1))
                .addAggregation(AggregationBuilders.terms("city").field("cityName").size(50)
                        .subAggregation(AggregationBuilders.terms("room_type").field("roomMainInfo").size(50)
                                .subAggregation(AggregationBuilders.avg("avg_type").field("unitprice")).order(BucketOrder.aggregation("avg_type", false))))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms stringTerms = (StringTerms) aggregations.getAsMap().get("city");
        List<StringTerms.Bucket> buckets = stringTerms.getBuckets();
        buckets.forEach(item -> {
            List<String> list = Arrays.asList(item.getKeyAsString(), "1室1厅", "2室1厅", "2室2厅", "3室1厅", "3室2厅", "4室2厅");
            StringTerms stringTerms1 = (StringTerms) item.getAggregations().getAsMap().get("room_type");
            List<StringTerms.Bucket> buckets1 = stringTerms1.getBuckets();
            buckets1.forEach(roomtype -> {
                if (list.contains(roomtype.getKeyAsString())) {
                    InternalAvg internalAvg = (InternalAvg) roomtype.getAggregations().getAsMap().get("avg_type");
                    list.set(list.indexOf(roomtype.getKeyAsString()), internalAvg.getValueAsString());
                }
            });
            results.add(list);
        });
        return results;
    }
}
