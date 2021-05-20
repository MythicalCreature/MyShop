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
<title>无效会员管理</title>
<script type="text/javascript">
    $(document).ready(function(){
        loadUser();
    });
    //连接servlet 获取 数据
    function loadUser(){
        $.ajax({
            url:"${pageContext.request.contextPath}/user?method=getUserList&invalid=true",
            method:"get",
            success:function(data){
                showMsg(data);
            },
            error:function(XMLHttpRequest,textStatus,errorThrown){
                alert("失败"+XMLHttpRequest.status+":"+textStatus+":"+errorThrown);
            }
        });
    }
    //显示用户信息
    function showMsg(data){
        var list = JSON.parse(data);
        $("#tb_list").html("<tr class='tr_head'><td>编号</td><td>邮箱</td><td>姓名</td><td>性别</td><td>操作</td></tr>");
        var i = 1;
        for(var u in list){
            //声明 tr  td  对象
            var tr = $("<tr></tr>");
            var td1 = $("<td>"+(i++)+"</td>");
            var td2 = $("<td>"+list[u].email+"</td>");
            var td3 = $("<td>"+list[u].username+"</td>");
            var td4 = $("<td>"+list[u].sex+"</td>");
            var td5 = $("<td><a href='javascript:delUser("+list[u].uid+")' class='btn btn-primary btn-xs'>删除</a></td>");
            //将td 添加到tr中
            tr.append(td1);
            tr.append(td2);
            tr.append(td3);
            tr.append(td4);
            tr.append(td5);
            $("#tb_list").append(tr);
        }
    }
    //删除用户
    function delUser(id){
        if(confirm("确认要删除吗?")){
            $.ajax({
                url:"${pageContext.request.contextPath}/user?method=deleteUser&uid="+id,
                method:"get",
                success:function(data){
                    loadUser();
                },
                error:function(XMLHttpRequest,textStatus,errorThrown){
                    alert("失败"+XMLHttpRequest.status+":"+textStatus+":"+errorThrown);
                }
            })
        }
    }
</script>
</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;margin-top: 5px;">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				已停用会员列表
			</div>
			<div class="panel-body">
				<table id="tb_list" class="table table-striped table-hover table-bordered">
				
				</table>

			</div>
		</div>
	</div>
</div>
</body>
</html>