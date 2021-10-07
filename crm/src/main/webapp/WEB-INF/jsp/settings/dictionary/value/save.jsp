<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

	<script>
		$(function () {

			// 字典类型校验
			$("#dvTypeCode").blur(function () {
				let type = $("#dvTypeCode").val()

				console.log(type)
				if (type==null|| type==""){
					$("#dtMsg").html("请选择字典类型编码.")
				}else{
					$("#dtMsg").html("")
				}
			})
			// 字典值校验
			$("#dvValue").blur(function (){
				let value = $("#dvValue").val()
				console.log(value)

				if (value==null|| value==""){
					$("#dvMsg").html("请输入字典值.")
				}else
					$("#dvMsg").html("")
			})

			// 添加操作
			$("#valueSave").click(function (){

				let typeCode = $.trim($("#dvTypeCode").val())
				let dvValue = $.trim($("#dvValue").val())
				let dvText = $.trim($("#dvText").val())
				let dvOrderNo = $.trim($("#dvOrderNo").val())

				if (typeCode!=null && dvValue!=null){
					$.ajax({
						url:"settings/dictionary/value/saveValue.do",
						data:{
							"typeCode":typeCode,
							"value":dvValue,
							"text":dvText,
							"orderNo":dvOrderNo
						},
						type:"post",
						dataType:"json",
						success:function(data){
							if (data.success){
								window.location.href="settings/dictionary/value/queryByPageHelper.do"
							}else{
								$("#errorMsg").html(data.msg)
								$("#msgDisplay").show()
							}
						}
					})
				}
			})



		})
	</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典值</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="valueSave">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>

	<%--隐藏域--%>
	<div id="msgDisplay" class="alert alert-danger alert-dismissible" role="alert" align="center"  style="display: none">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<strong>ERROR!</strong>&nbsp;&nbsp;&nbsp;<span id="errorMsg"></span>
	</div>
	<form class="form-horizontal" role="form">
					
		<div class="form-group">
			<label for="dvTypeCode" class="col-sm-2 control-label">字典类型编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="dvTypeCode" style="width: 200%;">
					<option></option>
					<c:forEach items="${types}" var="type">
						<option value="${type.code}">${type.name}</option>
					</c:forEach>
				</select>
				<span style="color: red" id="dtMsg"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="dvValue" class="col-sm-2 control-label">字典值<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="dvValue"  style="width: 200%;">
				<span style="color: red" id="dvMsg"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="dvText" class="col-sm-2 control-label">文本</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="dvText" style="width: 200%;">
			</div>
		</div>
		
		<div class="form-group">
			<label for="dvOrderNo" class="col-sm-2 control-label">排序号</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="dvOrderNo" style="width: 200%;">
			</div>
		</div>

	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>