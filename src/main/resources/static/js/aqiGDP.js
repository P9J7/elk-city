var cityAQIMap = {
    '北京': [[28014, 1961]],
    '天津': [[18549, 1293]],
    '石家庄': [[6460, 1016]],
    '太原': [[3382, 420]],
    '呼和浩特': [[2743, 286]],
    '沈阳': [[5864, 810]],
    '大连': [[7363, 669]],
    '长春': [[6530, 767]],
    '哈尔滨': [[6355, 536]],
    '上海': [[30632, 2301]],
    '南京': [[11715, 800]],
    '杭州': [[12603, 870]],
    '宁波': [[9842, 760]],
    '合肥': [[7213, 570]],
    '福州': [[7103, 711]],
    '厦门': [[4351, 353]],
    '南昌': [[5003, 504]],
    '济南': [[7201, 681]],
    '青岛': [[11037, 871]],
    '郑州': [[9130, 862]],
    '武汉': [[13410, 821]],
    '长沙': [[10535, 704]],
    '广州': [[21503, 1270]],
    '深圳': [[22490, 1035]],
    '南宁': [[4118, 666]],
    '海口': [[1390, 204]],
    '重庆': [[19424, 3017]],
    '成都': [[13889, 1404]],
    '贵阳': [[3537, 432]],
    '昆明': [[4857, 643]],
    '拉萨': [[479, 55]],
    '西安': [[7469, 1000]],
    '兰州': [[2523, 361]],
    '西宁': [[1284, 220]],
    '银川': [[1803, 199]],
    '乌鲁木齐': [[2743, 311]]
};

var cityHouseMap = {
    'bj': [[28014, 1961]],
    '天津': [[18549, 1293]],
    '石家庄': [[6460, 1016]],
    '太原': [[3382, 420]],
    '呼和浩特': [[2743, 286]],
    '沈阳': [[5864, 810]],
    '大连': [[7363, 669]],
    '长春': [[6530, 767]],
    '哈尔滨': [[6355, 536]],
    'sh': [[30632, 2301]],
    '南京': [[11715, 800]],
    '杭州': [[12603, 870]],
    '宁波': [[9842, 760]],
    '合肥': [[7213, 570]],
    '福州': [[7103, 711]],
    '厦门': [[4351, 353]],
    '南昌': [[5003, 504]],
    '济南': [[7201, 681]],
    '青岛': [[11037, 871]],
    '郑州': [[9130, 862]],
    '武汉': [[13410, 821]],
    '长沙': [[10535, 704]],
    'gz': [[21503, 1270]],
    'sz': [[22490, 1035]],
    '南宁': [[4118, 666]],
    '海口': [[1390, 204]],
    'cq': [[19424, 3017]],
    '成都': [[13889, 1404]],
    '贵阳': [[3537, 432]],
    '昆明': [[4857, 643]],
    '拉萨': [[479, 55]],
    '西安': [[7469, 1000]],
    '兰州': [[2523, 361]],
    '西宁': [[1284, 220]],
    '银川': [[1803, 199]],
    '乌鲁木齐': [[2743, 311]]
};

var convertData1 = function (data, first) {
    if (first == 1) {
        for (var i = 0; i < data.length; i++) {
            var cityData = cityAQIMap[data[i].name];
            if (cityData) {
                cityData[0] = cityData[0].concat(data[i].value);
            }
        }
        // console.log(cityAQIMap)
    }
    if (first == 2) {
        // console.log(cityAQIMap)
        for (var i = 0; i < data.length; i++) {
            var cityData = cityAQIMap[data[i].name];
            if (cityData) {
                cityData[0] = cityData[0].slice(0,2);
                cityData[0] = cityData[0].concat(data[i].value);
            }
        }
        // console.log(cityAQIMap)
    }
};

var convertData2 = function (data) {
        for (var i = 0; i < data.length; i++) {
            var cityData = cityHouseMap[data[i].name];
            if (cityData) {
                cityData[0] = cityData[0].concat(data[i].value);
            }
        }
        console.log(cityHouseMap)
};

var schema = [
    {name: 'gdp', index: 0, text: 'GDP'},
    {name: 'population', index: 1, text: '人口'},
    {name: 'AQI', index: 2, text: 'AQI指数'}
];


var itemStyle = {
    normal: {
        opacity: 0.8,
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowOffsetY: 0,
        shadowColor: 'rgba(0, 0, 0, 0.5)'
    }
};

option = {
    backgroundColor: '#404a59',
    color: [
        '#666666', '#00c5ff', '#808A87', '#808069', '#FAFFF0', '#E6E6E6', '#FAF0E6', '#FFFFCD', '#FCE6C9', '#FF0000',
        '#FFFF00', '#bbff40', '#FF9912', '#9C661F', '#00ff6d', '#FF7F50', '#FFD700', '#FF6347', '#FF7D40', '#FFC0CB',
        '#B0171F', '#ffb300', '#FF00FF', '#00FF00', '#608aff', '#C76114', '#7FFF00', '#228B22', '#6B8E23', '#03A89E',
        '#DA70D6', '#9933FA', '#00C78C', '#082E54', '#40E0D0', '#802A2A'
    ],
    legend: {
        y: 'top',
        data: ['北京', '上海', '广州', '深圳', '天津', '太原', '石家庄', '呼和浩特', '沈阳', '大连',
            '长春', '哈尔滨', '南京', '杭州', '宁波', '合肥', '福州', '厦门', '南昌', '济南',
            '青岛', '郑州', '武汉', '长沙', '南宁', '海口', '重庆', '成都', '贵阳', '昆明',
            '拉萨', '西安', '兰州', '西宁', '银川', '乌鲁木齐'],
        textStyle: {
            color: '#fff',
            fontSize: 16
        }
    },
    grid: {
        x: '10%',
        x2: 150,
        y: '18%',
        y2: '10%'
    },
    tooltip: {
        padding: 10,
        backgroundColor: '#222',
        borderColor: '#777',
        borderWidth: 1,
        formatter: function (obj) {
            var value = obj.value;
            return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
                + obj.seriesName
                + '</div>'
                + schema[0].text + '：' + value[0] + '亿元<br>'
                + schema[1].text + '：' + value[1] + '万人<br>'
                + schema[2].text + '：' + value[2] + '<br>';
        }
    },
    xAxis: {
        type: 'value',
        name: 'GDP',
        nameGap: 5000,
        nameTextStyle: {
            color: '#fff',
            fontSize: 14
        },
        max: 40000,
        splitLine: {
            show: true
        },
        axisLine: {
            lineStyle: {
                color: '#eee'
            }
        }
    },
    yAxis: {
        type: 'value',
        name: '人口',
        nameLocation: 'end',
        nameGap: 500,
        nameTextStyle: {
            color: '#fff',
            fontSize: 16
        },
        axisLine: {
            lineStyle: {
                color: '#eee'
            }
        },
        max: 3000,
        splitLine: {
            show: true
        }
    },
    visualMap: [
        {
            show: false,
            left: 'right',
            top: '10%',
            dimension: 2,
            min: 0,
            max: 250,
            itemWidth: 30,
            itemHeight: 120,
            calculable: true,
            precision: 0.1,
            text: ['圆形大小：PM2.5'],
            textGap: 30,
            textStyle: {
                color: '#fff'
            },
            inRange: {
                symbolSize: [10, 70]
            },
            outOfRange: {
                symbolSize: [10, 70],
                color: ['rgba(255,255,255,.2)']
            },
            controller: {
                inRange: {
                    color: ['#c23531']
                },
                outOfRange: {
                    color: ['#444']
                }
            }
        }
    ],
    series: [
        {
            name: '北京',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['北京']
        },
        {
            name: '上海',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['上海']
        },
        {
            name: '深圳',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['深圳']
        },
        {
            name: '广州',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['广州']
        },
        {
            name: '天津',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['天津']
        },
        {
            name: '石家庄',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['石家庄']
        },
        {
            name: '太原',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['太原']
        },
        {
            name: '呼和浩特',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['呼和浩特']
        },
        {
            name: '沈阳',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['沈阳']
        },
        {
            name: '大连',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['大连']
        },
        {
            name: '长春',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['长春']
        },
        {
            name: '哈尔滨',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['哈尔滨']
        },
        {
            name: '南京',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['南京']
        },
        {
            name: '杭州',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['杭州']
        },
        {
            name: '宁波',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['宁波']
        },
        {
            name: '合肥',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['合肥']
        },
        {
            name: '福州',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['福州']
        },
        {
            name: '厦门',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['厦门']
        },
        {
            name: '南昌',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['南昌']
        },
        {
            name: '济南',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['济南']
        },
        {
            name: '青岛',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['青岛']
        },
        {
            name: '郑州',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['郑州']
        },
        {
            name: '武汉',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['武汉']
        },
        {
            name: '长沙',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['长沙']
        },
        {
            name: '南宁',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['南宁']
        },
        {
            name: '海口',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['海口']
        },
        {
            name: '重庆',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['重庆']
        },
        {
            name: '成都',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['成都']
        },
        {
            name: '贵阳',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['贵阳']
        },
        {
            name: '昆明',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['昆明']
        },
        {
            name: '拉萨',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['拉萨']
        },
        {
            name: '西安',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['西安']
        },
        {
            name: '兰州',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['兰州']
        },
        {
            name: '西宁',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['西宁']
        },
        {
            name: '银川',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['银川']
        },
        {
            name: '乌鲁木齐',
            type: 'scatter',
            itemStyle: itemStyle,
            data: cityAQIMap['乌鲁木齐']
        }
    ]
};

var myChart;
$(document).ready(function () {
    var data = [];
    $.ajax({
        url: '/getAqiArea',
        method: 'POST',
        data: {
            year: 2014,
            month: 1
        }
    }).done(function (results) {
        myChart = echarts.init(document.getElementById('main'));
        for (var i in results) {
            data.push({
                name: results[i].cityName,
                value: results[i].cityCount
            });
        }
        convertData1(data,1);
        myChart.setOption(option);
    })

});

$(document).ready(function () {
    $.ajax({
        url: '/getCityAverPrice',
        method: 'GET'
    }).done(function (res) {
        option1 = {
            backgroundColor: '#404a59',
            color: [
                '#666666', '#00c5ff', '#808A87', '#808069', '#FAFFF0', '#E6E6E6', '#FAF0E6', '#FFFFCD', '#FCE6C9', '#FF0000',
                '#FFFF00', '#bbff40', '#FF9912', '#9C661F', '#00ff6d', '#FF7F50', '#FFD700', '#FF6347', '#FF7D40', '#FFC0CB',
                '#B0171F', '#ffb300', '#FF00FF', '#00FF00', '#608aff', '#C76114', '#7FFF00', '#228B22', '#6B8E23', '#03A89E',
                '#DA70D6', '#9933FA', '#00C78C', '#082E54', '#40E0D0', '#802A2A'
            ],
            legend: {
                y: 'top',
                data: ['北京', '上海', '广州', '深圳', '天津', '太原', '石家庄', '呼和浩特', '沈阳', '大连',
                    '长春', '哈尔滨', '南京', '杭州', '宁波', '合肥', '福州', '厦门', '南昌', '济南',
                    '青岛', '郑州', '武汉', '长沙', '南宁', '海口', '重庆', '成都', '贵阳', '昆明',
                    '拉萨', '西安', '兰州', '西宁', '银川', '乌鲁木齐'],
                textStyle: {
                    color: '#fff',
                    fontSize: 16
                }
            },
            grid: {
                x: '10%',
                x2: 150,
                y: '18%',
                y2: '10%'
            },
            tooltip: {
                padding: 10,
                backgroundColor: '#222',
                borderColor: '#777',
                borderWidth: 1,
                formatter: function (obj) {
                    var value = obj.value;
                    return '<div style="border-bottom: 1px solid rgba(255,255,255,.3); font-size: 18px;padding-bottom: 7px;margin-bottom: 7px">'
                        + obj.seriesName
                        + '</div>'
                        + schema[0].text + '：' + value[0] + '亿元<br>'
                        + schema[1].text + '：' + value[1] + '万人<br>'
                        + '房市均价：' + value[2] + '元/平米<br>';
                }
            },
            xAxis: {
                type: 'value',
                name: 'GDP',
                nameGap: 5000,
                nameTextStyle: {
                    color: '#fff',
                    fontSize: 14
                },
                max: 40000,
                splitLine: {
                    show: true
                },
                axisLine: {
                    lineStyle: {
                        color: '#eee'
                    }
                }
            },
            yAxis: {
                type: 'value',
                name: '人口',
                nameLocation: 'end',
                nameGap: 500,
                nameTextStyle: {
                    color: '#fff',
                    fontSize: 16
                },
                axisLine: {
                    lineStyle: {
                        color: '#eee'
                    }
                },
                max: 3000,
                splitLine: {
                    show: true
                }
            },
            visualMap: [
                {
                    show: false,
                    left: 'right',
                    top: '10%',
                    dimension: 2,
                    min: 0,
                    max: 50000,
                    itemWidth: 30,
                    itemHeight: 120,
                    calculable: true,
                    precision: 0.1,
                    text: ['圆形大小：PM2.5'],
                    textGap: 30,
                    textStyle: {
                        color: '#fff'
                    },
                    inRange: {
                        symbolSize: [10, 70]
                    },
                    outOfRange: {
                        symbolSize: [10, 70],
                        color: ['rgba(255,255,255,.2)']
                    },
                    controller: {
                        inRange: {
                            color: ['#c23531']
                        },
                        outOfRange: {
                            color: ['#444']
                        }
                    }
                }
            ],
            series: [
                {
                    name: '北京',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['bj']
                },
                {
                    name: '上海',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['sh']
                },
                {
                    name: '深圳',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['sz']
                },
                {
                    name: '广州',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['gz']
                },
                {
                    name: '天津',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['天津']
                },
                {
                    name: '石家庄',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['石家庄']
                },
                {
                    name: '太原',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['太原']
                },
                {
                    name: '呼和浩特',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['呼和浩特']
                },
                {
                    name: '沈阳',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['沈阳']
                },
                {
                    name: '大连',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['大连']
                },
                {
                    name: '长春',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['长春']
                },
                {
                    name: '哈尔滨',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['哈尔滨']
                },
                {
                    name: '南京',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['南京']
                },
                {
                    name: '杭州',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['杭州']
                },
                {
                    name: '宁波',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['宁波']
                },
                {
                    name: '合肥',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['合肥']
                },
                {
                    name: '福州',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['福州']
                },
                {
                    name: '厦门',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['厦门']
                },
                {
                    name: '南昌',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['南昌']
                },
                {
                    name: '济南',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['济南']
                },
                {
                    name: '青岛',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['青岛']
                },
                {
                    name: '郑州',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['郑州']
                },
                {
                    name: '武汉',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['武汉']
                },
                {
                    name: '长沙',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['长沙']
                },
                {
                    name: '南宁',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['南宁']
                },
                {
                    name: '海口',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['海口']
                },
                {
                    name: '重庆',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['cq']
                },
                {
                    name: '成都',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['成都']
                },
                {
                    name: '贵阳',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['贵阳']
                },
                {
                    name: '昆明',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['昆明']
                },
                {
                    name: '拉萨',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['拉萨']
                },
                {
                    name: '西安',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['西安']
                },
                {
                    name: '兰州',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['兰州']
                },
                {
                    name: '西宁',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['西宁']
                },
                {
                    name: '银川',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['银川']
                },
                {
                    name: '乌鲁木齐',
                    type: 'scatter',
                    itemStyle: itemStyle,
                    data: cityHouseMap['乌鲁木齐']
                }
            ]
        };
        var price = [];
        var averChart = echarts.init(document.getElementById('main1'));
        for (var i in res) {
            price.push({
                name: res[i].name,
                value: res[i].count
            });
        }
        convertData2(price);
        averChart.setOption(option1);
    })
});

$("#search").click(function () {
    var data = [];
    $.ajax({
        url: '/getAqiArea',
        method: 'POST',
        data: {
            year: $('#year').val(),
            month: $('#month').val()
        }
    }).done(function (results) {
        if (myChart != null && myChart != "" && myChart != undefined) {
            myChart.dispose();
        }
        myChart = echarts.init(document.getElementById('main'));
        for (var i in results) {
            data.push({
                name: results[i].cityName,
                value: results[i].cityCount
            });
        }
        convertData1(data,2);
        myChart.setOption(option);
    })
});