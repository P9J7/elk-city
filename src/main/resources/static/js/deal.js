// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';


var chart1;
var chart2;
var chart3;
var chart4;

function getChart() {
    $.ajax({
        url: '/getHouseType',
        type: 'post',
        dataType: 'json',
        data: {
            cityName: $("#cityName").val(),
            status: 2
        },
        contentType: 'application/x-www-form-urlencoded'
    }).done(function (results) {
        var labels = [], data = [];
        for (var i in results) {
            labels.push(results[i].name);
            data.push(results[i].count);
        }
        var ctx1 = document.getElementById("myHouseTypeChart");
        if (chart1 != null && chart1 != "" && chart1 != undefined) {
            chart1.destroy();
        }
        chart1 = new Chart(ctx1, {
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

    $.ajax({
        url: '/getMonthCount',
        type: 'post',
        dataType: 'json',
        data: {
            cityName: $("#cityName").val()
        },
        contentType: 'application/x-www-form-urlencoded'
    }).done(function (results) {
        var labels = [], data = [];
        for (var i in results) {
            labels.push(results[i].name);
            data.push(results[i].count);
        }
        ;
        var ctx2 = document.getElementById("myAreaChart");
        if (chart2 != null && chart2 != "" && chart2 != undefined) {
            chart2.destroy();
        }
        chart2 = new Chart(ctx2, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: '成交数',
                    data: data,
                    backgroundColor: "rgba(240,245,255,0.7)",
                    borderColor: "#8775ff",
                    pointBackgroundColor: "#36A2EB",
                    pointBorderColor: "#fff"
                }]
            }
        });
    });

    $.ajax({
        url: '/getAreaRange',
        type: 'post',
        dataType: 'json',
        data: {
            cityName: $("#cityName").val()
        },
        contentType: 'application/x-www-form-urlencoded'
    }).done(function (results) {
        var labels = [], data = [];
        for (var i in results) {
            labels.push(results[i].name);
            data.push(results[i].count);
        }
        var ctx3 = document.getElementById("areaRangeChart");
        if (chart3 != null && chart3 != "" && chart3 != undefined) {
            chart3.destroy();
        }
        chart3 = new Chart(ctx3, {
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
    });


    $.ajax({
        url: '/getPriceRange',
        type: 'post',
        dataType: 'json',
        data: {
            cityName: $("#cityName").val()
        },
        contentType: 'application/x-www-form-urlencoded'
    }).done(function (results) {
        var labels = [], data = [];
        for (var i in results) {
            labels.push(results[i].name);
            data.push(results[i].count);
        }
        var ctx4 = document.getElementById("priceRangeChart");
        if (chart4 != null && chart4 != "" && chart4 != undefined) {
            chart4.destroy();
        }
        chart4 = new Chart(ctx4, {
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
    });
}

$("#search").click(function () {
    getChart();
});

$(document).ready(function () {
    getChart();
});