<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 用户中心 <span class="c-gray en">&gt;</span> 用户管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
  <div class="text-c">
  <form class="form form-horizontal" action="${pageContext.request.contextPath }/serveltActionBank.action?methodName=queryAllOrders" method="post">
    <input type="text" class="input-text" style="width:250px" placeholder="输入用户订单ID、名称、电话、地址" id="" name="information" value="">
    <button type="submit" class="btn btn-success" id="" name="">
    <i class="icon-search"></i> 搜用户</button>
    </form>
  </div>
  <table class="table table-border table-bordered table-bg">
		<thead>
			<tr>
				<th scope="col" colspan="10">订单列表</th>
			</tr>
			<tr class="text-c">
				<th width="25">ID</th>
				<th width="80">订单ID</th>
				<th width="40">用户名</th>
				<th width="90">手机</th>
				<th width="40">订单金额</th>
				<th width="100">收货地址</th>
				<th width="130">订单时间</th>
				<th width="25">订单状态</th>
				<th width="50">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${requestScope.allMap.ordersList }" var="orders"  varStatus="status">
			<tr class="text-c">
				<td>${status.count}</td>
				<td>${orders.order_id}</td>
				<td>${orders.user_name}</td>
				<td>${orders.user_phone}</td>
				<td>${orders.money}</td>
				<td>${orders.address}</td>
				<td>${orders.ordertime}</td>
				<td>${orders.state}</td>
				<td class="td-manage">
				<a style="text-decoration:none" href="${pageContext.request.contextPath }/serveltActionBank.action?methodName=viewOrderDetails&order_id=${orders.order_id}" title="查看详情">
				<i class="Hui-iconfont">查看详情</i></a>
				<c:if test="${orders.state==1}">
				<a title="发货" href="${pageContext.request.contextPath }/serveltActionBank.action?methodName=modifyState&order_id=${orders.order_id}&state=${orders.state}" class="ml-5" style="text-decoration:none" onclick="deliverGoods()">
				<i class="Hui-iconfont">订单发货</i>
				</a> 
			    </c:if>
			    <c:if test="${orders.state==3}">
				<a title="订单完成" href="${pageContext.request.contextPath }/serveltActionBank.action?methodName=modifyState&order_id=${orders.order_id}&state=${orders.state}" class="ml-5" style="text-decoration:none"onclick="orderFulfillment()">
				<i class="Hui-iconfont">订单完成</i></a> 
				</c:if>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
  <div id="pageNav" class="pageNav"></div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
window.onload = (function(){
    // optional set
    pageNav.pre="&lt;上一页";
    pageNav.next="下一页&gt;";
    // p,当前页码,pn,总页面
    pageNav.fn = function(p,pn){$("#pageinfo").text("当前页:"+p+" 总页: "+pn);};
    //重写分页状态,跳到第三页,总页33页
    //pageNav.go(1,13);
});
$('.table-sort').dataTable({
	"lengthMenu":false,//显示数量选择 
	"bFilter": false,//过滤功能
	"bPaginate": false,//翻页信息
	"bInfo": false,//数量信息
	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
	"bStateSave": true,//状态保存
	"aoColumnDefs": [
	  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
	  {"orderable":false,"aTargets":[0,8,9]}// 制定列不参与排序
	]
});
function deliverGoods()
{
	alert("发货完成")
};
function orderFulfillment()
{
	alert("订单完成")
};
</script>
</body>
</html>

