$.ajax({
    url: '/getAqiAgg'
}).done(function (data) {
    option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {

        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['2014','2015','2016','2017','2018','2019']
        },
        yAxis: {
            type: 'value'
        },
        series: data
    };
    var myChart = echarts.init(document.getElementById('main'));
    myChart.setOption(option)
});

$.ajax({
    url: '/getPm2_5Agg'
}).done(function (data) {
    option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {

        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['2014','2015','2016','2017','2018','2019']
        },
        yAxis: {
            type: 'value'
        },
        series: data
    };
    var myChart = echarts.init(document.getElementById('main1'));
    myChart.setOption(option)
});

$.ajax({
    url: '/getPm10Agg'
}).done(function (data) {
    option = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {

        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['2014','2015','2016','2017','2018','2019']
        },
        yAxis: {
            type: 'value'
        },
        series: data
    };
    var myChart = echarts.init(document.getElementById('main2'));
    myChart.setOption(option)
});