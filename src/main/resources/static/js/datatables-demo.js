// Call the dataTables jQuery plugin
// "language": {
//     "lengthMenu": '每页显示<select>' + '<option value="10">10</option>'
//         + '<option value="20">20</option>'
//         + '<option value="30">30</option>'
//         + '<option value="40">40</option>'
//         + '<option value="50">50</option>' + '</select>条',
//     "paginate": {
//         "first": "首页",
//         "last": "尾页",
//         "previous": "上一页",
//         "next": "下一页"
//     },
//     "processing": "加载中...",  //DataTables载入数据时，是否显示‘进度’提示
//     "emptyTable": "暂无数据",
//     "info": "共 _PAGES_ 页  _TOTAL_ 条数据  ",
//     "infoEmpty": "暂无数据",
//     "emptyTable": "暂无要处理的数据...",  //表格中无数据
//     "search": "搜索:",
//     "infoFiltered": " —— 从  _MAX_ 条数据中筛选",
//     "zeroRecords": "没有找到记录"
//
// },
$(document).ready(function() {
  $('#dataTable').DataTable(
      {
          "sAjaxDataProp":"",
          "ajax": "/getAqiCount",
          "columns": [
              { "data": "cityName" ,"defaultContent": "0"},
              { "data": "cityCount","defaultContent": "0" },
              { "data": "qualityMap.严重污染","defaultContent": "0" },
              { "data": "qualityMap.重度污染","defaultContent": "0" },
              { "data": "qualityMap.中度污染","defaultContent": "0" },
              { "data": "qualityMap.轻度污染","defaultContent": "0" },
              { "data": "qualityMap.良","defaultContent": "0" },
              { "data": "qualityMap.优","defaultContent": "0" },
              { "data": "qualityMap.无","defaultContent": "0" }
          ],
          "order": [[ 1, "desc" ]]
      }
  )
});
