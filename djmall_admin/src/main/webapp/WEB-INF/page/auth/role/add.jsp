<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：add.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/4
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style type="text/css">
        .error{
            color: red;
        }
    </style>
    <script type="text/javascript">

        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/auth/role/add",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                parent.window.location.href = "<%=request.getContextPath()%>/auth/role/toShow";
                        });
                    });
            }
        });

        $(function(){
            $("#fm").validate({
                rules:{
                    roleName:{
                        required:true,
                        rangelength:[0,5],
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/role/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
                    }
                },
                messages:{
                    roleName:{
                        required:"请输入角色名",
                        rangelength:"长度限制在{0}到{1}位数之间",
                        remote:"角色名已存在~"
                    }
                }
            })
        });
    </script>
    <body>
        <form id="fm" class="layui-form" id="fm">
            <input type="hidden" name="_method" value="POST"/>
            <div class="layui-form-item">
                <label class="layui-form-label">角色名</label>
                <div class="layui-input-inline">
                    <input type="text" name="roleName" id="roleName" placeholder="请输入角色名" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <input type="submit" value="新增角色" class="layui-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </div>
        </form>
    </body>
</html>
