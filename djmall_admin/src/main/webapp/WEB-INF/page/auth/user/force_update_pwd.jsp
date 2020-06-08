<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/8
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>强制修改密码</title>
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
            left: 75%;
            top: 85%;
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
                var salt = md5(new Date().valueOf() + Math.floor(Math.random()*100000));
                $("#userPwd").val(md5(md5($("#userPwd").val()) + salt));
                $("#salt").val(salt);
                var index = layer.load(0,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/auth/user/forceUpdatePwd",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200){
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function(){
                                parent.window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
                            });
                    });
            }
        });

        /**
         * 表单验证
         */
        $(function () {
            $("#fm").validate({
                rules:{
                    userPwd:{
                        required:true,
                        rangelength:[3,6],
                        digits:true
                    },
                    userPwd1:{
                        required:true,
                        rangelength:[3,6],
                        digits:true,
                        equalTo:"#userPwd"
                    }
                },
                messages:{
                    userPwd:{
                        required:"密码不能为空",
                        rangelength:"密码必须在{0}~{1}位数之间",
                        digits:"密码必须是整数"
                    },
                    userPwd1:{
                        required:"请输入确认密码",
                        rangelength:"密码必须在{0}~{1}位数之间",
                        digits:"密码必须是整数",
                        equalTo:"确认密码与密码不一致"
                    }
                }
            })
        });

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
                    <input type="hidden" name="salt" id="salt" />
                    <input type="hidden" name="_method" value="PUT"/>
                    <input type="hidden" name="userId" id="userId" value="${user.userId}"/>

                    <div class="layui-form-item">
                        <label class="layui-form-label">密码</label>
                        <div class="layui-input-inline">
                            <input type="password" name="userPwd" id="userPwd" placeholder="请输入密码" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">确认密码</label>
                        <div class="layui-input-inline">
                            <input type="password" name="userPwd1" id="userPwd1" placeholder="请输入确认密码" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label"></label>
                        <div class="layui-input-inline">
                            <input type="submit" value="修改" class="layui-btn layui-btn-normal">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>