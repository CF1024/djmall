<%--
  ~ 作者：CF
  ~ 日期：2020-08-01 14:18
  ~ 项目：djmall
  ~ 模块：djmall_platform
  ~ 类名：order_detail.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/8/1
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>订单详情</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <body>
        订单编号:${order.orderNo}<br /><br />
        收货信息:${order.receiverName}-${phoneNumber}<br /><br />
        收货地址:${order.receiverProvince}-${order.receiverCity}-${order.receiverCounty}-${order.receiverDetail}<br /><br />
        商品信息：<br />
        <table class="layui-table">
            <tr>
                <th>编号</th>
                <th>商品信息</th>
                <th>数量</th>
                <th>付款金额</th>
                <th>折扣</th>
            </tr>
            <c:forEach items="${order.detailList}" var="detail" varStatus="i">
                <tr>
                    <td>${i.index+1}</td>
                    <td>${detail.productName}:${detail.skuInfo}</td>
                    <td>${detail.buyCount}</td>
                    <td>${detail.payMoney}元</td>
                    <td>
                        <c:if test="${detail.skuRate == '100'}">无折扣</c:if>
                        <c:if test="${detail.skuRate != '100'}">${detail.skuRate}%</c:if>
                    </td>
                </tr>
            </c:forEach>
        </table><br>
        下单时间:${order.createTime}<br /><br />
        支付方式:${order.payTypeShow}<br /><br />
        支付时间:<c:if test="${order.payTime != null}">${order.payTime}</c:if>
                <c:if test="${order.payTime == null}">未支付</c:if><br /><br />
        商品总金额:${order.totalMoney}元<br /><br />
        运费：<c:if test="${order.totalFreight == '0.00'}">包邮</c:if>
        <c:if test="${order.totalFreight != '0.00'}">${order.totalFreight}元</c:if><br /><br />
        实付金额:${order.totalPayMoney}元<br /><br />
    </body>
</html>
