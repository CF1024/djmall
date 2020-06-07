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
        <div class="layui-side layui-bg-black">
            <div class="layui-side-scroll">
                <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                    <li class="layui-nav-item"><a href="<%=request.getContextPath()%>/auth/user/toShow" target="right">用户展示</a></li>
                    <li class="layui-nav-item"><a href="<%=request.getContextPath()%>/auth/role/toShow" target="right">角色展示</a></li>
                    <li class="layui-nav-item"><a href="<%=request.getContextPath()%>/auth/resource/toShow" target="right">资源展示</a></li>
                </ul>
            </div>
        </div>
    </body>
</html>
