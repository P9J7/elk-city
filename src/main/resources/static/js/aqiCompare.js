$(document).ready(function () {
    function getdiffdate(stime,etime){
        //初始化日期列表，数组
        var diffdate = new Array();
        var i=0;
        //开始日期小于等于结束日期,并循环
        while(stime<=etime){
            diffdate[i] = stime;

            //获取开始日期时间戳
            var stime_ts = new Date(stime).getTime();
            // console.log('当前日期：'+stime   +'当前时间戳：'+stime_ts);

            //增加一天时间戳后的日期
            var next_date = stime_ts + (24*60*60*1000);

            //拼接年月日，这里的月份会返回（0-11），所以要+1
            var next_dates_y = new Date(next_date).getFullYear()+'-';
            var next_dates_m = (new Date(next_date).getMonth()+1 < 10)?'0'+(new Date(next_date).getMonth()+1)+'-':(new Date(next_date).getMonth()+1)+'-';
            var next_dates_d = (new Date(next_date).getDate() < 10)?'0'+new Date(next_date).getDate():new Date(next_date).getDate();

            stime = next_dates_y+next_dates_m+next_dates_d;

            //增加数组key
            i++;
        }
        return diffdate;
    }

    var date = new Array();
    date = getdiffdate($('#date1').val(),$('#date2').val());
    $.ajax({
        url: '/getAqiCompare',
        method: 'POST',
        data: {
            city1: $('#city1').val(),
            city2: $('#city2').val(),
            date1: $('#date1').val(),
            date2: $('#date2').val(),
            type: 'aqi'
        }
    }).done(function (data) {
        var myChart = echarts.init(document.getElementById('main'));

        // for (var i in results) {
        //     data.push({
        //         name: results[i].cityName,
        //         value: results[i].cityCount
        //     });
        // }
        option = {
            title: {
                text: '城市AQI比较'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                // data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
                data:[$('#city1').val(),$('#city2').val()]
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
                // data: ['周一','周二','周三','周四','周五','周六','周日']
                 data: date
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: $('#city1').val(),
                    type:'line',
                    stack: '总量',
                    // data:[120, 132, 101, 134, 90, 230, 210]
                    data:data[0]
                },
                {
                    name: $('#city2').val(),
                    type: 'line',
                    stack: '总量',
                    // data:[220, 182, 191, 234, 290, 330, 310]
                    data: data[1]
                }
                // },
                // {
                //     name:'视频广告',
                //     type:'line',
                //     stack: '总量',
                //     data:[150, 232, 201, 154, 190, 330, 410]
                // },
                // {
                //     name:'直接访问',
                //     type:'line',
                //     stack: '总量',
                //     data:[320, 332, 301, 334, 390, 330, 320]
                // },
                // {
                //     name:'搜索引擎',
                //     type:'line',
                //     stack: '总量',
                //     data:[820, 932, 901, 934, 1290, 1330, 1320]
                // }
            ]
        };
        myChart.setOption(option);
    })
});