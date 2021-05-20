<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/bootstrap.min.css" />
    <script src="../js/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script type="text/javascript">

        $(function(){
            $("#productName").val("${productName}");
            $("#state").val("${state}");
            $("#productType").val("${productType}");
            $("#time").val("${time}");
            $("#search").click(function(){
                var productName = $("input[name='productName']").val();
                var state = $("input[name='state']").val();
                var productType = $("#productType option:selected").val();
                var time = $("input[name='time']").val();
                location.href="${pageContext.request.contextPath}/product?method=searchProductType&productName="+productName+"&state="+state+"&productType="+productType+"&time="+time;
            })
        })
    </script>
    <title>商品分类</title>
</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                商品类型
            </div>
            <center>
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <li class="${pageBean.currentPage==1?'disabled':''}">
                            <a  href="product?method=searchProductType&productName=${productName}&state=${state}&productType=${productType}&time=${time}&currentPage=${pageBean.currentPage-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <c:forEach begin="1" end="${pageBean.totalPage}" step="1" var="index">
                            <c:if test="${pageBean.currentPage==index}">
                                <li class="active"><a href="product?method=searchProductType&productName=${productName}&state=${state}&productType=${productType}&time=${time}&currentPage=${index}">${index}</a></li>
                            </c:if>
                            <c:if test="${pageBean.currentPage!=index}">
                                <li ><a href="product?method=searchProductType&productName=${productName}&state=${state}&productType=${productType}&time=${time}&currentPage=${index}">${index}</a></li>
                            </c:if>
                        </c:forEach>

                        <li class="${pageBean.currentPage==pageBean.totalPage?'disabled':''}">
                            <a href="product?method=searchProductType&productName=${productName}&state=${state}&productType=${productType}&time=${time}&currentPage=${pageBean.currentPage+1}" aria-label="Next">
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
                            <span>商品等级</span>
                            <input type="text" name="state" class="form-control">
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
                        <div class="form-group form-inline">
                            <span>商品名称</span>
                            <input type="text" name="productName" class="form-control">
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
                        <div class="form-group form-inline">
                            <span>商品类型</span>
                            <select name="productType" id="productType" class="productType form-control">
                                <option value="">----------</option>
                                <c:forEach items="${typeList}" var="type" varStatus="i">
                                    <option value="${type.tid}">${type.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
                        <div class="form-group form-inline">
                            <span>上架时间</span>
                            <input type="date" id="time" name="time" class="form-control">
                        </div>
                    </div>
                    <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
                        <button type="button" class="btn btn-primary" id="search"><span class="glyphicon glyphicon-search"></span></button>
                    </div>
                </div>
                <div style="height: 400px;">
                    <table id="tb_list" class="table table-striped table-hover table-bordered table-condensed">
                        <tr>
                            <td>序号</td><td>商品名称</td><td>价格</td><td>上架时间</td><td>热销指数</td><td>所属类型</td><td>操作</td>
                        </tr>
                        <c:forEach items="${pageBean.list}" var="gtype" varStatus="i">
                            <tr>
                                <td>${i.count}</td>
                                <td>${gtype.name}</td>
                                <td>${gtype.price}</td>
                                <td>${gtype.time}</td>
                                <td>${gtype.state}</td>
                                <td>${gtype.type.name}</td>
                                <td><a tabindex="0" id="example${gtype.pid}" class="btn btn-primary btn-xs"
                                       role="button" data-toggle="popover"
                                       data-trigger="focus"
                                       data-placement="left"
                                       data-content="${gtype.info}">描述</a>
                                    <script type="text/javascript">
                                        $(function(){
                                            $("#example${gtype.pid}").popover();
                                        })
                                    </script>
                                </td>
                            </tr>
                        </c:forEach>

                    </table>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>