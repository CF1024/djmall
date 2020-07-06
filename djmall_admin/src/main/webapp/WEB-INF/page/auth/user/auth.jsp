<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：auth.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/8
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>授权</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <script type="text/javascript">
        function auth() {
            var index = layer.load(0, {shade:0.5});
            $.post(
                "<%=request.getContextPath()%>/auth/user/auth",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if(data.code != 200){
                        layer.msg(data.msg, {icon:5,time:2000});
                        return;
                    }
                    layer.msg(data.msg, {icon: 6, time: 2000},
                        function(){
                            parent.window.location.href = "<%=request.getContextPath()%>/auth/user/toShow";
                    });
                }
            );
        }
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();//重点在这里
        });
    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" name="_method" value="PUT"/>
            <input type="hidden" value="${userRole.userId}" name="userId">
            <input type="button" value="确认" onclick="auth()" class="layui-btn layui-btn-normal">
            <table border="0px" class="layui-table">
                <colgroup>
                    <col width="100">
                    <col width="100">
                    <col width="500">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th></th>
                    <th>编号</th>
                    <th>角色名</th>
                </tr>
                </thead>
                <c:forEach items="${roleList}" var="role">
                    <tr>
                        <td><input type="radio" name="roleId" value="${role.roleId}" <c:if test="${userRole.roleId == role.roleId}">checked</c:if>></td>
                        <td>${role.roleId}</td>
                        <td>${role.roleName}</td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>
