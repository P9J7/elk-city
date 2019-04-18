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
        labels.push(results[i].name);
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
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(220,20,60, 0.5)',
                    'rgba(255,140,0, 0.5)',
                    'rgba(219,112,147, 0.2)',
                    'rgba(255,0,255, 0.2)',
                    'rgba(123,104,238, 0.7)',
                    'rgba(0,191,255, 0.7)',
                    'rgba(0,250,154, 0.2)',
                    'rgb(153, 102, 255, 0.2)',
                    'rgb(102, 153, 153, 0.2)',
                    'rgb(51, 51, 153, 0.2)',
                    'rgb(153, 51, 102, 0.2)',
                    'rgb(255, 204, 153,0.2)'

                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(220,20,60, 1)',
                    'rgba(255,140,0, 1)',
                    'rgba(219,112,147, 1)',
                    'rgba(255,0,255, 1)',
                    'rgba(123,104,238, 1)',
                    'rgba(0,191,255, 1)',
                    'rgba(0,250,154, 1)',
                    'rgb(153, 102, 255, 1)',
                    'rgb(102, 153, 153,1)',
                    'rgb(51, 51, 153,1)',
                    'rgb(153, 51, 102,1)',
                    'rgb(255, 204, 153,1)'
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
        labels.push(results[i].name);
        data.push(results[i].count);
    };
    var ctx = document.getElementById("myHouseTypeChart");
    var myHouseTypeChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                label: '户型数量',
                backgroundColor: [
                    'red',
                    'yellow',
                    'blue',
                    'Maroon',
                    'Olive',
                    'Lime',
                    'Teal',
                    'Purple',
                    'Aque'
                ],
                data: data
            }]
        }
    });
});

var getAverPrice = $.ajax({
    url: '/getAverPrice',
    type: 'post',
    dataType: 'json',
    data: {
        cityName: $("#areaHelp").attr("href")+"Area"
    },
    contentType: 'application/x-www-form-urlencoded'
}).done(function (results) {
    var labels = [], data = [];
    for (var i in results) {
        labels.push(results[i].name);
        data.push(results[i].count);
    };
    var ctx = document.getElementById("areaAverChart");
    var myAreaChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '均价',
                data: data,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)'
            }]
        }
    });
});

var getConYear = $.ajax({
    url: '/getConYear',
    type: 'post',
    dataType: 'json',
    data: {
        cityName: $("#areaHelp").attr("href")
    },
    contentType: 'application/x-www-form-urlencoded'
}).done(function (results) {
    var labels = [], data = [];
    for (var i in results) {
        labels.push(results[i].name);
        data.push(results[i].count);
    };
    var ctx = document.getElementById("conYearChart");
    var myAreaChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '建成年代',
                data: data,
                backgroundColor: 'rgba(102, 0, 255, 0.2)',
                borderColor: 'rgba(102, 0, 255, 0.5)',
                pointBackgroundColor: "#eb0023",
                pointBorderColor: "#ff5010"
            }]
        }
    });
});


