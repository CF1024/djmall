<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：force_update_pwd.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

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
        <title>用户登录</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/verify.css" media="all">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/md5-min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/verify.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style type="text/css">
        #login{
            position:absolute;
            left: 55%;
            top: 65%;
            margin-left:-200px;
            margin-top:-140px;
            border:1px;
            background-color:transparent;
            align:center;
        }
        #form{
            position:relative;
            left:70%;
            top:70%;
            margin-left:-150px;
            margin-top:-80px;
        }
        #phoneLogin{
            position:absolute;
            left: 50%;
            top: 60%;
            margin-left:-200px;
            margin-top:-140px;
            border:1px;
            background-color:transparent;
            align:center;
            width: 300px;
        }
        #phoneForm{
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
        $.validator.setDefaults({
            submitHandler: $(function () {
                $("#loginBtn").click(function () {
                    var userPwd = $("#userPwd");
                    userPwd.val(md5(userPwd.val()));
                    var index = layer.load(0, {shade:0.5});
                    $.get(
                        "<%=request.getContextPath()%>/user/login",
                        $("#fm").serialize(),
                        function (data) {
                            layer.close(index);
                            if(data.code !== 200){
                                layer.msg(data.msg, {icon:5,time:2000});
                                return;
                            }
                            layer.msg(data.msg, {icon: 6, time: 2000},
                                function(){
                                    // 存cookie
                                    cookie.set("TOKEN", data.data.token, 22);
                                    cookie.set("NICK_NAME", data.data.nickName, 22);
                                    // 刷新父页面
                                    window.location.href = "<%=request.getContextPath()%>/product/toShow";
                                });
                        });
                })
            })
        })

        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use(['form', 'element'], function () {
            var form = layui.form;
            var element = layui.element;
            element.on('tab(filter)', function (data) {
                console.log(this); //当前Tab标题所在的原始DOM元素
                console.log(data.index); //得到当前Tab的所在下标
                console.log(data.elem); //得到当前的Tab大容器
            });
            form.render();
        });

        var show_num = [];

        //表单验证
        jQuery.validator.addMethod("isPhone", function(value, element) {
            var length = value.length;
            var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请填写正确的手机号码");//可以自定义默认提示信息
        $(function() {
            $("#phoneFm").validate({
                rules: {
                    userPhone: {
                        required: true,
                        isPhone: true
                    },
                    verifyCode: {
                        required: true
                    }
                },
                messages: {
                    userPhone: {
                        required: "请输入手机号码",
                        isPhone: "请输入正确格式的手机号码~"
                    },
                    verifyCode: {
                        required: "验证码不能为空！"
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
            var val = $("#graPhCode").val().toLowerCase(); //toLowerCase 把字符串转换为小写
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

        //手机号登录
        $.validator.setDefaults({
            submitHandler: $(function () {
                $("#phoneLoginPBtn").click(function () {
                    var index = layer.load(0, {shade:0.5});
                    $.get(
                        "<%=request.getContextPath()%>/auth/user/phoneLogin",
                        $("#fm").serialize(),
                        function (data) {
                            layer.close(index);
                            if(data.code != 200){
                                layer.msg(data.msg, {icon:5,time:2000});
                                return;
                            }
                            layer.msg(data.msg, {icon: 6, time: 2000},
                                function(){
                                    window.location.href = "<%=request.getContextPath()%>/index/toIndex";
                                });
                        });
                })
            })
        })
    </script>
    <body>
        <div class="layui-layout layui-layout-admin">
            <div class="layui-header">
                <div class="layui-logo" style="color: coral">点金商城</div>
                <ul class="layui-nav layui-layout-right">
                    <li class="layui-nav-item">
                        <a href="<%=request.getContextPath()%>/product/toShow"  style="color: aqua"><i class="layui-icon layui-icon-home" style="font-size: 30px"></i>首页</a>
                    </li>
                    <li class="layui-nav-item" id="hiddenRegister">
                        <a href="<%=request.getContextPath()%>/user/toRegister" style="color: tomato"><i class="layui-icon layui-icon-user" style="font-size: 30px"></i>注册</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="layui-tab">
            <ul class="layui-tab-title" lay-filter="login">
                <li class="layui-this">账号登录</li>
                <li>手机登录</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <div id="login">
                        <div id="form">
                            <form class="layui-form" id="fm">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">账号</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="userName" id="userName" placeholder="请输入用户名" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">密码</label>
                                    <div class="layui-input-inline">
                                        <input type="password" name="userPwd" id="userPwd" required placeholder="请输入密码" class="layui-input">
                                    </div>
                                </div>
                                <label class="layui-form-label"></label>
                                <input id="loginBtn" type="button" value="登录" class="layui-btn" />
                                <div class="layui-form-item">
                                    <label class="layui-form-label"></label>
                                    <div class="layui-form-mid layui-word-aux">
                                        <a href="<%=request.getContextPath()%>/user/toForgetPwd" class="layui-blue">忘记密码</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    </div>
                                    <div class="layui-form-mid layui-word-aux">
                                        <a href="<%=request.getContextPath()%>/user/toRegister" target="_blank" class="layui-blue">点我注册</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="layui-tab-item">
                    <div id="phoneLogin">
                        <div id="phoneForm">
                            <form class="layui-form" id="phoneFm">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">手机号</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="userPhone" id="userPhone" placeholder="请输入手机号" class="layui-input">
                                    </div>
                                    <div class="layui-form-mid layui-word-aux">
                                        <a href="<%=request.getContextPath()%>/user/toRegister" target="_blank" class="layui-blue">点我注册</a>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">图形码</label>
                                    <div class="layui-input-inline">
                                        <input type="text" value="" placeholder="请输入验证码(不区分大小写)" class="layui-input" id="graPhCode"/>
                                    </div>
                                    <div class="layui-word-aux">
                                        <canvas id="canvas" style="width: 100px; height: 35px;" class="layui-blue"></canvas>
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
                                <label class="layui-form-label"></label>
                                <input type="submit" id="phoneLoginPBtn" value="登录" class="layui-btn">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>