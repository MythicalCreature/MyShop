<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>购物车</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript">
        function check() {
            var count = $(".j-checkbox:checked").length;
            console.log(count);
            if(count==0){
                window.alert("请至少选中一个");
                return false;
            }
        }
        $(function () {
            $(".checkAll").change(function(){
                $(".j-checkbox").prop("checked",$(this).prop("checked"));
                getSum();
            });
            $(".j-checkbox").change(function(){
                //$(".j-checkbox:checked").length这个是被选中的小复选框的个数
                //$(".j-checkbox").length这个是所有的小复选框的个数
                if($(".j-checkbox:checked").length==$(".j-checkbox").length){
                    $(".checkAll").prop("checked",true);
                }else{
                    $(".checkAll").prop("checked",false);
                }
                getSum();
            });
            //5.计算总计和总额模块
            getSum();
            function getSum(){
                var count = 0;//计算总件数
                var money = 0;//计算总价数
                $(".j-checkbox:checked").parents("th").siblings("th").find(".txt").each(function(i,ele){
                    count +=parseInt($(ele).val());
                });
                $(".j-checkbox:checked").parents("th").siblings(".sum").each(function(i,ele){
                    money += parseFloat($(ele).text().substr(1));
                });
                $(".num").text(count);
                $(".total").text("￥"+money.toFixed(2));
            }
        });
        function sub(cid,num,price){
            if (num == 1){
                if (confirm("当前购物车只有一条商品，是否要移除！")) {
                    location.href = "cart?method=delete&cid="+cid;
                }
            }else{
                num--;
                location.href = "cart?method=update&cid="+cid+"&num="+num+"&price="+price;
            }
        }
        function add(cid,num,price){
            num++;
            location.href = "cart?method=update&cid="+cid+"&num="+num+"&price="+price;
        }
        function delCart(cid){
            if (confirm("是否要删除购物车数据！")){
                location.href = "cart?method=delete&cid="+cid;
            }
        }
        function clearCart(uid){
            if (confirm("是否要清空购物车！")) {
                location.href = "cart?method=clear&uid="+uid;
            }
        }
    </script>
</head>
<body style="background-color:#f5f5f5">
<%@ include file="header.jsp"%>
<form action="${pageContext.request.contextPath}/orders?method=preView&uid=${loginUser.uid}" method="post">
<div class="container" style="background-color: white;">
    <div class="row" style="margin-left: 40px">
        <h3>我的购物车<small>温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算</small></h3>
    </div>
    <div class="row" style="margin-top: 40px;">

        <c:if test="${empty list}">
            <h3>购物车空空如也！快去购物车吧！</h3>
        </c:if>
        <c:if test="${!empty list}">
            <div class="col-md-10 col-md-offset-1">
                <table class="table table-bordered table-striped table-hover">
                    <tr>
                        <th><input type="checkbox" class="checkAll">全选</th>
                        <th>序号</th>
                        <th>商品名称</th>
                        <th>价格</th>
                        <th>数量</th>
                        <th>小计</th>
                        <th>操作</th>
                    </tr>
                    <c:set value="0" var="sum"></c:set>
                    <c:forEach items="${list}" var="c" varStatus="i">
                        <tr>
                            <th><input type="checkbox" class="j-checkbox" name="product" value="${c.product.pid}"/></th>
                            <th>${i.count}</th>
                            <th>${c.product.name}</th>
                            <th>${c.product.price}</th>
                            <th width="100px">
                                <div class="input-group">
		 						<span class="input-group-btn">
		 							<button class="btn btn-default" type="button" onclick="sub(${c.cid},${c.num},${c.product.price})">-</button>
		 						</span>
                                    <input type="text" class="form-control txt" id="num_count${i.count}" value="${c.num}" readonly style="width:40px">
                                    <span class="input-group-btn">
		 							<button class="btn btn-default" type="button" onclick="add(${c.cid},${c.num},${c.product.price})">+</button>
		 						</span>
                                </div>
                            </th>
                            <th class="sum">¥&nbsp;${c.count}</th>
                            <th>
                                <button type="button" class="btn btn-default" onclick="delCart(${c.cid})">删除</button>
                            </th>
                        </tr>
                        <c:set var="sum" value="${sum+c.count}"></c:set>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </div>
    <hr>
    <div class="row">
        <div class="pull-right" style="margin-right: 40px;">
            <div style="margin-bottom: 20px;">
                商品数量总计：<span id="num" class="text-danger num"><b>￥&nbsp;&nbsp;0</b></span>
                商品金额总计：<span id="total" class="text-danger total"><b>￥&nbsp;&nbsp;0</b></span>
            </div>
            <div>
                <a id="removeAllProduct" href="javascript:clearCart(0)"   onclick="clearCart(${loginUser.uid})" class="btn btn-default btn-lg">清空购物车</a>
                &nbsp;&nbsp;
                <input type="submit" value="添加收货地址" onclick="return check()" class="btn  btn-danger btn-lg"/>
            </div>
        </div>
    </div>
</div>
</form>

<!-- 底部 -->
<%@ include file="footer.jsp"%>

</body>
</html>