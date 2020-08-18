<%--
  ~ 作者：CF
  ~ 日期：2020-07-17 09:38
  ~ 项目：djmall
  ~ 模块：djmall_platform
  ~ 类名：show.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/17
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>确认订单</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
    </head>
    <style type="text/css">
        #disable{height:12px;line-height:22px;padding:0 5px;font-size:12px}
        .form-control{height: 40px; width:770px; border:1px solid #1E9FFF}
    </style>
    <script type="text/javascript">
        //是否登录
        $(function () {
            $("#hiddenLogout").hide();
            var token = cookie.get("TOKEN");
            var nickName = cookie.get("NICK_NAME");
            // 是否登录
            if (token === undefined && nickName === undefined) {
                $("#login").html("<i class='layui-icon layui-icon-username' style='font-size: 30px'></i>点我登录");
                $("#login").attr("href", "<%=request.getContextPath()%>/user/toLogin");
                $("#hiddenRegister").show();
                $("#hiddenLogout").hide();
            }  else if (token !== '' && nickName !== '') {
                $("#login").html("<i class='layui-icon layui-icon-username' style='font-size: 30px'></i>"+nickName);
                $("#login").attr("href", "<%=request.getContextPath()%>/user/index/toIndex?TOKEN=" + getToken());
                $("#hiddenRegister").hide();
                $("#hiddenLogout").show();
            }
        })

        //退出登录
        function signOut() {
            var index = layer.load(0,{offset: '230px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/user/toLogout?TOKEN="+getToken(),
                {"_method": "DELETE"},
                function (data) {
                    layer.close(index);
                    layer.msg(data.msg, {offset: '230px', icon: 6, time: 2000},
                        function(){
                            cookie.clear("TOKEN");
                            cookie.clear("NICK_NAME");
                            window.location.href = "<%=request.getContextPath()%>/product/toShow";
                        });
                })
        }
        //我的购物车
        function toMyShoppingCart() {
            window.location.href = "<%=request.getContextPath()%>/user/cart/toMyShoppingCart?TOKEN="+getToken();
        }

        //新增收货地址
        function toNewShippingAddress() {
            //iframe层
            layer.open({
                type: 2,
                title: '新增收货地址',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                offset: '230px',
                area: ['800px', '50%'],
                content: '<%=request.getContextPath()%>/user/address/toNewShippingAddress?TOKEN='+getToken()
            });
        }

        //获取id
        function getIds() {
            var ids=[];
            $(":checkbox[name='checked']:checked").each(function () {
                ids.push($(this).val());
            });
            return ids;
        }

        $(function () {
            getTotal();
        })

        //金额计算 修改选中状态
        function getTotal() {
            var ids = getIds();                         //购物车id集合
            var number = ids.length;                    //个数
            if (number === 0) {
                $("#total").hide();
                $("#virtualTotal").show();
                ids.push(-1);
            } else {
                $("#total").show();
                $("#hiddenCheck").hide();
                $("#virtualTotal").hide();
            }
            token_post(
                "<%=request.getContextPath()%>/user/cart/getTotal?TOKEN="+getToken(),
                {"ids" : ids},
                function (data) {
                    var money = data.data;
                    $("#number").html(number);
                    $("#totalPrice").html(money.totalPrice);
                    $("#totalDiscountedPrice").html(money.totalDiscountedPrice);
                    $("#totalFreight").html(money.totalFreight == '0.00' ? '包邮' : money.totalFreight + "元");
                    $("#finalPrice").html(money.finalPrice);
                })
        }

        //取消订单
        function cancelOrder() {
            history.back();
        }

        //确认订单
        function confirmOrder() {
            var index = layer.load(0, {shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/order/addOrder?TOKEN="+getToken(),
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if(data.code !== 200){
                        layer.msg(data.msg, {offset: '230px', icon:5, time:5000});
                        return;
                    }
                    layer.msg(data.msg, {offset: '230px', icon: 6, time: 2000},
                        function(){
                            window.location.href = "<%=request.getContextPath()%>/user/index/toIndex?TOKEN="+getToken();
                    });
                })
        }

    </script>
    <body>
        <div class="layui-layout layui-layout-admin">
            <div class="layui-header">
                <div class="layui-logo" style="color: coral">点金商城</div>
                <div id="hiddenLogout">
                    <ul class="layui-nav layui-layout-left">
                        <li class="layui-nav-item" >
                            <a href="javascript:signOut()" style="color: springgreen"><i class="layui-icon layui-icon-logout" style="font-size: 30px"></i>退出登录</a>
                        </li>
                    </ul>
                </div>
                <ul class="layui-nav layui-layout-right">
                    <li class="layui-nav-item">
                        <a href="<%=request.getContextPath()%>/product/toShow"  style="color: aqua"><i class="layui-icon layui-icon-home" style="font-size: 30px"></i>首页</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="<%=request.getContextPath()%>/user/toLogin" id="login" style="color: violet"><i class="layui-icon layui-icon-username" style="font-size: 30px"></i>点我登录</a>
                    </li>
                    <li class="layui-nav-item" id="hiddenRegister">
                        <a href="<%=request.getContextPath()%>/user/toRegister" style="color: tomato"><i class="layui-icon layui-icon-user" style="font-size: 30px"></i>注册</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:toMyShoppingCart()" style="color: dodgerblue"><i class="layui-icon layui-icon-cart" style="font-size: 30px"></i>我的购物车</a>
                    </li>
                </ul>
            </div>
        </div><br/>
        <form id="fm">
            <div class="layui-container">
                <div class="layui-row layui-col-space5">
                    <div class="layui-col-md12">
                        <div class="layui-form-item">
                            <label class="layui-form-label">收货人信息</label>
                            <select name="addressId" class="form-control">
                                <c:forEach items="${addressList}" var="addr">
                                    <option value="${addr.id}">${addr.receiverName}-${addr.phone}-${addr.detailedAddress}</option>
                                </c:forEach>
                            </select><a href="javascript:toNewShippingAddress()">没有地址？去添加</a>
                        </div>
                    </div>
                </div>
            </div><br/>
            <c:forEach items="${cartList}" var="cart">
                <c:if test="${cart.checked == 0}">
                    <div class="layui-container">
                        <fieldset style="width: 1000px; height: 150px">
                            <legend>商品信息:</legend>
                                <div class="layui-row layui-col-space5">
                                    <div class="layui-col-md12">
                                        <div class="layui-col-md4" style="height: 70px">
                                            名称：${cart.productName}
                                        </div>
                                        <div class="layui-col-md4" style="height: 70px">
                                            原价：￥${cart.skuPrice}元
                                        </div>
                                        <div class="layui-col-md4" style="height: 70px">
                                            数量：x${cart.quantity}
                                        </div>
                                        <div class="layui-col-md4" style="height: 70px">
                                            商品信息：${cart.skuAttrValueNames}
                                        </div>
                                        <div class="layui-col-md4" style="height: 70px">
                                            折扣：<c:if test="${cart.skuRate == '100'}">无,按照原价</c:if>
                                            <c:if test="${cart.skuRate != '100'}">
                                                ${cart.skuRate}%&nbsp;
                                                现价：￥<span style="color: red"><fmt:formatNumber type="number" value="${cart.skuPrice * cart.skuRate * 0.01}" maxFractionDigits="2"/>元</span>
                                            </c:if>
                                        </div>
                                        <div class="layui-col-md4" style="height: 70px">
                                            邮费：<c:if test="${cart.freight == '0.00'}">包邮</c:if><c:if test="${cart.freight != '0.00'}">${cart.freight}元</c:if>
                                        </div>
                                    </div>
                                </div>
                        </fieldset>
                    </div><br/>
                </c:if>
            </c:forEach>

            <div id="virtualTotal" align="center">
                <span style="color: red; font-size: 31px; font-family: 楷体, serif" >合计：0元</span>
            </div>
            <div id="total" align="center">
                已选择<b><span style="color: red; font-size: 21px" id="number"></span></b>件商品，
                总商品金额：<b><span id="totalPrice">￥</span>元</b>，
                商品折后金额：<b><span id="totalDiscountedPrice">￥</span>元</b>，
                运费：<b><span id="totalFreight">￥</span></b><br/>
                应付总额：<b><span style="color: red; font-size: 21px" id="finalPrice">￥</span>元</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                支付方式：
                <select name="payType">
                    <c:forEach items="${payTypeList}" var="pay">
                        <option value="${pay.baseCode}">${pay.baseName}</option>
                    </c:forEach>
                </select>
            </div>
            <div  id="hiddenCheck">
                <c:forEach items="${cartList}" var="cart">
                    <c:if test="${cart.checked == 0}">
                        <input type="hidden" name="skuIdList" value="${cart.skuId}">
                        <input type="hidden" name="cartIdList" value="${cart.cartId}">
                        <input type="hidden" name="quantityList" value="${cart.quantity}">
                        <input type="checkbox" name="checked" <c:if test="${cart.checked == 0}">checked</c:if> value="${cart.cartId}" onclick="getTotal()">
                    </c:if>
                </c:forEach>
            </div>
        </form>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <label class="layui-form-label"></label>
                <div class="left-toolbox" style="position: fixed; left: 1300px; bottom: 0px; width: 1010px;">
                    <input type="button" value="确认订单" onclick="confirmOrder()" class="layui-btn layui-btn-normal">
                    <input type="button" value="取消订单" onclick="cancelOrder()" class="layui-btn layui-btn-normal">
                </div>
            </div>
        </div>
    </body>
</html>
