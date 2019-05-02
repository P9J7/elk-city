$(document).ready(function() {
    $('#dataTable').DataTable(
        {
            "sAjaxDataProp":"",
            "ajax": "/getMaxFav",
            "columns": [
                { "data": "id" ,"defaultContent": "0"},
                { "data": "favcount" ,"defaultContent": "0"},
                { "data": "cityName","defaultContent": "0" },
                { "data": "areaName","defaultContent": "0" },
                { "data": "communityName","defaultContent": "0" },
                { "data": "areaSubInfo","defaultContent": "0" },
                { "data": "roomMainInfo","defaultContent": "0" },
                { "data": "areaMainInfo","defaultContent": "0" },
                { "data": "price","defaultContent": "0" },
                { "data": "unitprice","defaultContent": "0" }
                // { "data": "url","defaultContent": "0" }
            ],
            "order": [[ 1, "desc" ]]
        }
    );

    $.ajax({
        url: '/getMaxHouseType'
    }).done(function (results) {
        var data1 = [];
        var name1 = [];
        myChart1 = echarts.init(document.getElementById('main1'));
        for (var i in results) {
            name1.push(results[i].name);
            data1.push({
                name: results[i].name,
                value: results[i].count
            });
        };
        option1 = {
            // title : {
            //     text: '某站点用户访问来源',
            //     subtext: '纯属虚构',
            //     x:'center'
            // },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: name
            },
            series : [
                {
                    name: '房型',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data: data1,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart1.setOption(option1)
    });

    $.ajax({
        url: '/getMaxCon'
    }).done(function (results) {
        var data2 = [];
        var name2 = [];
        myChart2 = echarts.init(document.getElementById('main2'));
        for (var i in results) {
            name2.push(results[i].name);
            data2.push({
                name: results[i].name,
                value: results[i].count
            });
        };
        option2 = {
            // title : {
            //     text: '某站点用户访问来源',
            //     subtext: '纯属虚构',
            //     x:'center'
            // },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                // show: false,
                orient: 'vertical',
                left: 'left',
                data: name2
            },
            series : [
                {
                    name: '建筑年代',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data: data2,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    // label: {
                    //     normal: {
                    //         position: 'inner',
                    //         show:false
                    //     }
                    // },
                    // labelLine: {
                    //     normal: {
                    //         show: false
                    //     }
                    // }
                }
            ]
        };
        myChart2.setOption(option2)
    });

    $.ajax({
        url: '/getMaxArea'
    }).done(function (results) {
        option3 = {
            tooltip : {
                trigger: 'item',
                formatter: "面积,总价<br>{c}"
            },
            xAxis: {
                name: '面积/平方米'
            },
            yAxis: {
                name: '总价/万元'
            },
            series: [{
                symbolSize: 20,
                data: results,
                type: 'scatter'
            }]
        };
        myChart3 = echarts.init(document.getElementById('main3'));
        myChart3.setOption(option3)
    })
});