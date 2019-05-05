$.ajax({
    url: '/getHouseCompare'
}).done(function (data) {
    option = {
        legend: {},
        tooltip: {},
        dataset: {
            source: data
        },
        xAxis: [
            {type: 'category', gridIndex: 0}
        ],
        yAxis: [
            {gridIndex: 0}
        ],
        series: [
            // These series are in the first grid.
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'},
            {type: 'bar', seriesLayoutBy: 'row'}
        ]
    };
    var myChart = echarts.init(document.getElementById('main'));
    myChart.setOption(option);
});


