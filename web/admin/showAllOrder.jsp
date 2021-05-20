<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台 订单列表</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script type="text/javascript">
	function sendOrder(id){
		location.href = "${pageContext.request.contextPath}/orders?method=sendOrder&oid="+id;
	}
	$(function(){
	    $("#username").val("${username}");
        $("#ostate").val(${ostate});
        $("#search").click(function(){
			var username = $("input[name='username']").val();
			var state = $("select[name='orderState'] option:selected").val();
			location.href="${pageContext.request.contextPath}/orders?method=searchOrder&username="+username+"&ostate="+state;
		})
	})
</script>
</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;margin-top: 5px;">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				订单列表
			</div>
            <center>
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li class="${pageBean.currentPage==1?'disabled':''}">
                            <a  href="orders?method=searchOrder&username=${username}&ostate=${ostate}&currentPage=${pageBean.currentPage-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach begin="1" end="${pageBean.totalPage}" step="1" var="index">
                            <c:if test="${pageBean.currentPage==index}">
                                <li class="active"><a href="orders?method=searchOrder&username=${username}&ostate=${ostate}&currentPage=${index}">${index}</a></li>
                            </c:if>

                            <c:if test="${pageBean.currentPage!=index}">
                                <li ><a href="orders?method=searchOrder&username=${username}&ostate=${ostate}&currentPage=${index}">${index}</a></li>
                            </c:if>
                        </c:forEach>

                        <li class="${pageBean.currentPage==pageBean.totalPage?'disabled':''}">
                            <a href="orders?method=searchOrder&username=${username}&ostate=${ostate}&currentPage=${pageBean.currentPage+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </center>
			<div class="panel-body">
				<div class="row">
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>用户姓名</span>
							<input type="text" name="username" id="username" class="username form-control">
						</div>
					</div>
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>订单状态</span>
							<select name="orderState" id="ostate" class="ostate form-control">
								<option value="">----------</option>
                                <option value="-1">订单交易失败</option>
								<option value="1">未支付</option>
								<option value="2">已支付,待发货</option>
								<option value="3">已发货,待收货</option>
                                <option value="4">订单完成</option>
							</select>
						</div>
					</div>
					<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
						<button type="button" class="btn btn-primary" id="search"><span class="glyphicon glyphicon-search"></span></button>
					</div>
				</div>
				
				<table id="tb_list" class="table table-striped table-hover table-bordered table-condensed">
					<tr>
						<td>序号</td>
						<td>订单编号</td>
						<td>总金额</td>
						<td>订单状态</td>
						<td>订单时间</td>
						<td>用户名</td>
						<td>操作</td>
					</tr>
					<c:forEach items="${pageBean.list}" var="order" varStatus="i">
					<tr>
						<td>${i.count}</td>
						<td>${order.oid}</td>
						<td>${order.count}</td>
						<td>
                            <c:if test="${order.ostate eq -1}">
                                订单交易<br>失败
                            </c:if>
							<c:if test="${order.ostate eq 1}">
								未支付
							</c:if>
							<c:if test="${order.ostate eq 2}">
								已支付,<br>待发货
							</c:if>
							<c:if test="${order.ostate eq 3}">
								已发货,<br>待收货
							</c:if>
							<c:if test="${order.ostate eq 4}">
								订单完成
							</c:if>
						</td>
						<td>${order.time}</td>
						<td>${order.user.username}</td>
						<td>
							<c:if test="${order.ostate eq 2}">
								<button type="button" class="btn btn-danger btn-sm" onclick="sendOrder('${order.oid}')">&nbsp;&nbsp;&nbsp;&nbsp;发货&nbsp;&nbsp;&nbsp;&nbsp;</button>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>

			</div>
		</div>
	</div>
</div>
</body>
</html>