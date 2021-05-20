<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../css/bootstrap.min.css"/>
    <style>
        #box{
            width: 300px;
            height: 25px;
            overflow: hidden;
        }
    </style>
    <script src="../js/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script type="text/javascript">
        //在input file内容改变的时候触发事件
        $('#filed').change(function(){
            //获取input file的files文件数组;
            //$('#filed')获取的是jQuery对象，.get(0)转为原生对象;
            //这边默认只能选一个，但是存放形式仍然是数组，所以取第一个元素使用[0];
            var file = $('#filed').get(0).files[0];
            //创建用来读取此文件的对象
            var reader = new FileReader();
            //使用该对象读取file文件
            reader.readAsDataURL(file);
            //读取文件成功后执行的方法函数
            reader.onload=function(e){
                //读取成功后返回的一个参数e，整个的一个进度事件
                console.log(e);
                //选择所要显示图片的img，要赋值给img的src就是e中target下result里面
                //的base64编码格式的地址
                $('#imgshow').get(0).src = e.target.result;
            }
        })
        $(document).ready(function () {
            var time = new Date();
            var day = ("0" + time.getDate()).slice(-2);
            var month = ("0" + (time.getMonth() + 1)).slice(-2);
            var today = time.getFullYear() + "-" + (month) + "-" + (day);
            $('#time').val(today);
        })
    </script>
    <title>商品添加页面</title>
</head>
<body>
<div class="row" style="margin-left: 20px;">
    <form action="${pageContext.request.contextPath }/product?method=add" method="post" >
        <div>
            <h3>新增商品</h3>
        </div>
        <hr/>
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group form-inline">
                    <label>名称:</label>
                    <input type="text" name="name" class="form-control"/>
                </div>
                <div class="form-group form-inline">
                    <label>时间:</label>
                    <input type="date" name="time" id="time" class="form-control"/>
                </div>
                <div class="form-group form-inline">
                    <label>热销指数:</label>
                    <input type="text" name="state" class="form-control"/>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group form-inline">
                    <label>价格:</label>
                    <input type="text" name="price" class="form-control"/>
                </div>
                <div class="form-group form-inline">
                    <label>分类:</label>
                    <select name="productType" id="productType" class="productType form-control">
                        <option value="">----------</option>
                        <c:forEach items="${typeList}" var="type" varStatus="i">
                            <option value="${type.tid}">${type.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-10">
                <div class="form-group form-inline">
                    <label>商品图片</label>
                    <div id="box">
                        <input id="filed" type="file" name="image" accept="image/*"/>
                    </div>
                </div>
                <div class="form-group ">
                    <label>商品简介</label>
                    <textarea name="info" class="form-control" rows="2"></textarea>
                </div>
                <div class="form-group form-inline">
                    <input type="submit" value="添加" class="btn btn-primary"/>
                    <input type="reset" value="重置" class="btn btn-default"/>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>