// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';


var getAreaCount = $.ajax({
    url: '/getAreaCount',
    type: 'post',
    dataType: 'json',
    data: {
        cityName: $("#areaHelp").attr("href")+"Area"
    },
    contentType: 'application/x-www-form-urlencoded'
}).done(function (results) {
    var labels = [], data = [];
    for (var i in results) {
        labels.push(results[i].areaName);
        data.push(results[i].count);
    };
    var ctx = document.getElementById("myAreaChart");
    var myAreaChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                label: '数据量',
                data: data,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)'
                ]
            }]
        }
    });
});
var getHouseType = $.ajax({
    url: '/getHouseType',
    type: 'post',
    dataType: 'json',
    data: {
        cityName: $("#areaHelp").attr("href"),
        status: 1
    },
    contentType: 'application/x-www-form-urlencoded'
}).done(function (results) {
    var labels = [], data = [];
    for (var i in results) {
        labels.push(results[i].houseType);
        data.push(results[i].count);
    };
    var ctx = document.getElementById("myHouseTypeChart");
    var myHouseTypeChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '户型数量',
                backgroundColor: "#4e73df",
                hoverBackgroundColor: "#2e59d9",
                borderColor: "#4e73df",
                data: data
            }]
        }
    });
});


