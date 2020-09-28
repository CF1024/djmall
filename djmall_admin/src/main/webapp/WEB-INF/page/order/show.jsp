<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：show.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/7
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <style type="text/css">
        a{
            color: #0000FF;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            show();
        })

        function show() {
            var index = layer.load(0, {shade:0.5});
            $.get(
                "<%=request.getContextPath()%>/order/show",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var order = data.data.list[i];
                        html += "<tr>";
                        html += "<td>" + order.orderNo +"</td>";
                        html += "<td>" + order.productNameShow +"</td>";
                        <c:if test="${USER.userRole == 1}">
                        html += "<td>" + order.businessNickNameShow + "</td>";
                        </c:if>
                        html += "<td>" + order.totalBuyCount +"</td>";
                        html += "<td>" + order.totalPayMoney +"元</td>";
                        html += "<td>" + order.payTypeShow +"</td>";
                        html += "<td>";
                        html += order.receiverName+"-"+order.receiverPhone+"-"+order.receiverProvince+order.receiverCity+order.receiverCounty+order.receiverDetail;
                        html += "</td>";
                        html += "<td>" + order.nickNameShow +"</td>";
                        html += "<td>" + order.userPhoneShow +"</td>";
                        html += "<td>" + order.createTime +"</td>";
                        html += order.payTime == null?"<td>暂无</td>":"<td>" + order.payTime +"</td>";
                        if (${USER.userRole == 2}) {
                            if (order.remindStatus == 1) {
                                html += "<td>买家提醒赶紧发货了，";
                                html += "<a href='javascript:remind(\""+order.orderNo+"\")'>我知道了</a>";
                                html += "</td>";
                            } else {
                                html += "<td>无消息处理</td>";
                            }
                        }
                        html += "<td>" + order.orderStatusShow;
                        if (${USER.userRole == 2} && order.orderStatus == 'PAID') {
                            html += "，<a href = 'javascript:toDeliver(\""+order.orderNo+"\")'>去发货</a>"
                        }
                        html += "</td>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                    var pageHtml = "";
                    var pageNo = parseInt($("#pageNo").val());
                    pageHtml += "<input type='button' value='上一页' onclick='page("+ data.data.pages + "," + (pageNo - 1)+")' class='layui-btn layui-btn-normal'/>";
                    pageHtml += "<input type='button' value='下一页' onclick='page("+ data.data.pages + "," + (pageNo + 1)+")' class='layui-btn layui-btn-normal'/>";
                    $("#pageInfo").html(pageHtml);
                }
            );
        }

        //分页
        function page(pages, pageNo) {
            if (pageNo < 1) {
                layer.msg("已是第一页");
                return;
            }
            if (pageNo > pages) {
                layer.msg("已是最后一页");
                return;
            }
            $("#pageNo").val(pageNo);
            show();
        }
        //去发货
        function toDeliver(orderNo) {
            var index = layer.load(0, {shade:0.5});
            $.post(
                "<%=request.getContextPath()%>/order/updateStatus",
                {"_method":"PUT","orderStatus":"SHIPPED","orderNo":orderNo},
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    window.location.href = "<%=request.getContextPath()%>/order/toShow";
                }
            );
        }

        //我知道了
        function remind(orderNo) {
            var index = layer.load(0, {shade:0.5});
            $.post(
                "<%=request.getContextPath()%>/order/updateRemind",
                {"orderNo":orderNo, "remindStatus":"2","_method":"PUT"},
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    window.location.reload();
                }
            );
        }
        //导出订单
        function exportOrder() {
            window.location.href = "<%=request.getContextPath()%>/order/exportOrder";
        }
    </script>
    <body>
    <form id="fm">
        <input type="hidden" value="1" name="pageNo" id="pageNo">
    </form>
        <shiro:hasPermission name="EXPORT_ORDER_BTN">
            <input type="button" value="导出订单" onclick="exportOrder()" class="layui-btn layui-btn-radius layui-btn-primary">
        </shiro:hasPermission>
        <table border="0px" class="layui-table">
            <colgroup>
                <col width="100">
                <col width="100">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>订单号</th>
                <th>商品名称</th>
                <c:if test="${USER.userRole == 1}">
                    <th>商家</th>
                </c:if>
                <th>购买数量</th>
                <th>付款金额（包含邮费）</th>
                <th>支付方式</th>
                <th>收货人信息</th>
                <th>下单人</th>
                <th>下单人电话</th>
                <th>下单时间</th>
                <th>付款时间</th>
                <c:if test="${USER.userRole == 2}">
                    <th>买家消息</th>
                </c:if>
                <th>订单状态</th>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
        <div id="pageInfo"></div>
    </body>
</html>
