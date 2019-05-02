var myChart;
$(document).ready(function () {
    $.ajax({
        url: '/getAqiObserve',
        method: 'POST',
        data: {
            city: $('#city').val()
        }
    }).done(function (results) {
        var option = {
            toolbox: {
                feature: {
                    dataZoom: {
                        yAxisIndex: false
                    },
                    saveAsImage: {
                        pixelRatio: 2
                    }
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                bottom: 90
            },
            dataZoom: [{
                type: 'inside'
            }, {
                type: 'slider'
            }],
            xAxis: {
                data: results.timeData,
                silent: false,
                splitLine: {
                    show: false
                },
                splitArea: {
                    show: false
                }
            },
            yAxis: {
                splitArea: {
                    show: false
                }
            },
            series: [{
                type: 'bar',
                data: results.aqiData,
                large: true
            }]
        };
        myChart = echarts.init(document.getElementById('main'));
        myChart.setOption(option)
    });
});

$("#search").click(function () {
    $.ajax({
        url: '/getAqiObserve',
        method: 'POST',
        data: {
            city: $('#city').val()
        }
    }).done(function (results) {
        var option = {
            toolbox: {
                feature: {
                    dataZoom: {
                        yAxisIndex: false
                    },
                    saveAsImage: {
                        pixelRatio: 2
                    }
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            grid: {
                bottom: 90
            },
            dataZoom: [{
                type: 'inside'
            }, {
                type: 'slider'
            }],
            xAxis: {
                data: results.timeData,
                silent: false,
                splitLine: {
                    show: false
                },
                splitArea: {
                    show: false
                }
            },
            yAxis: {
                splitArea: {
                    show: false
                }
            },
            series: [{
                type: 'bar',
                data: results.aqiData,
                large: true
            }]
        };
        if (myChart != null && myChart != "" && myChart != undefined) {
            myChart.dispose();
        }
        myChart = echarts.init(document.getElementById('main'));
        myChart.setOption(option)
    });
});
