<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/5
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Title</title>
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
            left: 52%;
            top: 48%;
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
                    "<%=request.getContextPath()%>/auth/user/addUser",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200){
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 5000},
                            function(){
                                window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
                            });
                    });
            }
        });

        /**
         * 表单验证
         */
        jQuery.validator.addMethod("isPhone", function(value, element) {
            var length = value.length;
            var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请填写正确的手机号码");//可以自定义默认提示信息

        jQuery.validator.addMethod("notEqualTo", function(value, element) {
            return value != $("#userName").val();
        }, $.validator.format("昵称不能和用户名相同哟"));//可以自定义默认提示信息
        $(function () {
            $("#fm").validate({
                rules:{
                    userName:{
                        required:true,//必输字段
                        rangelength:[5,10],// 输入长度必须介于 5 和 10 之间的字符串")(汉字算一个字符)
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/user/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
                    },
                    nickName:{
                        required: true,
                        rangelength:[1,10],// 输入长度必须介于 1 和 10 之间的字符串")(汉字算一个字符)
                        notEqualTo:true,
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/user/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
                    },
                    userPhone:{
                        required:true,
                        isPhone:true,
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/user/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
                    },
                    userEmail:{
                        required:true,
                        email:true,
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/user/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
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

                messages:{
                    userName:{
                        required:"账号不能为空",//必输字段
                        rangelength:"账号必须在{0}~{1}个字符串之间",// 输入长度必须介于 5 和 10 之间的字符串")(汉字算一个字符)
                        remote: "账号已存在"
                    },
                    nickName:{
                        required: "昵称不能为空",
                        rangelength:"昵称必须在{0}~{1}个字符串之间",// 输入长度必须介于 1 和 10 之间的字符串")(汉字算一个字符)
                        notEqualTo:"昵称不能和用户名相同哟！",
                        remote: "你的小伙伴已经使用了该昵称"
                    },
                    userPhone:{
                        required:"手机号不能为空",
                        isPhone:"请填写正确的手机号码",
                        remote: "手机号已存在"
                    },
                    userEmail:{
                        required:"邮箱不能为空",
                        email:"邮箱格式不正确",
                        remote: "邮箱已存在"
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
                    <div class="layui-form-item">
                        <label class="layui-form-label">账号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="userName" id="userName" placeholder="请输入账号" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">昵称</label>
                        <div class="layui-input-inline">
                            <input type="text" name="nickName" id="nickName" placeholder="请输入昵称" class="layui-input">
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
                        <label class="layui-form-label">手机号</label>
                        <div class="layui-input-inline">
                            <input type="text" name="userPhone" id="userPhone" placeholder="请输入手机号" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">邮箱</label>
                        <div class="layui-input-inline">
                            <input type="text" name="userEmail" id="userEmail" placeholder="请输入邮箱" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">角色</label>
                        <div class="layui-input-block">
                            <c:forEach items="${roleList}" var="role">
                                <input type="radio" name="userRole" value="${role.roleId}" title="${role.roleName}" <c:if test="${role.roleId == 3}">checked</c:if>>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">性别</label>
                        <div class="layui-input-block">
                            <input type="radio" name="userSex"  value="SECRECY" title="保密" checked>
                            <input type="radio" name="userSex"  value="MAN" title="男">
                            <input type="radio" name="userSex" value="WOMAN" title="女">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <div class="layui-input-inline">
                            <input type="submit" value="注册" class="layui-btn layui-btn-normal">
                        </div>
                        <div class="layui-form-mid layui-word-aux">
                            <a href="<%=request.getContextPath()%>/auth/user/toLogin" class="layui-blue">已有账号，点此去登录</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
