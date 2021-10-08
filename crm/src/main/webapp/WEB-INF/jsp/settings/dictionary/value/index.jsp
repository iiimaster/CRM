<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <script>

        $(function () {
            // 1.全选/全不选
            $("#ckAll").click(function () {
                // 获取全选框的选中状态
                let ck = $("#ckAll").prop("checked")

                // alert()
                // 将全选框的选中状态加到每个复选框上
                let $flag = $(".flag")
                for (let i=0;i<$flag.length;i++){
                    $flag[i].checked = ck
                }
            })


            // 反选
            $(".flag").click(function () {
                // 获取所有复选框的状态
                // 总复选框的个数
                let num = $(".flag").length

                // 复选框状态为true的个数
                let ckTrue = $(".flag:checked").length

                if (num === ckTrue){
                    $("#ckAll").prop("checked",true)
                }else{
                    $("#ckAll").prop("checked",false)
                }
            })


            // 2.跳转到修改操作页面，edit.jsp
            $("#toEditValue").click(function () {
                let $flag = $(".flag:checked")

                if ($flag.length === 0){
                    alert("请选择要修改的数据")

                }else if ($flag.length > 1){
                    alert("只能选择一条数据")
                }else{
                    // 获取要修改的id
                    let id = $flag.val()
                    // 将数据传递到后台
                    window.location.href="settings/dictionary/value/toEditValue.do?id="+id
                }

            })


        })


    </script>
</head>
<body>

<div>
    <div style="position: relative; left: 30px; top: -10px;">
        <div class="page-header">
            <h3>字典值列表</h3>
        </div>
    </div>
</div>
<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
    <div class="btn-group" style="position: relative; top: 18%;">
        <button type="button" class="btn btn-primary"
                onclick="window.location.href='settings/dictionary/value/toValueSave.do'"><span
                class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button type="button" class="btn btn-default" id="toEditValue" ><span
                class="glyphicon glyphicon-edit"></span> 编辑
        </button>
        <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
    </div>
</div>
<div style="position: relative; left: 30px; top: 20px;">
    <table class="table table-hover" style=" height: 438px">
        <thead>
        <tr style="color: #B3B3B3;">
            <td><input type="checkbox" id="ckAll"/></td>
            <td>序号</td>
            <td>字典值</td>
            <td>文本</td>
            <td>排序号</td>
            <td>字典类型编码</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${values}" var="value" varStatus="idstatus">
            <tr class="${idstatus.index % 2==0 ? '':'active'}">
                <td><input type="checkbox" class="flag" value="${value.id}"/></td>
                <td>${idstatus.index + 1}</td>
                <td>${value.value}</td>
                <td>${value.text}</td>
                <td>${value.orderNo}</td>
                <td>${value.typeCode}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div align="center">

        <%-- 使用 pageHelper-bootstrap 实现分页功能  --%>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${pageNow>1}">
                    <li>
                        <a href="settings/dictionary/value/queryByPageHelper.do?page=${pageNow-1}"
                           aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${pages}" var="page">
                    <li><a href="settings/dictionary/value/queryByPageHelper.do?page=${page}">${page}</a></li>
                </c:forEach>
                <c:if test="${pageNow < pages}">
                    <li>
                    <li>
                        <a href="settings/dictionary/value/queryByPageHelper.do?page=${pageNow+1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

</body>
</html>