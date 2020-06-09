<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/3
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>用户登录</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/md5-min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style type="text/css">
        #login{
            position:absolute;
            left: 45%;
            top: 60%;
            margin-left:-200px;
            margin-top:-140px;
            border:1px;
            background-color:transparent;
            align:center;
        }
        #form{
            position:relative;
            left:50%;
            top:50%;
            margin-left:-150px;
            margin-top:-80px;
        }
        .error{
            color: red;
        }
    </style>
    <script type="text/javascript">
        $.validator.setDefaults({
            submitHandler : function () {
                $("#userPwd").val(md5($("#userPwd").val()));
                var index = layer.load(0, {shade:0.5});
                $.get(
                    "<%=request.getContextPath()%>/auth/user/login",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if (data.code == -100) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            layer.open({
                                type: 2,
                                title: '修改密码',
                                shadeClose: true,
                                maxmin: true, //开启最大化最小化按钮
                                shade: 0.8,
                                area: ['400px', '50%'],
                                content: '<%=request.getContextPath()%>/auth/user/toForceUpdatePwd?userName='+$("#userName").val()
                            });
                            return;
                        }
                        if(data.code != 200){
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function(){
                                window.location.href = "<%=request.getContextPath()%>/index/toIndex";
                            });
                    });
            }
        });

        //表单验证
        $(function () {
            if ('${msg}' != '') {
                layer.msg('${msg}', {icon: 6, time: 4000});
            }
            $("#fm").validate({
                rules:{
                    userName:{
                        required: true
                    },
                    userPwd:{
                        required: true
                    }
                },
                messages:{
                    userName: {
                        required:"请输入账号"
                    },
                    userPwd: {
                        required:"请输入密码"
                    }
                }
            })
        });
        //退出登录
        if (window.top.document.URL != document.URL) {
            window.top.location = document.URL;
        }
        //手机号登录
        function toPhoneLogin() {
            window.location.href = "<%=request.getContextPath()%>/auth/user/toPhoneLogin";
        }

        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });

    </script>
    <body>
        <div id="login">
            <div id="form">
                <form class="layui-form" id="fm">
                    <div class="layui-form-item">
                        <label class="layui-form-label">账号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="userName" id="userName" placeholder="请输入用户名" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">
                            <a href="<%=request.getContextPath()%>/auth/user/toAddUser" class="layui-blue">还没有账号？点此去注册</a>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">密码</label>
                        <div class="layui-input-inline">
                            <input type="password" name="userPwd" id="userPwd" required placeholder="请输入密码" class="layui-input">
                        </div>
                        <div class="layui-form-mid layui-word-aux">
                            <a href="<%=request.getContextPath()%>/auth/user/toForgetPwd" class="layui-blue">忘记密码</a>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <input type="submit" value="登录" class="layui-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" value="手机登录" onclick="toPhoneLogin()" class="layui-btn layui-btn-primary">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
