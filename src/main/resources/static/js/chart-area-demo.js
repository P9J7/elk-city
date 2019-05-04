// Set new default font family and font color to mimic Bootstrap's default styling
// Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
// Chart.defaults.global.defaultFontColor = '#858796';


// var getCityCount = $.ajax({
//     url: '/getCityCount',
//     dataType: 'json'
// }).done(function (results) {
//     var labels = [], data = [];
//     for (var i in results) {
//         labels.push(results[i].name);
//         data.push(results[i].count);
//     };
//     var ctx = document.getElementById("myAreaChart");
//     var myChart = new Chart(ctx, {
//         type: 'pie',
//         data: {
//             labels: labels,
//             datasets: [{
//                 label: '数据量',
//                 data: data,
//                 backgroundColor: [
//                     'rgba(255, 99, 132, 0.5)',
//                     'rgba(54, 162, 235, 0.5)',
//                     'rgba(255, 206, 86, 0.5)',
//                     'rgba(75, 192, 192, 0.5)',
//                     'rgba(255, 155, 235, 0.5)'
//                 ],
//                 borderColor: [
//                     'rgba(255, 99, 132, 1)',
//                     'rgba(54, 162, 235, 1)',
//                     'rgba(255, 206, 86, 1)',
//                     'rgba(75, 192, 192, 1)',
//                     'rgba(255, 155, 235, 1)'
//                 ]
//             }]
//         }
//     });
// });

$.ajax({
    url: '/getCityCount'
}).done(function (results) {
    var data = [];
    var name = [];
    var myChart = echarts.init(document.getElementById('main'));
    for (var i in results) {
        name.push(results[i].name);
        data.push({
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
            data: name
        },
        series : [
            {
                name: '数据量',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data: data,
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
    myChart.setOption(option2)
});
