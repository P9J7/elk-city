$("#search").click(function () {
    $('#dataTable').DataTable().destroy();
    $('#dataTable').DataTable(
        {
            "sAjaxDataProp":"",
            "retrieve": true,
            "aLengthMenu" : [25, 50, 100], //更改显示记录数选项
            "iDisplayLength" : 25, //默认显示的记录数
            "ajax": {
                "url": "/getAqiRank",
                "type": "POST",
                "data": {
                    year:$('#year').val(),
                    month:$('#month').val()
                    }
                },
            "columns": [
                { "data": "cityCount" ,"defaultContent": "0"},
                { "data": "cityName" ,"defaultContent": "0"},
                { "data": "qualityMap.aqi","defaultContent": "0" },
                { "data": "qualityMap.pm2_5","defaultContent": "0" },
                { "data": "qualityMap.pm10","defaultContent": "0" },
                { "data": "qualityMap.so2","defaultContent": "0" },
                { "data": "qualityMap.co","defaultContent": "0" },
                { "data": "qualityMap.no2","defaultContent": "0" },
                { "data": "qualityMap.o3","defaultContent": "0" }
            ],
            "order": [[ 2, "desc" ]],
            "fnInitComplete": function () {
                $('#yearMonthText').html($('#year').val() + '年' + $('#month').val() + '月')
            }
        })
});

$(document).ready(function () {
    $('#dataTable').DataTable(
        {
            "sAjaxDataProp":"",
            "retrieve": true,
            "aLengthMenu" : [25, 50, 100], //更改显示记录数选项
            "iDisplayLength" : 25, //默认显示的记录数
            "ajax": {
                "url": "/getAqiRank",
                "type": "POST",
                "data": {
                    year:2014,
                    month:1
                }
            },
            "columns": [
                { "data": "cityCount" ,"defaultContent": "0"},
                { "data": "cityName" ,"defaultContent": "0"},
                { "data": "qualityMap.aqi","defaultContent": "0" },
                { "data": "qualityMap.pm2_5","defaultContent": "0" },
                { "data": "qualityMap.pm10","defaultContent": "0" },
                { "data": "qualityMap.so2","defaultContent": "0" },
                { "data": "qualityMap.co","defaultContent": "0" },
                { "data": "qualityMap.no2","defaultContent": "0" },
                { "data": "qualityMap.o3","defaultContent": "0" }
            ],
            "order": [[ 2, "desc" ]],
            "fnInitComplete": function () {
                $('#yearMonthText').html('2014年1月')
            }
        }
    )
});