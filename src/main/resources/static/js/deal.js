// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';
var getHouseType = $.ajax({
    url: '/getHouseType',
    type: 'post',
    dataType: 'json',
    data: {
        cityName: $("#areaHelp").attr("href"),
        status: 2
    },
    contentType: 'application/x-www-form-urlencoded'
}).done(function (results) {
    var labels = [], data = [];
    for (var i in results) {
        labels.push(results[i].name);
        data.push(results[i].count);
    }
    ;
    var ctx = document.getElementById("myHouseTypeChart");
    var myHouseTypeChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '户型数量',
                backgroundColor: "#7bdf15",
                hoverBackgroundColor: "#0ed929",
                borderColor: "#0cdf97",
                data: data
            }]
        }
    });
});

var getAreaCount = $.ajax({
    url: '/getMonthCount',
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
    }
    ;
    var ctx = document.getElementById("myAreaChart");
    var myAreaChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: '成交数',
                data: data,
                backgroundColor: "#f0fff5",
                borderColor: "#8775ff",
                pointBackgroundColor: "#36A2EB",
                pointBorderColor: "#fff"
            }]
        }
    });
});

var getAreaRange = $.ajax({
    url: '/getAreaRange',
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
    }
    ;
    var ctx = document.getElementById("areaRangeChart");
    var myAreaChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                label: '房屋面积/平米',
                data: data,
                backgroundColor: [
                    'red',
                    'yellow',
                    'blue',
                    'Maroon',
                    'Lime',
                    'Purple',
                    'Aque'
                ]
            }]
        }
    });
})

var getpriceRange = $.ajax({
    url: '/getPriceRange',
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
    }
    ;
    var ctx = document.getElementById("priceRangeChart");
    var myAreaChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '成交数',
                data: data,
                backgroundColor: "#df6775",
                hoverBackgroundColor: "#d9000c",
                borderColor: "#df0063",
            }]
        }
    });
})