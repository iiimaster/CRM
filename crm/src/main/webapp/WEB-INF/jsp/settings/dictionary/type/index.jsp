<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
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
    <script>
        $(function () {
            // 1.加载数据库数据到页面
            $.ajax({
                url: "settings/dictionary/type/findAllTypeList.do",
                data: {},
                type: "post",
                dataType: "json",
                success: function (result) {
                    // 传递三个参数
                    // 1.success:成功与否 2.msg:提示信息 3.data传递的数据
                    let html = "";
                    if (result.success) {//成功加载到数据
                        $.each(result.data, function (i, n) {//i:遍历从0开始，n:单条数据
                            html += '<tr class="' + (i % 2 == 0 ? '' : 'active') + '">'
                            // html += '<tr class="active">'
                            html += '<td><input value="' + n.code + '" name="flag" type="checkbox"/></td>'
                            html += '<td >' + (i + 1) + '</td>'
                            html += '<td name="code' + (i + 1) + '">' + n.code + '</td>'
                            html += '<td>' + n.name + '</td>'
                            html += '<td>' + n.description + '</td>'
                            html += '</tr>'
                        });
                        $("#tbList").html(html);
                    } else {// 数据加载失败
                        // 将错误信息加载到隐藏域中
                        $("#tbListResult").html(result.msg)
                        // 显示隐藏域
                        $("#msgDisplay").show()
                    }

                }
            })

            // 2.全选或全不选
            $("#selectAll").click(function () {

                // 获取全选按钮的选中状态
                let ck = $('#selectAll').prop("checked")
                // alert(ck)

                // 将全选按钮的状态赋值给每个复选框
                let $flag = $("input[name=flag]")
                // alert($flag.length)
                for (let i = 0; i < $flag.length; i++) {

                    // 这种方式使用的是dom的api还是jquery的api？
                    // dom方式
                    $flag[i].checked = ck;

                    // jquery对象与dom对象之间的转换问题：
                    // jquery[i]，将jquery对象转换为dom对象.checked是dom中才有的属性
                    // $(jquery[i]),将dom对象转换为jquery对象，用jquery中的prop()方法设置属性

                    // jquery方式
                    // $($flag[i]).prop("checked",ck)

                }
            })

            // 3.反选，当所有的复选框都被选中时(给所有复选框添加点击事件)，全选框也被勾选
            // 发现直接设置点击事件会失败，因为这不是原始html页面上的代码
            // 而要添加点击事件的标签都是我们用字符串添加的，故点击事件会失效
            // $("input[name=flag]").click()

            $("#tbList").on("click", $("input[name=flag]"), function () {
                // 获取复选框为 选中状态 的 复选框数量
                let ckCount = $("input[name=flag]:checked").length
                // console.log(ckCount)

                // 获取所有复选框的数量
                let allFlag = $("input[name=flag]").length
                // alert(allFlag)

                // 根据 复选框的数量 确定 全选框的选中状态
                if (ckCount == allFlag) {
                    $('#selectAll').prop("checked", true)
                } else {
                    $('#selectAll').prop("checked", false)
                }


            })

            // 4.跳转到保存页面，save.jsp
            // 5.跳转到修改页面，需要包含所修改数据的主键，edit.jsp
            $("#typeEdit").click(function () {
                // 1.只能编辑一条数据
                // 获取选中的数据条数
                let $flag = $("input[name=flag]:checked")

                if ($flag.length === 0) {
                    // 为选中数据
                    alert("请选择要编辑的数据")
                } else if ($flag.length > 1) {
                    // 选中了多条数据
                    alert("只能选择一条数据编辑");
                } else {
                    // 只选了一条数据
                    // 获取选中数据的主键
                    let code = $flag.val()
                    // 将要编辑的数据主键传递到后台
                    window.location.href = "settings/dictionary/type/toTypeEdit.do?code=" + code
                }
            })

            // 6.删除字典类型

            // a.点击删除按钮时
            $("#delBtn").click(function () {
                // 获取 为选中状态 的复选框
                let $flag = $("input[name=flag]:checked")
                // alert($flag.length)

                if ($flag.length === 0) {
                    // 未选中数据
                    $("#delTypeMsg").html("请选择要删除的数据")
                }else{
                    // 提示信息
                    $("#delTypeMsg").html("你确定要删除选中的字典类型数据吗？")

                    // b.确认删除时的操作
                    $("#delType").click(function () {

                        // 选中状态复选框的主键值
                        let $flagVal = ""

                        // 选中数据后

                        // 将选中的复选框的主键值进行字符串的拼接
                        for (let i = 0; i < $flag.length; i++) {
                            if (i === $flag.length - 1) {
                                $flagVal += $flag.eq(i).val();
                            } else {
                                $flagVal += $flag.eq(i).val() + "-"
                            }
                        }
                        // console.log($flagVal)

                        $.ajax({
                            url: "settings/dictionary/type/delType.do",
                            data: {
                                "delTypeIds": $flagVal
                            },
                            type: "post",
                            dataType: "json",
                            success: function (data) {

                                if (data.success) {
                                    // console.log(data)
                                    window.location.href = "settings/dictionary/type/toTypeIndex.do"
                                }else{
                                    $("#tbListResult").html(data.msg)
                                    $("#msgDisplay").show()
                                }

                            }
                        })
                    })
                }
            })




        })
    </script>
</head>
<body>

<div>
    <div style="position: relative; left: 30px; top: -10px;width: 1369px;">
        <div class="page-header">
            <h3>字典类型列表</h3>
        </div>
    </div>
</div>
<div class="btn-toolbar" role="toolbar"
     style="background-color: #F7F7F7;width: 1369px; height: 50px; position: relative;left: 30px;">
    <div class="btn-group" style="position: relative; top: 18%;">
        <button type="button" class="btn btn-primary"
                onclick="window.location.href='settings/dictionary/type/toTypeSave.do'"><span
                class="glyphicon glyphicon-plus"></span> 创建
        </button>
        <button type="button" class="btn btn-default" id="typeEdit"><span
                class="glyphicon glyphicon-edit"></span> 编辑
        </button>
        <button id="delBtn" type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal"><span
                class="glyphicon glyphicon-minus"></span> 删除
        </button>

        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">删除字典类型</h4>
                    </div>
                    <div class="modal-body" id="delTypeMsg">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" id="delType">确认删除</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--隐藏域--%>
<div id="msgDisplay" class="alert alert-danger alert-dismissible" role="alert" align="center" style="display: none">
    <strong>Danger!</strong>&nbsp;&nbsp;&nbsp;<span id="tbListResult"></span>
</div>

<div style="position: relative; left: 30px; top: 20px; width: 1369px;">
    <table class="table table-hover">
        <thead>
        <tr style="color: #B3B3B3;">
            <td><input type="checkbox" id="selectAll"/></td>
            <td>序号</td>
            <td>编码</td>
            <td>名称</td>
            <td>描述</td>
        </tr>
        </thead>
        <tbody id="tbList">
        <%--        <tr class="active">--%>
        <%--            <td><input type="checkbox"/></td>--%>
        <%--            <td>1</td>--%>
        <%--            <td>sex</td>--%>
        <%--            <td>性别</td>--%>
        <%--            <td>性别包括男和女</td>--%>
        <%--        </tr>--%>
        </tbody>
    </table>
</div>


</body>
</html>