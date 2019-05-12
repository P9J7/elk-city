package club.p9j7.service;

import club.p9j7.model.Aqi;
import club.p9j7.model.AqiAggResult;
import club.p9j7.model.AqiResultContent;
import club.p9j7.repository.AqiElk;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.InternalDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.InternalTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AqiService {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    AqiElk aqiElk;

    public List<AqiResultContent> getCityAqiCount() {
        List<AqiResultContent> resultList = Collections.synchronizedList(new ArrayList<>());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("group_by_city").field("city").size(369)
                        .subAggregation(AggregationBuilders.terms("group_by_quality").field("quality")))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms cityTerms = (StringTerms) aggregations.getAsMap().get("group_by_city");
        List<StringTerms.Bucket> cityBuckets = cityTerms.getBuckets();
        cityBuckets.forEach((city) -> {
            int cityCount = (int) city.getDocCount();
            StringTerms qualityTerms = (StringTerms) city.getAggregations().getAsMap().get("group_by_quality");
            List<StringTerms.Bucket> qualityBucket = qualityTerms.getBuckets();
            Map<String, Integer> map = new HashMap<>();
            qualityBucket.forEach(((quality) -> {
                map.put(quality.getKeyAsString(), (int) quality.getDocCount());
            }));
            AqiResultContent aqiResultContent = new AqiResultContent(city.getKeyAsString(), cityCount, map);
            resultList.add(aqiResultContent);
        });
        return resultList;
    }

    public List<AqiResultContent> getAqiRank(String year, String month) {
        List<AqiResultContent> resultContents = Collections.synchronizedList(new ArrayList<>());
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.rangeQuery("time_point").from(year + "-" + month + "-1")
                        .to(year + "-" + month + "-28"))
                .addAggregation(AggregationBuilders.terms("group_by_city").field("city").size(369)
                        .subAggregation(AggregationBuilders.avg("avg_aqi_month").field("aqi"))
                        .subAggregation(AggregationBuilders.avg("avg_pm2_5_month").field("pm2_5"))
                        .subAggregation(AggregationBuilders.avg("avg_pm10_month").field("pm10"))
                        .subAggregation(AggregationBuilders.avg("avg_so2_month").field("so2"))
                        .subAggregation(AggregationBuilders.avg("avg_co_month").field("co"))
                        .subAggregation(AggregationBuilders.avg("avg_no2_month").field("no2"))
                        .subAggregation(AggregationBuilders.avg("avg_o3_month").field("o3"))
                        .order(BucketOrder.aggregation("avg_aqi_month", false))).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms cityTerms = (StringTerms) aggregations.getAsMap().get("group_by_city");
        List<StringTerms.Bucket> cityBuckets = cityTerms.getBuckets();
        DecimalFormat df = new DecimalFormat("0.00");
        AtomicInteger i = new AtomicInteger(1);
        cityBuckets.forEach((city) -> {
            InternalAvg aqi = (InternalAvg) city.getAggregations().asMap().get("avg_aqi_month");
            InternalAvg pm2_5 = (InternalAvg) city.getAggregations().asMap().get("avg_pm2_5_month");
            InternalAvg pm10 = (InternalAvg) city.getAggregations().asMap().get("avg_pm10_month");
            InternalAvg so2 = (InternalAvg) city.getAggregations().asMap().get("avg_so2_month");
            InternalAvg co = (InternalAvg) city.getAggregations().asMap().get("avg_co_month");
            InternalAvg no2 = (InternalAvg) city.getAggregations().asMap().get("avg_no2_month");
            InternalAvg o3 = (InternalAvg) city.getAggregations().asMap().get("avg_o3_month");
            Map<String, String> map = new HashMap<>();
            map.put("aqi", df.format(aqi.getValue()));
            map.put("pm2_5", df.format(pm2_5.getValue()));
            map.put("pm10", df.format(pm10.getValue()));
            map.put("so2", df.format(so2.getValue()));
            map.put("co", df.format(co.getValue()));
            map.put("no2", df.format(no2.getValue()));
            map.put("o3", df.format(o3.getValue()));
            if (aqi.getValue() > 0 && city.getDocCount() > 15) {
                AqiResultContent aqiResultContent = new AqiResultContent(city.getKeyAsString(), i.getAndIncrement(), map);
                resultContents.add(aqiResultContent);
            }
        });
        return resultContents;
    }

    public List<AqiResultContent> getAqiArea(String year, String month) {
        List<AqiResultContent> resultContents = Collections.synchronizedList(new ArrayList<>());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.rangeQuery("time_point")
                .from(year + "-" + month + "-1").to(year + "-" + month + "-28"))
                .addAggregation(AggregationBuilders.terms("group_by_city").field("city").size(369)
                        .subAggregation(AggregationBuilders.avg("avg_aqi_month").field("aqi"))
                        .order(BucketOrder.aggregation("avg_aqi_month", false))).build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        StringTerms cityTerms = (StringTerms) aggregations.getAsMap().get("group_by_city");
        List<StringTerms.Bucket> cityBuckets = cityTerms.getBuckets();
        cityBuckets.forEach(city -> {
            InternalAvg internalAvg = (InternalAvg) city.getAggregations().asMap().get("avg_aqi_month");
            if (internalAvg.getValue() > 0 && city.getDocCount() > 15) {
                AqiResultContent aqiResultContent = new AqiResultContent(city.getKeyAsString(), (int) internalAvg.value());
                resultContents.add(aqiResultContent);
            }
        });
        return resultContents;
    }

    public List<List<Double>> getAqiCompare(String city1, String city2, String date1, String date2) {
        List<List<Double>> lists = new ArrayList<>();
        List<Double> aqi1List = new ArrayList<>();
        List<Double> pm2_51List = new ArrayList<>();
        List<Double> pm101List = new ArrayList<>();
        List<Double> co1List = new ArrayList<>();
        List<Double> no21List = new ArrayList<>();
        List<Double> so21List = new ArrayList<>();
        List<Double> o31List = new ArrayList<>();
        List<Double> aqi2List = new ArrayList<>();
        List<Double> pm2_52List = new ArrayList<>();
        List<Double> pm102List = new ArrayList<>();
        List<Double> co2List = new ArrayList<>();
        List<Double> no22List = new ArrayList<>();
        List<Double> so22List = new ArrayList<>();
        List<Double> o32List = new ArrayList<>();
        SearchQuery searchQuery1 = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("city", city1))
                .must(QueryBuilders.rangeQuery("time_point")
                        .from(date1).to(date2)))
                .withPageable(PageRequest.of(0, 366))
                .withSort(SortBuilders.fieldSort("time_point")).build();
        List<Aqi> city1List = elasticsearchTemplate.queryForList(searchQuery1, Aqi.class);
        fillList(aqi1List, pm2_51List, pm101List, co1List, no21List, so21List, o31List, city1List);
        SearchQuery searchQuery2 = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("city", city2))
                .must(QueryBuilders.rangeQuery("time_point")
                        .from(date1).to(date2)))
                .withPageable(PageRequest.of(0, 366))
                .withSort(SortBuilders.fieldSort("time_point")).build();
        List<Aqi> city2List = elasticsearchTemplate.queryForList(searchQuery2, Aqi.class);
        fillList(aqi2List, pm2_52List, pm102List, co2List, no22List, so22List, o32List, city2List);
        lists.add(aqi1List);
        lists.add(aqi2List);
        lists.add(pm2_51List);
        lists.add(pm2_52List);
        lists.add(pm101List);
        lists.add(pm102List);
        lists.add(co1List);
        lists.add(co2List);
        lists.add(no21List);
        lists.add(no22List);
        lists.add(so21List);
        lists.add(so22List);
        lists.add(o31List);
        lists.add(o32List);
        return lists;
    }

    private void fillList(List<Double> aqi1List, List<Double> pm2_51List, List<Double> pm101List, List<Double> co1List, List<Double> no21List, List<Double> so21List, List<Double> o31List, List<Aqi> city1List) {
        city1List.forEach(item -> {
            aqi1List.add(Double.valueOf(item.getAqi()));
            pm2_51List.add(Double.valueOf(item.getPm2_5()));
            pm101List.add(Double.valueOf(item.getPm10()));
            co1List.add(item.getCo().doubleValue());
            no21List.add(Double.valueOf(item.getNo2()));
            so21List.add(Double.valueOf(item.getSo2()));
            o31List.add(Double.valueOf(item.getO3()));
        });
    }

    public Map<String, List<String>> getAqiObserve(String city) {
        List<String> aqiContents = Collections.synchronizedList(new ArrayList<>());
        List<String> dateContents = Collections.synchronizedList(new ArrayList<>());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("time_point").from("2014-01-01").to("2019-04-01"))
                .must(QueryBuilders.matchQuery("city", city)))
                .withSort(SortBuilders.fieldSort("time_point"))
                .withPageable(PageRequest.of(0, 3000))
                .build();
        List<Aqi> aqiList = elasticsearchTemplate.queryForList(searchQuery, Aqi.class);
        aqiList.forEach(item -> {
            aqiContents.add(String.valueOf(item.getAqi()));
            dateContents.add(item.getTime_point());
        });
        Map<String, List<String>> map = new HashMap<>();
        map.put("aqiData", aqiContents);
        map.put("timeData", dateContents);
        return map;
    }

    public List<AqiAggResult> getPmAver() {
        List<AqiAggResult> results = Collections.synchronizedList(new ArrayList<>());
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.rangeQuery("time_point").from("2014-01-01").to("2019-01-01"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_month").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.MONTH)
                        .subAggregation(AggregationBuilders.avg("avgPm2_5").field("pm2_5"))
                        .subAggregation(AggregationBuilders.avg("avgPm10").field("pm10")))
                .build();
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalDateHistogram groupByYear = (InternalDateHistogram) aggregations.getAsMap().get("group_by_month");
        List<InternalDateHistogram.Bucket> buckets = groupByYear.getBuckets();
        List<Double> pm2_5List = new ArrayList<>();
        List<Double> pm10List = new ArrayList<>();
        buckets.forEach(item -> {
            InternalAvg pm2_5 = (InternalAvg) item.getAggregations().getAsMap().get("avgPm2_5");
            InternalAvg pm10 = (InternalAvg) item.getAggregations().getAsMap().get("avgPm10");
            pm2_5List.add(new BigDecimal(pm2_5.getValue()).setScale(2, RoundingMode.DOWN).doubleValue());
            pm10List.add(new BigDecimal(pm10.getValue()).setScale(2, RoundingMode.DOWN).doubleValue());
        });
        AqiAggResult pm2_5Result = new AqiAggResult("PM2.5", "line", pm2_5List);
        AqiAggResult pm10Result = new AqiAggResult("PM10", "line", pm10List);
        results.add(pm2_5Result);
        results.add(pm10Result);
        return results;
    }

    public List<AqiAggResult> getAqiAgg() {
        List<AqiAggResult> results = Collections.synchronizedList(new ArrayList<>());
        SearchQuery jjj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "北京", "天津", "保定", "唐山", "廊坊", "石家庄", "邯郸",
                        "秦皇岛", "张家口", "承德", "沧州", "邢台", "衡水"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.terms("group_by_quality").field("quality")))
                .build();
        SearchQuery csj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "上海", "南京", "无锡", "常州", "苏州", "南通", "盐城",
                        "扬州", "镇江", "泰州", "杭州", "宁波", "嘉兴", "湖州", "绍兴", "金华", "舟山", "台州", "合肥", "芜湖", "马鞍山",
                        "铜陵", "安庆", "滁州", "池州", "宣城"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.terms("group_by_quality").field("quality")))
                .build();
        SearchQuery zsj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "广州", "佛山", "肇庆", "清远", "云浮", "韶关", "深圳",
                        "东莞", "惠州", "汕尾", "河源", "珠海", "中山", "江门", "阳江"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.terms("group_by_quality").field("quality")))
                .build();
        results.add(aqiAggGet(jjj, "京津冀"));
        results.add(aqiAggGet(csj, "长三角"));
        results.add(aqiAggGet(zsj, "珠三角"));
        return results;
    }

    public List<AqiAggResult> getPm2_5Agg() {
        List<AqiAggResult> results = Collections.synchronizedList(new ArrayList<>());
        SearchQuery jjj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "北京", "天津", "保定", "唐山", "廊坊", "石家庄", "邯郸",
                        "秦皇岛", "张家口", "承德", "沧州", "邢台", "衡水"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.avg("avg_pm2_5").field("pm2_5")))
                .build();
        SearchQuery csj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "上海", "南京", "无锡", "常州", "苏州", "南通", "盐城",
                        "扬州", "镇江", "泰州", "杭州", "宁波", "嘉兴", "湖州", "绍兴", "金华", "舟山", "台州", "合肥", "芜湖", "马鞍山",
                        "铜陵", "安庆", "滁州", "池州", "宣城"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.avg("avg_pm2_5").field("pm2_5")))
                .build();
        SearchQuery zsj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "广州", "佛山", "肇庆", "清远", "云浮", "韶关", "深圳",
                        "东莞", "惠州", "汕尾", "河源", "珠海", "中山", "江门", "阳江"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.avg("avg_pm2_5").field("pm2_5")))
                .build();
        results.add(pm2_5AggGet(jjj, "京津冀"));
        results.add(pm2_5AggGet(csj, "长三角"));
        results.add(pm2_5AggGet(zsj, "珠三角"));
        return results;
    }

    public List<AqiAggResult> getPm10Agg() {
        List<AqiAggResult> results = Collections.synchronizedList(new ArrayList<>());
        SearchQuery jjj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "北京", "天津", "保定", "唐山", "廊坊", "石家庄", "邯郸",
                        "秦皇岛", "张家口", "承德", "沧州", "邢台", "衡水"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.avg("avg_pm10").field("pm10")))
                .build();
        SearchQuery csj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "上海", "南京", "无锡", "常州", "苏州", "南通", "盐城",
                        "扬州", "镇江", "泰州", "杭州", "宁波", "嘉兴", "湖州", "绍兴", "金华", "舟山", "台州", "合肥", "芜湖", "马鞍山",
                        "铜陵", "安庆", "滁州", "池州", "宣城"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.avg("avg_pm10").field("pm10")))
                .build();
        SearchQuery zsj = new NativeSearchQueryBuilder().withQuery(
                QueryBuilders.termsQuery("city", "广州", "佛山", "肇庆", "清远", "云浮", "韶关", "深圳",
                        "东莞", "惠州", "汕尾", "河源", "珠海", "中山", "江门", "阳江"))
                .addAggregation(AggregationBuilders.dateHistogram("group_by_year").field("time_point")
                        .dateHistogramInterval(DateHistogramInterval.YEAR)
                        .subAggregation(AggregationBuilders.avg("avg_pm10").field("pm10")))
                .build();
        results.add(pm10AggGet(jjj, "京津冀"));
        results.add(pm10AggGet(csj, "长三角"));
        results.add(pm10AggGet(zsj, "珠三角"));
        return results;
    }

    public AqiAggResult aqiAggGet(SearchQuery searchQuery, String name) {
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalDateHistogram groupByYear = (InternalDateHistogram) aggregations.getAsMap().get("group_by_year");
        List<InternalDateHistogram.Bucket> buckets = groupByYear.getBuckets();
        List<Double> list = new ArrayList<>();
        buckets.forEach(item -> {
            if (!item.getKeyAsString().equals("2013-01-01T00:00:00.000Z")) {
                StringTerms stringTerms = (StringTerms) item.getAggregations().getAsMap().get("group_by_quality");
                List<StringTerms.Bucket> buckets1 = stringTerms.getBuckets();
                long count = buckets1.stream().filter(quality -> (quality.getKeyAsString().equals("良") || quality.getKeyAsString().equals("优"))).mapToLong(InternalTerms.Bucket::getDocCount).sum();
                list.add(new BigDecimal(count * 1.0 / item.getDocCount() * 100).setScale(2, RoundingMode.DOWN).doubleValue());
            }
        });
        AqiAggResult aqiAggResult = new AqiAggResult(name, "line", list);
        return aqiAggResult;
    }

    public AqiAggResult pm2_5AggGet(SearchQuery searchQuery, String name) {
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalDateHistogram groupByYear = (InternalDateHistogram) aggregations.getAsMap().get("group_by_year");
        List<InternalDateHistogram.Bucket> buckets = groupByYear.getBuckets();
        List<Double> list = new ArrayList<>();
        buckets.forEach(item -> {
            if (!item.getKeyAsString().equals("2013-01-01T00:00:00.000Z")) {
                InternalAvg internalAvg = (InternalAvg) item.getAggregations().getAsMap().get("avg_pm2_5");
                list.add(new BigDecimal(internalAvg.getValue()).setScale(2, RoundingMode.DOWN).doubleValue());
            }
        });
        AqiAggResult aqiAggResult = new AqiAggResult(name, "line", list);
        return aqiAggResult;
    }

    public AqiAggResult pm10AggGet(SearchQuery searchQuery, String name) {
        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, response -> response.getAggregations());
        InternalDateHistogram groupByYear = (InternalDateHistogram) aggregations.getAsMap().get("group_by_year");
        List<InternalDateHistogram.Bucket> buckets = groupByYear.getBuckets();
        List<Double> list = new ArrayList<>();
        buckets.forEach(item -> {
            if (!item.getKeyAsString().equals("2013-01-01T00:00:00.000Z")) {
                InternalAvg internalAvg = (InternalAvg) item.getAggregations().getAsMap().get("avg_pm10");
                list.add(new BigDecimal(internalAvg.getValue()).setScale(2, RoundingMode.DOWN).doubleValue());
            }
        });
        AqiAggResult aqiAggResult = new AqiAggResult(name, "line", list);
        return aqiAggResult;
    }
}
