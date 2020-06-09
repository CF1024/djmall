<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/3
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <script>
        //JavaScript代码区域
        layui.use('element', function(){
            var element = layui.element;

        });
    </script>
    <body class="layui-layout-body" style="overflow: hidden">
        <div class="layui-layout layui-layout-admin">
            <div class="layui-header">
                <div class="layui-logo" style="color: coral">点金商城</div>
                <ul class="layui-nav layui-layout-left">
                    <li class="layui-nav-item"><a href="<%=request.getContextPath()%>/auth/user/logout" style="color: aqua">退出登录</a></li>
                </ul>
                <ul class="layui-nav layui-layout-right">
                    <li class="layui-nav-item">欢迎<span style="color: hotpink">${USER.nickName}</span>登录</li>
                </ul>
            </div>
        </div>
    </body>
</html>
