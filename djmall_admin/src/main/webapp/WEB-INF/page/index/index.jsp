<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：index.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/3
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<frameset rows="60px, *">
    <frame src="<%=request.getContextPath()%>/index/toTop" name="top">
    <frameset cols="200px, *">
        <frame src="<%=request.getContextPath()%>/index/toLeft" name="left">
        <frame src="<%=request.getContextPath()%>/index/toRight" name="right">
    </frameset>
</frameset>
</html>
