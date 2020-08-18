<%--
  ~ 作者：CF
  ~ 日期：2020-07-19 18:49
  ~ 项目：djmall
  ~ 模块：djmall_platform
  ~ 类名：show.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/19
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style type="text/css">
        .error{color: red;}
        .divBtn{position:relative;display:inline-block;height:38px;line-height:38px;padding:0 60px;background-color:#1E9FFF;color:#fff;white-space:nowrap;text-align:center;font-size:14px;border:none;border-radius:100px;cursor:pointer}
        .divBtn input{position:absolute;font-size:10px;height: 40px;right:2px;top:0;opacity:0;}
        .imgStyle{width:190px;height:190px;margin-right:10px;border-radius:50%}
    </style>
    <script type="text/javascript">
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use(['form', 'table', 'upload'], function () {
            var form = layui.form;
            var upload = layui.upload;
            form.render();//重点在这里
            //预览图片
            $(function(){
                $(".divImg").hide();
            });
            //选完文件后不自动上传
            upload.render({
                elem: '#file',
                auto: false,
                accept: 'images', //只允许上传图片
                acceptMime: 'image/gif,image/jpeg,image/jpg,image/png,image/svg', //只筛选图片
                choose: function(obj){
                    //将每次选择的文件追加到文件队列
                    var files = obj.pushFile();
                    //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                    obj.preview(function(index, file, result){
                        console.log(index); //得到文件索引
                        console.log(file); //得到文件对象
                        console.log(result); //得到文件base64编码，比如图片
                        $('#imgShow').attr('src', result); //图片链接（base64）
                        if(obj != null){
                            $(".divImg").show();
                        }else{
                            $(".divImg").hide();
                        }
                        $(".divProductImg").hide();
                    });
                },
            });
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
                    nickName:{
                        required: true,
                        rangelength:[1,10],// 输入长度必须介于 1 和 10 之间的字符串")(汉字算一个字符)
                        notEqualTo:true,
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/user/",
                            data:{
                                userId:function() {
                                    return $("#userId").val();
                                },
                                dataType:"json"
                            }
                        }
                    },
                    userPhone:{
                        required:true,
                        isPhone:true,
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/user/",
                            data:{
                                userId:function() {
                                    return $("#userId").val();
                                },
                                dataType:"json"
                            }
                        }
                    },
                    userEmail:{
                        required:true,
                        email:true,
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/user/",
                            data:{
                                userId:function() {
                                    return $("#userId").val();
                                },
                                dataType:"json"
                            }
                        }
                    }
                },
                messages:{
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
                    }
                }
            })
        });


        //修改商品
        $.validator.setDefaults({
            submitHandler: function () {
                var formData = new FormData($("#fm")[0]);
                var index = layer.load(0, {shade:0.5});
                //七牛雲上传图片只能ajax提交
                $.ajax({
                    url:'<%= request.getContextPath() %>/user/updateGeneralUser',
                    dataType:'json',
                    type:'post',
                    data: formData,
                    processData : false, // 使数据不做处理
                    contentType : false, // 不要设置Content-Type请求头信息
                    beforeSend: function (request) {
                        request.setRequestHeader("TOKEN", cookie.get("TOKEN"));
                    },
                    success: function (data) {
                        layer.close(index);
                        if(data.code !== 200){
                            layer.msg(data.msg, {offset: '230px', icon:5, time:5000});
                            return;
                        }
                        layer.msg(data.msg, {offset: '230px', icon: 6, time: 2000},
                            function(){
                                // 存cookie
                                cookie.set("TOKEN", data.data.token, 22);
                                cookie.set("NICK_NAME", data.data.nickName, 22);
                                window.location.reload();
                               /* window.location.href = "<%=request.getContextPath()%>/user/toShowUserDetails?TOKEN="+getToken();*/
                            });
                    }
                });
            }
        });

    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="token" value="${TOKEN}">
            <input type="hidden" name="userId" id="userId" value="${user.userId}"/>
            <input type="hidden" name="userName" id="userName" value="${user.userName}"/>
            <%--图片修改--%>
            <label class="layui-form-label">头像</label>
            <div class="layui-upload">
                <div class="layui-upload-list">
                    <div class="divProductImg">
                        <img src="http://qeu5389un.bkt.clouddn.com/${user.userImg}" class="imgStyle">
                        <input type="hidden" name="removeImg" value="${user.userImg}">
                    </div>
                </div>
                <div class="layui-upload-list">
                    <div class="divImg">
                        <img src="" id="imgShow" class="imgStyle">
                    </div>
                </div>
                <label class="layui-form-label"></label>
                <div class="divBtn">
                    <i class="layui-icon">&#xe67c;</i>上传头像
                    <input id="file" type="file" name="file"/>
                </div><span id="fileError"></span>
            </div><br/>

            <div class="layui-form-item">
                <label class="layui-form-label">昵称</label>
                <div class="layui-input-inline">
                    <input type="text" name="nickName" id="nickName" value="${user.nickName}" placeholder="请输入昵称" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input type="text" name="userPhone" id="userPhone" value="${user.userPhone}" placeholder="请输入手机号" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">邮箱</label>
                <div class="layui-input-inline">
                    <input type="text" name="userEmail" id="userEmail" value="${user.userEmail}" placeholder="请输入邮箱" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">性别</label>
                <div class="layui-input-block">
                    <jsp:useBean id="sexList" scope="request" type="java.util.List"/>
                    <c:forEach items="${sexList}" var="sex">
                        <input type="radio" name="userSex" value="${sex.baseCode}" title="${sex.baseName}"<c:if test="${sex.baseCode == user.userSex}">checked</c:if>>
                    </c:forEach>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <label class="layui-form-label"></label>
                    <input type="submit" value="修改" class="layui-btn layui-btn-normal">
                </div>
            </div>
        </form>
    </body>
</html>
