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
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

    <script type="text/javascript">

        //默认情况下取消和保存按钮是隐藏的
        var cancelAndSaveBtnDefault = true;

        $(function () {
            $("#remark").focus(function () {
                if (cancelAndSaveBtnDefault) {
                    //设置remarkDiv的高度为130px
                    $("#remarkDiv").css("height", "130px");
                    //显示
                    $("#cancelAndSaveBtn").show("2000");
                    cancelAndSaveBtnDefault = false;
                }
            });

            $("#cancelBtn").click(function () {
                //显示
                $("#cancelAndSaveBtn").hide();
                //设置remarkDiv的高度为130px
                $("#remarkDiv").css("height", "90px");
                cancelAndSaveBtnDefault = true;
            });

            $(".remarkDiv").mouseover(function () {
                $(this).children("div").children("div").show();
            });

            $(".remarkDiv").mouseout(function () {
                $(this).children("div").children("div").hide();
            });

            $(".myHref").mouseover(function () {
                $(this).children("span").css("color", "red");
            });

            $(".myHref").mouseout(function () {
                $(this).children("span").css("color", "#E6E6E6");
            });


            // 1.查询备注信息列表
            getActivityRemarkList();


            // 2.每条备注 的 编辑、删除 按钮的显示与消失
            // 给每条备注添加一个鼠标移入，移出的事件
            $("#remarkBody").on("mouseover",".remarkDiv",function(){ // 给 .remarkDiv 一个鼠标移入事件
                // 让 .remarkDiv 标签下的第二个div z执行 显示 操作
                $(this).children("div").children("div").show();
            })
            $("#remarkBody").on("mouseout",".remarkDiv",function(){ // 给 .remarkDiv 一个鼠标移出事件
                // 让 .remarkDiv 标签下的第二个div z执行 隐藏 操作
                $(this).children("div").children("div").hide();
            })

            // 3.添加备注操作
            $("#saveRemarkBtn").click(function () {
                // 获取文本框的值(备注信息)
                let noteContent = $("#remark").val()

                if (noteContent == ""){
                    alert("请输入备注信息")
                    return false;
                }

                $.ajax({
                    url:"workbench/activity/saveActivityRemark.do",
                    data:{
                        "activityId":"${activity.id}",
                        "noteContent":noteContent
                    },
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        if (data.success){
                            window.location.href = "workbench/activity/toDetail.do?id=${activity.id}"
                        }else {
                            alert(data.msg)
                        }
                    }
                })
            })

            // 4.修改备注操作,function updateRemark(id){}
            // 5.删除备注操作,function deleteRemark(id){}




        });

        // 备注信息列表查询
        function getActivityRemarkList() {
            $.ajax({
                url: "workbench/activity/getActivityRemarkList.do?activityId=${activity.id}",
                data: {},
                type: "post",
                dataType: "json",
                success: function (data) {
                    // data{ success:true/false, msg:xxx, data:{{}...} }
                    if (data.success) {
                        // 异步加载
                        let html = ""

                        $.each(data.data,function (i,n) {

                            // 拼接字符串标签
                            html += '<div class="remarkDiv" style="height: 60px;">'
                            html += '<img title="'+n.createBy+'" src="image/user-thumbnail.png" style="width: 30px; height:30px;">'
                            html += '<div style="position: relative; top: -40px; left: 40px;" >'
                            html += '<h5>'+n.noteContent+'</h5>'
                            html += '<font color="gray">市场活动</font> <font color="gray">-</font> <b>${activity.name}</b> <small style="color: gray;"> '+(n.editFlag == 0 ? n.createTime:n.editTime)+' 由'+(n.editFlag == 0 ? n.createBy:n.editBy)+'</small>'
                            html += '<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">'
                            // 在字符串中进行参数传递，要添加单引号的转移，保证n.id是以字符串的方式传递过去的
                            html += '<a class="myHref" onclick="updateRemark(\''+n.id+'\')" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #534646;"></span></a>'
                            html += '&nbsp;&nbsp;&nbsp;&nbsp;'
                            html += '<a class="myHref" onclick="deleteRemark(\''+n.id+'\')" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #FF0000;"></span></a>'
                            html += '</div>'
                            html += '</div>'
                            html += '</div>'

                        })

                        // 加载字符串标签
                        // 方式1，html，创建一个div标签，异步加载字符串标签
                        $("#activityRemark").html(html)
                        // 方式2，append,将字符串标签加载到line（div标签）标签的上方
                        // $("#line").append(html)
                        // 方式3，after,将字符串标签加载到activityRemarkLine（div标签）标签的下方
                        // $("#activityRemarkLine").after(html)
                    }
                }
            })
        }

        // 修改备注信息
        function updateRemark(id) {
            // alert("id="+id)
            // 发送请求，根据id获取备注信息
            $.ajax({
                url:"workbench/activity/queryRemarkById.do",
                data:{
                    "id":id
                },
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data.success){
                        // 将作用域中的数据拿出，并加载到页面
                        $("#noteContent").val(data.data.noteContent)
                        $("#remarkId").val(data.data.id)
                        // 显示模态窗口
                        $("#editRemarkModal").modal("show")

                        // 点击更新，发送请求，修改操作
                        $("#updateRemarkBtn").click(function () {
                            // 拿到修改后的数据
                            let noteContent = $("#noteContent").val()
                            let id = $("#remarkId").val()

                            $.ajax({
                                url:"workbench/activity/updateRemark.do",
                                data:{
                                    "id":id,
                                    "noteContent":noteContent
                                },
                                type:"post",
                                dataType:"json",
                                success:function(data){
                                    if(data.success){
                                        // 刷新备注信息列表
                                        getActivityRemarkList();
                                        // 关闭模态窗口
                                        $("#editRemarkModal").modal("hide")
                                    }else{
                                        alert(data.msg)
                                    }
                                }
                            })
                        })


                    }
                }
            })


        }

        // 删除备注信息
        function deleteRemark(id) {
            // alert("id="+id)
            if (confirm("您确定要删除这条备注吗?")){
                $.ajax({
                    url:"workbench/activity/deleteRemark.do",
                    data:{
                        "id":id
                    },
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        if (data.success){
                            getActivityRemarkList();
                        }else{
                            alert(data.msg)
                        }
                    }
                })
            }
        }

    </script>

</head>
<body>

<!-- 修改市场活动备注的模态窗口 -->
<div class="modal fade" id="editRemarkModal" role="dialog">
    <%-- 备注的id --%>
    <input type="hidden" id="remarkId">
    <div class="modal-dialog" role="document" style="width: 40%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">修改备注</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="noteContent" class="col-sm-2 control-label">内容</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="noteContent"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
            </div>
        </div>
    </div>
</div>


<!-- 返回按钮 -->
<div style="position: relative; top: 35px; left: 10px;">
    <a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left"
                                                                         style="font-size: 20px; color: #DDDDDD"></span></a>
</div>

<!-- 大标题 -->
<div style="position: relative; left: 40px; top: -30px;">
    <div class="page-header">
        <h3>市场活动-${activity.name} <small>${activity.startDate} ~ ${activity.endDate}</small></h3>
    </div>

</div>

<br/>
<br/>
<br/>

<!-- 详细信息 -->
<div style="position: relative; top: -70px;">
    <div style="position: relative; left: 40px; height: 30px;">
        <div style="width: 300px; color: gray;">所有者</div>
        <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.createBy}</b></div>
        <div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>
        <div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${activity.name}</b></div>
        <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
        <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
    </div>

    <div style="position: relative; left: 40px; height: 30px; top: 10px;">
        <div style="width: 300px; color: gray;">开始日期</div>
        <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.startDate}</b></div>
        <div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">结束日期</div>
        <div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${activity.endDate}</b></div>
        <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
        <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
    </div>
    <div style="position: relative; left: 40px; height: 30px; top: 20px;">
        <div style="width: 300px; color: gray;">成本</div>
        <div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.cost}</b></div>
        <div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
    </div>
    <div style="position: relative; left: 40px; height: 30px; top: 30px;">
        <div style="width: 300px; color: gray;">创建者</div>
        <div style="width: 500px;position: relative; left: 200px; top: -20px;">
            <b>${activity.createBy}&nbsp;&nbsp;</b><small
                style="font-size: 10px; color: gray;">${activity.createTime}</small></div>
        <div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
    </div>
    <div style="position: relative; left: 40px; height: 30px; top: 40px;">
        <div style="width: 300px; color: gray;">修改者</div>
        <div style="width: 500px;position: relative; left: 200px; top: -20px;">
            <b>${activity.editBy}&nbsp;&nbsp;</b><small
                style="font-size: 10px; color: gray;">${activity.editTime}</small></div>
        <div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
    </div>
    <div style="position: relative; left: 40px; height: 30px; top: 50px;">
        <div style="width: 300px; color: gray;">描述</div>
        <div style="width: 630px;position: relative; left: 200px; top: -20px;">
            <b>
                ${activity.description}
            </b>
        </div>
        <div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
    </div>
</div>

<!-- 备注 -->
<div style="position: relative; top: 30px; left: 40px;" id="remarkBody">
    <div id="activityRemarkLine" class="page-header">
        <h4>备注</h4>
    </div>

    <%-- 用于动态加载数据库中的数据 --%>
    <div id="activityRemark"></div>

    <!-- 备注1 -->
    <%--<div class="remarkDiv" style="height: 60px;">
        <img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
        <div style="position: relative; top: -40px; left: 40px;" >
            <h5>哎呦！</h5>
            <font color="gray">市场活动</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
            <div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
                <a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
            </div>
        </div>
    </div>--%>
    <div id="line" class="page-header"></div>

    <div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
        <form role="form" style="position: relative;top: 10px; left: 10px;">
            <textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2" placeholder="添加备注..."></textarea>
            <p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
                <button id="cancelBtn" type="button" class="btn btn-default">取消</button>
                <button id="saveRemarkBtn" type="button" class="btn btn-primary">保存</button>
            </p>
        </form>
    </div>
</div>
<div style="height: 200px;"></div>
</body>
</html>