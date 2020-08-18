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
<html>
    <head>
        <title>订单展示</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
    </head>
    <style type="text/css">
        a{color: #0000FF;}
    </style>
    <script type="text/javascript">
        //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
        layui.use('element', function(){
            var element = layui.element;
            element.on('tab(filter)', function(data){
                /*console.log(this); //当前Tab标题所在的原始DOM元素
                console.log(data.index); //得到当前Tab的所在下标
                console.log(data.elem); //得到当前的Tab大容器*/
                $("#pageNo").val(1);
                $("#tbd0").empty();
                $("#tbd1").empty();
                $("#tbd2").empty();
                $("#tbd3").empty();
                if (data.index == 0) {
                    showOrder();
                } else if (data.index == 1) {
                    getOrderInfo(1, "TO_BE_DELIVERED");
                } else if (data.index == 2) {
                    getOrderInfo(2, "COMPLETED");
                } else if (data.index == 3) {
                    getOrderInfo(3, "CANCELLED");
                }
            });
        });

        $(function () {
            showOrder();
        })

        function showOrder() {
            var index = layer.load(0, {offset: '300px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/order/showOrder",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    if (data.data.list.length <= 0) {
                        $("#span0").html("暂无订单，快去逛逛吧");
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var order = data.data.list[i];
                        html += "<tr>";
                        html += "<td><a href='<%=request.getContextPath()%>/order/toOrderDetails?TOKEN="+getToken()+"&orderNo="+order.orderNo+"'>" + order.orderNo + "</a></td>";
                        html += "<td>" + order.productNameShow +"</td>";
                        html += "<td>" + order.totalBuyCount +"</td>";
                        html += "<td>" + order.totalPayMoney +"元</td>";
                        html += "<td>" + order.payTypeShow +"</td>";
                        html += order.totalFreight == 0.00 ? "<td>包邮</td>":"<td>" + order.totalFreight +"元</td>";
                        html += "<td>" + order.createTime +"</td>";
                        html += "<td>";
                        html += order.orderStatusShow;
                        html += "，<a href='javascript:pay(\""+order.orderNo+"\")'>去支付</a><br/>";
                        html += " <div align='center'><b><a href='javascript:cancel(\""+order.orderNo+"\")' style = 'color:#0c0c0c;'>取消订单</a></b></div>";
                        html += "</td>";
                        html += "</tr>";
                    }
                    $("#tbd0").html(html);
                    var pageHtml = "";
                    var pageNo = parseInt($("#pageNo").val());
                    pageHtml += "<a href='javascript:orderPage("+0+","+data.data.pages+","+(pageNo + 1)+")'>点击查看更多</a>";
                    $("#span0").html(pageHtml);
                }
            );
        }

        //订单子表展示
        function getOrderInfo(no, orderStatus) {
            var index = layer.load(0, {offset: '300px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/order/getOrderInfo",
                {"pageNo":$("#pageNo").val(), "orderStatus":orderStatus},
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    if (data.data.list.length <= 0) {
                        $("#span"+no).html("暂无订单，快去逛逛吧");
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var order = data.data.list[i];
                        html += "<tr>";
                        html += "<td><a href='<%=request.getContextPath()%>/order/toOrderInfoDetails?TOKEN="+getToken()+"&orderNo="+order.orderNo+"'>" + order.orderNo + "</a></td>";
                        html += "<td>" + order.productNameShow + "</td>";
                        html += "<td>" + order.totalBuyCount + "</td>";
                        html += order.skuRateShow == '100' ? "<td>无折扣</td>":"<td>" + order.skuRateShow + "%</td>";
                        html += "<td>" + order.totalPayMoney + "元</td>";
                        html += "<td>" + order.payTypeShow + "</td>";
                        html += order.totalFreight == 0.00 ? "<td>包邮</td>":"<td>" + order.totalFreight +"元</td>";
                        html += "<td>" + order.createTime + "</td>";
                        html += order.payTime == null ? "<td>暂无</td>":"<td>" + order.payTime + "</td>";
                        html += "<td>";
                        if (order.orderStatus == "PAID") {
                            if (order.remindStatus == null || (order.remindStatus != null && new Date(order.remindTime).getTime() < new Date().getTime())) {
                                html += "<a href='javascript:remind(\""+order.orderNo+"\")'>提醒卖家发货</a>";
                            } else {
                                html += "已提醒";
                            }
                        } else if (order.orderStatus == "SHIPPED") {
                            html += "<a href='javascript:confirmReceipt(\""+order.orderNo+"\")'>确认收货</a>";
                        } else if (order.orderStatus === "CONFIRM_RECEIPT") {
                            if (order.isAllComment > 0){
                                html += "<a href='javascript:toComment(\""+order.orderNo+"\")'>评价晒单</a> | ";
                            }
                            html += "<a href='javascript:buyAgain(\""+order.orderNo+"\")'>再次购买</a>";
                        } else if (order.orderStatus == "COMPLETED" || order.orderStatus == "CANCELLED") {
                            html += "<a href='javascript:buyAgain(\""+order.orderNo+"\")'>再次购买</a>";
                        }
                        html += "</td>";
                        html += "</tr>";
                    }
                    $("#tbd"+no).append(html);
                    var pageHtml = "";
                    var pageNo = parseInt($("#pageNo").val());
                    pageHtml += "<a href='javascript:page("+no+","+data.data.pages+","+(pageNo + 1)+",\""+orderStatus+"\")'>点击查看更多</a>"
                    $("#span"+no).html(pageHtml);
                }
            );
        }

        //主订单加载
        function orderPage(index, pages, pageNo) {
            if (pageNo > pages) {
                var pageHtml = "我是有底线的~";
                $("#span"+index).html(pageHtml);
                return;
            }
            $("#pageNo").val(pageNo);
            showOrder();
        }
        //子订单加载
        function page(index, totalNum, pageNo, orderStatus) {
            if (pageNo > totalNum) {
                var pageHtml = "我是有底线的~";
                $("#span"+index).html(pageHtml);
                return;
            }
            $("#pageNo").val(pageNo);
            getOrderInfo(index, orderStatus);
        }


        //去支付
        function pay(orderNo) {
            var index = layer.load(0, {offset: '300px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/order/toPay?TOKEN="+getToken() +"&orderNo="+orderNo,
                {"orderNo":orderNo, "orderStatus":"CANCELED","_method":"PUT"},
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    window.location.reload();
                }
            );
           //parent.window.location.href = "<%=request.getContextPath()%>/order/toPay?TOKEN="+getToken() +"&orderNo="+orderNo;
        }

        //取消订单
        function cancel(orderNo) {
            var index = layer.load(0, {offset: '300px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/order/updateStatus",
                {"orderNo":orderNo, "orderStatus":"CANCELLED","_method":"PUT"},
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

        //提醒发货
        function remind(orderNo) {
            var index = layer.load(0, {offset: '300px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/order/updateRemind",
                {"orderNo":orderNo, "remindStatus":"1","_method":"PUT"},
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
        //确认收货
        function confirmReceipt(orderNo) {
            var index = layer.load(0, {offset: '300px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/order/updateStatus",
                {"orderNo":orderNo, "orderStatus":"CONFIRM_RECEIPT","_method":"PUT"},
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

        //再次购买
        function buyAgain(orderNo) {
            var index = layer.load(0, {offset: '300px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/order/buyAgain",
                {"orderNo":orderNo},
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    parent.window.location.href = "<%=request.getContextPath()%>/user/cart/toMyShoppingCart?TOKEN="+getToken();
                }
            );
        }

        //去评论
        function toComment(orderNo) {
            window.location.href = "<%=request.getContextPath()%>/order/toComment?orderNo="+orderNo+"&TOKEN="+getToken();
        }
    </script>
    <body>
        <form id="fm">
            <input type="hidden" value="1" name="pageNo" id="pageNo">
        </form>

        <div class="layui-tab" lay-filter="filter">
            <ul class="layui-tab-title">
                <li class="layui-this">待付款</li>
                <li>待收货</li>
                <li>已完成</li>
                <li>已取消</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <table class="layui-table">
                        <thead>
                        <tr>
                            <th>订单号</th>
                            <th>商品名称</th>
                            <th>购买数量</th>
                            <th>付款金额（包含邮费）</th>
                            <th>支付方式</th>
                            <th>邮费</th>
                            <th>下单时间</th>
                            <th>订单状态</th>
                        </tr>
                        </thead>
                        <tbody id="tbd0"></tbody>
                    </table>
                    <div align="center">
                        <span id="span0" ></span>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <table class="layui-table">
                        <thead>
                        <tr>
                            <th>订单号</th>
                            <th>商品信息</th>
                            <th>购买数量</th>
                            <th>折扣</th>
                            <th>付款金额（包含邮费）</th>
                            <th>支付方式</th>
                            <th>邮费</th>
                            <th>下单时间</th>
                            <th>付款时间</th>
                            <th>订单状态</th>
                        </tr>
                        </thead>
                        <tbody id="tbd1"></tbody>
                    </table>
                    <div align="center">
                        <span id="span1" ></span>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <table class="layui-table">
                        <tr>
                            <th>订单号</th>
                            <th>商品信息</th>
                            <th>购买数量</th>
                            <th>折扣</th>
                            <th>付款金额（包含邮费）</th>
                            <th>支付方式</th>
                            <th>邮费</th>
                            <th>下单时间</th>
                            <th>付款时间</th>
                            <th>订单状态</th>
                        </tr>
                        <tbody id="tbd2"></tbody>
                    </table>
                    <div align="center">
                        <span id="span2" ></span>
                    </div>
                </div>
                <div class="layui-tab-item">
                    <table class="layui-table">
                        <tr>
                            <th>订单号</th>
                            <th>商品信息</th>
                            <th>购买数量</th>
                            <th>折扣</th>
                            <th>付款金额（包含邮费）</th>
                            <th>支付方式</th>
                            <th>邮费</th>
                            <th>下单时间</th>
                            <th>付款时间</th>
                            <th>订单状态</th>
                        </tr>
                        <tbody id="tbd3"></tbody>
                    </table>
                    <div align="center">
                        <span id="span3" ></span>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
