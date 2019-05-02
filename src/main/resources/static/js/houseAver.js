$.ajax({
    url: '/getCityAverPrice'
    // type: 'post',
    // dataType: 'json',
    // data: {
    //     cityName: $("#cityName").val(),
    //     status: 2
    // },
    // contentType: 'application/x-www-form-urlencoded'
}).done(function (results) {
    var labels = [], data = [];
    for (var i in results) {
        labels.push(results[i].name);
        data.push(results[i].count);
    }
    var ctx1 = document.getElementById("myAreaChart");
    // if (chart1 != null && chart1 != "" && chart1 != undefined) {
    //     chart1.destroy();
    // }
    chart1 = new Chart(ctx1, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '元/平方米',
                backgroundColor: "#00c5ff",
                hoverBackgroundColor: "#0098cb",
                borderColor: "#00a9cb",
                data: data
            }]
        }
    });
});

$.ajax({
    url: '/getMaxAverPrice'
}).done(function (results) {
    var labels = [], data = [];
    for (var i in results) {
        labels.push(results[i].name);
        data.push(results[i].count);
    }
    var ctx2 = document.getElementById("myAreaChart2");
    // if (chart1 != null && chart1 != "" && chart1 != undefined) {
    //     chart1.destroy();
    // }
    chart2 = new Chart(ctx2, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: '元/平方米',
                backgroundColor: "#0093af",
                hoverBackgroundColor: "#00c6e7",
                borderColor: "#00cbee",
                data: data
            }]
        }
    });
});