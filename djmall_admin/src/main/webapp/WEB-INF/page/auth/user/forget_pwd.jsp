<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：forget_pwd.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/9
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/verify.css" media="all">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/md5-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/verify.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
</head>
<style type="text/css">
    #login{
        position:absolute;
        left: 50%;
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
    .layui-form-label{
        white-space:nowrap!important;
    }
    .error{
        color: red;
    }
</style>
<script type="text/javascript">
    var show_num = [];

    //表单验证
    jQuery.validator.addMethod("isPhone", function(value, element) {
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请填写正确的手机号码");//可以自定义默认提示信息
    $(function() {
        $("#fm").validate({
            rules: {
                userPhone: {
                    required: true,
                    isPhone: true
                },
                verifyCode: {
                    required: true
                },
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
            messages: {
                userPhone: {
                    required: "请输入手机号码",
                    isPhone: "请输入正确格式的手机号码~"
                },
                verifyCode: {
                    required: "验证码不能为空！"
                },
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
        });

        //图片验证码
        draw(show_num);
        $("#canvas").on('click',function(){
            draw(show_num);
        });
    });

    //发送短信验证码
    var countdown=60;
    function sendCode(){
        var val = $("#graPhCode").val().toLowerCase();
        var num = show_num.join("");
        if(val == ''){
            layer.msg('请输入图片验证码！', {icon:4, time:2000});
            return;
        }else if(val != num) {
            layer.msg('图片验证码错误！请重新输入！', {icon:5, time:2000});
            $("#graPhCode").val('');
            draw(show_num);
            return;
        }
        if ($("#userPhone").val() == null || $("#userPhone").val() == "") {
            layer.msg("请输入手机号再获取验证码", {icon: 5, time: 2000});
            return;
        }
        $.post(
            "<%=request.getContextPath()%>/auth/user/sendCode",
            $("#fm").serialize(),
            function(data){
                if (data.code != "200") {
                    layer.msg(data.msg, {icon: 5, time: 1000});
                    return;
                }
                layer.msg(data.msg +  ",验证码有效期为五分钟,请在规定时限内进行处理!", {icon: 6, time: 2000})
            });
        var obj = $("#btn");
        setTime(obj);
    }

    function setTime(obj) {
        if (countdown == 0) {
            obj.attr('disabled',false);
            obj.val("免费获取验证码");
            countdown = 60;
            return;
        } else {
            obj.attr('disabled',true);
            obj.val("重新发送(" + countdown + ")");
            countdown--;
        }
        setTimeout(
            function(){setTime(obj);}, 1000);
    }

    //忘记密码
    $.validator.setDefaults({
        submitHandler : function () {
            var salt = md5(new Date().valueOf() + Math.floor(Math.random()*100000));
            $("#userPwd").val(md5(md5($("#userPwd").val()) + salt));
            $("#salt").val(salt);
            var index = layer.load(0, {shade:0.5});
            $.post(
                "<%=request.getContextPath()%>/auth/user/forgetPwd",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if(data.code != 200){
                        layer.msg(data.msg, {icon:5,time:2000});
                        return;
                    }
                    layer.msg(data.msg, {icon: 6, time: 2000},
                        function(){
                            window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
                        });
                });
        }
    });

    //取消
    function toLogin() {
        window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
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
            <input type="hidden" name="salt" id="salt" />
            <div class="layui-form-item">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input type="text" name="userPhone" id="userPhone" placeholder="请输入手机号" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <a href="<%=request.getContextPath()%>/auth/user/toAddUser" class="layui-blue">还没有账号？点此去注册</a>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">图形码</label>
                <div class="layui-input-inline">
                    <input type="text" value="" placeholder="请输入验证码(不区分大小写)" class="layui-input" id="graPhCode"/>
                </div>
                <div class="layui-word-aux">
                    <canvas id="canvas" width="100" height="35" class="layui-blue"></canvas>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">验证码</label>
                <div class="layui-input-inline">
                    <input type="text" name="verifyCode" id="verifyCode" required placeholder="请输入验证码" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <input type="button" value="发送验证码" onclick="sendCode()" id="btn" class="layui-btn layui-btn-warm">
                </div>
            </div>
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
                <input type="submit" value="修改" class="layui-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="取消" onclick="toLogin()" class="layui-btn layui-btn-primary">
            </div>
        </form>
    </div>
</div>
</body>
</html>

