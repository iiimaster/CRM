<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script>
		$(function () {
			// 页面加载完成后加载的代码，
			// 给登录按钮绑定事件，在点击登录按钮时，发送ajax请求
			$("#loginBtn").click(function () {
				// 获取用户名和密码
				var loginAct = $.trim($("#loginAct").val())
				var loginPwd = $.trim($("#loginPwd").val())
				// console.log(loginAct+":::>>"+loginPwd)

				// 校验
				if (loginAct == ''){
					$("#msg").html("请输入用户名")
					return false
				}
				if (loginPwd == ''){
					$("#msg").html("请输入密码")
					return false
				}
				// 10天免登录标记
				var flag = $("#flag").val()

				$.ajax({
					url:"settings/user/login.do",
					data:{
						"loginAct":loginAct,
						"loginPwd":loginPwd,
						"flag":flag
					},
					type:"post",
					dataType:"json",
					success:function (data) {
						if(data.success){
							// 测试的时候可以这样写
							// $("#msg").html(data.msg)
							//跳转到首页面
							window.location.href = "workbench/toIndex.do";
						}else{
							$("#msg").html(data.msg)
						}
					}
				})
			})

			//10天免登录操作
			// 给复选框添加点击事件
			$("#loginFlag").click(function () {
				// 获取复选框状态
				var flag = $("#loginFlag").prop("checked")

				if (flag){//选中标记
					$("#flag").val("select")
				}else{//未选中
					$("#flag").val("")
				}
				// alert($("#flag").val())
			})
		})
	</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2019&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form id="loginForm" action="settings/user/login.do" method="post" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" id="loginAct" type="text" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" id="loginPwd" type="password" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<input id="loginFlag" type="checkbox" name="autoLogin"> 十天内免登录
							<input type="hidden" id="flag">
						</label>
						&nbsp;&nbsp;
						<span id="msg" style="color: #ff0000"></span>
					</div>
					<button id="loginBtn" type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>