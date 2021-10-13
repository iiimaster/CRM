<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <script type="text/javascript">

        $(function () {

            // 1.a.加载市场活动全部列表信息
            // getActivitisByPageHelper();
            // 	 b.加载市场活动列表信息-分页实现

            // 2.全选/全不选
            $("#all").click(function () {
                // 获取全选框的状态
                let ck = $("#all").prop(`checked`)
                // alert(ck)

                // 单选框
                let $flag = $(".flag")
                // alert($flag.length)

                // 将全选框的状态作用于每个单选框
                for (let i = 0; i < $flag.length; i++) {
                    $(".flag")[i].checked = ck
                }
            })
            // 3.反选
            $(".flag").click(function () {

                // 复选框总个数
                let count = $(".flag").length

                // 选中的复选框的个数
                let ckTrue = $(".flag:checked").length

                if (ckTrue === count) {
                    $("#all").prop("checked", true)
                } else {
                    $("#all").prop("checked", false)
                }
            })


            // 加载时间控件，方便时间的输入
            $(".time").datetimepicker({
                minView: "month",
                language:  'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });


            // 4.创建市场活动数据
            $("#openCreateActivityModel").click(function () {
                $.ajax({
                    url:"workbench/activity/toSaveActivity.do",
                    data:{

                    },
                    type:"post",
                    dataType:"json",
                    success:function(result){
                        if (result.success){ // 查询成功
                            // 异步加载
                            let html = ""

                            $.each(result.data,function (i,n) {
                                // 将标签封装到字符串中
                                html += "<option value='"+n.id+"'>"+n.name+"</option>"
                            })

                            // 将html加载到页面中
                            $("#create-owner").html(html)

                            // 给下拉列表添加默认选项(已登录的用户)
                            $("#create-owner").val("${user.id}")

                            // 打开创建市场活动的模态窗口
                            $("#createActivityModal").modal("show")


                        }
                    }
                })
            })
            // 点击保存按钮，保存数据
            $("#saveActivityBtn").click(function () {

                // 获取要添加的数据
                let owner = $("#create-owner").val()
                let name = $("#create-name").val()
                let startDate = $("#create-startDate").val()
                let endDate = $("#create-endDate").val()
                let cost = $("#create-cost").val()
                let description = $("#create-describe").val()

                if (owner == ""){
                    alert("请选择所有者！！！")
                    return false;
                }

                if (name == ""){
                    alert("请输入市场活动名称！")
                    // $("#msg").html("请输入市场活动名称！")
                    return false;
                }

                // 发送ajax请求，保存数据
                $.ajax({
                    url:"workbench/activity/saveActivity.do",
                    data:{
                        "owner":owner,
                        "name":name,
                        "startDate":startDate,
                        "endDate":endDate,
                        "cost":cost,
                        "description":description
                    },
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        if (data.success){
                            window.location.href = "workbench/activity/getActivitisByPageHelper.do"
                        }else{
                            alert(data.msg)
                        }
                    }
                })
            })
            // 点击关闭按钮，关闭模态窗口，并清空模态窗口中的数据
            $("#closeSaveBtn").click(function () {

                $("#create-name").val("")
                $("#create-startDate").val("")
                $("#create-endDate").val("")
                $("#create-cost").val("")
                $("#create-describe").val("")

            })


            // 5.修改市场活动数据
            // 6.删除市场活动数据
            // 7.导入、导出市场活动数据
            // 8.搜索框功能实现

        });

    </script>
</head>
<body>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <div class="form-group ">
                        <label for="create-owner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner">

                            </select>
                        </div>
                        <label for="create-name" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name"><span style="color: red" id="msg"></span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startDate">
                        </div>
                        <label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endDate">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <!--data-dismiss="modal"-->
                <button id="closeSaveBtn" type="reset"  class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveActivityBtn" type="button" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner">
                                <%--   <c:forEach items="${users}" var="user">--%>
                                <%--       <option value="${user.id}">${user.name}</option>--%>
                                <%--   </c:forEach>--%>

                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost" value="5,000">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
            </div>
        </div>
    </div>
</div>

<!-- 导入市场活动的模态窗口 -->
<div class="modal fade" id="importActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
            </div>
            <div class="modal-body" style="height: 350px;">
                <div style="position: relative;top: 20px; left: 50px;">
                    请选择要上传的文件：<small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
                </div>
                <div style="position: relative;top: 40px; left: 50px;">
                    <input type="file" id="activityFile">
                </div>
                <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;">
                    <h3>重要提示</h3>
                    <ul>
                        <li>操作仅针对Excel，仅支持后缀名为XLS/XLSX的文件。</li>
                        <li>给定文件的第一行将视为字段名。</li>
                        <li>请确认您的文件大小不超过5MB。</li>
                        <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                        <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                        <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                        <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="startTime"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="endTime">
                    </div>
                </div>

                <button type="submit" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <!-- data-toggle="modal" data-target="#createActivityModal" -->
                <button id="openCreateActivityModel" type="button" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <!-- data-toggle="modal" data-target="#editActivityModal" -->
                <button id="openUpdateActivityModel" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button id="delActivity" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
            </div>
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal">
                    <span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）
                </button>
                <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）
                </button>
                <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span
                        class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）
                </button>
            </div>
        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="all"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${activities}" var="activity" varStatus="activityIndex">
                    <tr class="${(activityIndex.index+1) % 2 == 0 ? 'active':''}">
                        <td><input type="checkbox" class="flag"/></td>
                        <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/toDetail.do';">${activity.name}</a></td>
                        <td>${activity.owner}</td>
                        <td>${activity.startDate}</td>
                        <td>${activity.endDate}</td>
                    </tr>
                </c:forEach>
                <%--<tr class="active">
                    <td><input type="checkbox" /></td>
                    <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/toDetail.do';">发传单</a></td>
                    <td>zhangsan</td>
                    <td>2020-10-10</td>
                    <td>2020-10-20</td>
                </tr>--%>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div>
                <button type="button" class="btn btn-default" style="cursor: default;">共<b>${count}</b>条记录</button>
            </div>
            <div class="btn-group" style="position: relative;top: -34px; left: 110px;">
                <button type="button" class="btn btn-default" style="cursor: default;">显示</button>
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        ${pageSize}
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="workbench/activity/getActivitisByPageHelper.do?pageSize=8">8</a></li>
                        <li><a href="workbench/activity/getActivitisByPageHelper.do?pageSize=15">15</a></li>
                    </ul>
                </div>
                <button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
            </div>
            <div style="position: relative;top: -88px; left: 285px;">
                <nav>
                    <ul class="pagination">
                        <li>
                            <a href="workbench/activity/getActivitisByPageHelper.do?page=1&pageSize=${pageSize}">首页</a>
                        </li>
                        <c:if test="${pageNow > 1}">
                            <li><a href="workbench/activity/getActivitisByPageHelper.do?page=${pageNow - 1}&pageSize=${pageSize}">上一页</a></li>
                        </c:if>

                        <c:forEach begin="1" end="${pages}" var="page">
                            <li class="${page == pageNow ? 'active':''}">
                                <a href="workbench/activity/getActivitisByPageHelper.do?page=${page}&pageSize=${pageSize}">${page}</a>
                            </li>
                        </c:forEach>

                        <%--<li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">4</a></li>
                        <li><a href="#">5</a></li>--%>

                        <c:if  test="${pageNow < pages}">
                            <li><a href="workbench/activity/getActivitisByPageHelper.do?page=${pageNow + 1}&pageSize=${pageSize}">下一页</a></li>
                        </c:if>

                        <li >
                            <a href="workbench/activity/getActivitisByPageHelper.do?page=${pages}&pageSize=${pageSize}">末页</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

    </div>

</div>
</body>
</html>