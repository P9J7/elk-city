function getdiffdate(stime, etime) {
    //初始化日期列表，数组
    var diffdate = new Array();
    var i = 0;
    //开始日期小于等于结束日期,并循环
    while (stime <= etime) {
        diffdate[i] = stime;

        //获取开始日期时间戳
        var stime_ts = new Date(stime).getTime();
        // console.log('当前日期：'+stime   +'当前时间戳：'+stime_ts);

        //增加一天时间戳后的日期
        var next_date = stime_ts + (24 * 60 * 60 * 1000);

        //拼接年月日，这里的月份会返回（0-11），所以要+1
        var next_dates_y = new Date(next_date).getFullYear() + '-';
        var next_dates_m = (new Date(next_date).getMonth() + 1 < 10) ? '0' + (new Date(next_date).getMonth() + 1) + '-' : (new Date(next_date).getMonth() + 1) + '-';
        var next_dates_d = (new Date(next_date).getDate() < 10) ? '0' + new Date(next_date).getDate() : new Date(next_date).getDate();

        stime = next_dates_y + next_dates_m + next_dates_d;

        //增加数组key
        i++;
    }
    return diffdate;
}

function flush() {
    var date = new Array();
    date = getdiffdate($('#date1').val(), $('#date2').val());
    $.ajax({
        url: '/getAqiCompare',
        method: 'POST',
        data: {
            city1: $('#city1').val(),
            city2: $('#city2').val(),
            date1: $('#date1').val(),
            date2: $('#date2').val()
        }
    }).done(function (data) {
        var myChart = echarts.init(document.getElementById('main'));
        option = {
            title: {
                text: '城市AQI比较'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: [$('#city1').val(), $('#city2').val()]
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
                data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: $('#city1').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[0]
                },
                {
                    name: $('#city2').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[1]
                }
            ]
        };
        myChart.setOption(option);
        var myChart1 = echarts.init(document.getElementById('main1'));
        option = {
            title: {
                text: '城市PM2.5比较'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: [$('#city1').val(), $('#city2').val()]
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
                data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: $('#city1').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[2]
                },
                {
                    name: $('#city2').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[3]
                }
            ]
        };
        myChart1.setOption(option);
        var myChart2 = echarts.init(document.getElementById('main2'));
        option = {
            title: {
                text: '城市PM10比较'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: [$('#city1').val(), $('#city2').val()]
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
                data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: $('#city1').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[4]
                },
                {
                    name: $('#city2').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[5]
                }
            ]
        };
        myChart2.setOption(option);
        var myChart3 = echarts.init(document.getElementById('main3'));
        option = {
            title: {
                text: '城市CO比较'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: [$('#city1').val(), $('#city2').val()]
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
                data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: $('#city1').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[6]
                },
                {
                    name: $('#city2').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[7]
                }
            ]
        };
        myChart3.setOption(option);
        var myChart4 = echarts.init(document.getElementById('main4'));
        option = {
            title: {
                text: '城市NO2比较'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: [$('#city1').val(), $('#city2').val()]
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
                data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: $('#city1').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[8]
                },
                {
                    name: $('#city2').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[9]
                }
            ]
        };
        myChart4.setOption(option);
        var myChart5 = echarts.init(document.getElementById('main5'));
        option = {
            title: {
                text: '城市SO2比较'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: [$('#city1').val(), $('#city2').val()]
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
                data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: $('#city1').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[10]
                },
                {
                    name: $('#city2').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[11]
                }
            ]
        };
        myChart5.setOption(option);
        var myChart6 = echarts.init(document.getElementById('main6'));
        option = {
            title: {
                text: '城市O3比较'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: [$('#city1').val(), $('#city2').val()]
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
                data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: $('#city1').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[12]
                },
                {
                    name: $('#city2').val(),
                    type: 'line',
                    stack: '总量',
                    data: data[13]
                }
            ]
        };
        myChart6.setOption(option);
    })
}

//todo 必须加function才能只调用一次是什么鬼
$('#search').click(function () {
        flush();
    }
);

$(document).ready(
    flush()
);