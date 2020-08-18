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
        <title>我的购物车</title>
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

        //获取id
        function getIds() {
            var ids=[];
            $(":checkbox[name='checked']:checked").each(function () {
                ids.push($(this).val());
            });
            return ids;
        }
        /**
         * 库存加减
         */
        //-
        function subCount(obj, skuCount) {
            var quantityObj = $(obj).next();        //next()方法，获取指定元素同辈的下一个元素
            quantityObj.val(parseInt(quantityObj.val()) - 1);
            check_count(quantityObj, skuCount);
            getTotal()
        }
        //+
        function plusCount(obj, skuCount) {
            var quantityObj = $(obj).prev();        //prev方法：获取指定元素同辈的上一个元素
            quantityObj.val(parseInt(quantityObj.val()) + 1);
            check_count(quantityObj, skuCount);
            getTotal()
        }

        //检验数量是否合格
        function check_count(quantityObj, skuCount) {
            if (!(quantityObj instanceof jQuery)) { //如果实例不是jQuery类型则得到新的数据
                quantityObj = $(quantityObj);
            }
            var quantity =parseInt(quantityObj.val());  //获取购买数量
            if (skuCount <= 0) {
                quantityObj.parent().find(".countSpan").text("无货");
                quantityObj.val(1);
            } else if (quantity > skuCount) {
                quantityObj.parent().find(".countSpan").text("库存不足");
                quantityObj.val(skuCount);
            } else if (quantity < skuCount) {
                quantityObj.parent().find(".countSpan").text("货源充足");
                if (quantity < 1) {
                    quantityObj.val(1);
                }
            }
            var cartId = quantityObj.parent().prev().find(":input[name = 'checked']").val();
            token_post(
                "<%=request.getContextPath()%>/user/cart/updateCart",
                {"cartId" : cartId, "_method" : "PUT", "quantity" : quantityObj.val()},
                function (data) {
                    if (data.code != 200) {
                        layer.msg(data.msg,{offset: '230px', icon:5, time:2000});
                    }
                }
            )
        }

        $(function () {
            getTotal();
        })

        //全选 取消全选
        function checkAll() {
            var allCheck = document.getElementById("allCheck");
            var checked = document.getElementsByName("checked");
            for(var i = 0; i < checked.length; i++){
                checked[i].checked = allCheck.checked;
            }
            getTotal();
        }

        //金额计算
        function getTotal() {
            var ids = getIds();                         //购物车id集合
            var number = ids.length;                    //个数
            var checkedLength = $("input[type='checkbox'][class='checkNum']").length; //选中集合长度
            var checkedSelect = $("input[type='checkbox'][class='checkNum']:checked").length; //已选中得值

            if (number == 0) {
                $("#total").hide();
                $("#virtualTotal").show();
                ids.push(-1);
            } else {
                $("#total").show();
                $("#virtualTotal").hide();
            }
            if(!$(".checkNum").checked){
                $("#allCheck").prop("checked",false);   // 子复选框某个不选择，全选也被取消
            }
            if(checkedLength===checkedSelect){
                $("#allCheck").prop("checked",true);   // 子复选框全部部被选择，全选也被选择；1.对于HTML元素我们自己自定义的DOM属性，在处理时，使用attr方法；2.对于HTML元素本身就带有的固有属性，在处理时，使用prop方法。
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

        function removeChecked() {
            var ids = getIds();                         //购物车id集合
            if (ids.length < 1) {
                layer.msg("请选择一条");
                return;
            }
            var index = layer.load(0, {offset: '230px', shade:0.5});
            layer.confirm("确认删除？", {offset: '230px', icon: 3, title:'提示'}, function(index){
                token_post(
                    "<%=request.getContextPath()%>/user/cart/deleteCart",
                    {"ids":ids.join(","), "_method":"DELETE"},
                    function (data) {
                        layer.msg(data.msg,{offset: '230px', icon:6, time:2000},function () {
                            window.location.href = "<%=request.getContextPath()%>/user/cart/toMyShoppingCart?TOKEN="+getToken();
                        });
                    }
                );
                layer.close(index);
            }, function () {
                layer.close(index);
            });
        }
        //删除 不想要了
        function removeThis(cartId) {
            var index = layer.load(0, {offset: '230px', shade:0.5});
            layer.confirm("确认删除？", {offset: '230px', icon: 3, title:'提示'}, function(index){
                token_post(
                    "<%=request.getContextPath()%>/user/cart/deleteCart",
                    {"ids":cartId, "_method":"DELETE"},
                    function (data) {
                        layer.msg(data.msg,{icon:6, time:2000},function () {
                            window.location.href = "<%=request.getContextPath()%>/user/cart/toMyShoppingCart?TOKEN="+getToken();
                        });
                    }
                );
                layer.close(index);
            }, function () {
                layer.close(index);
            });
        }

        //去结算
        function toConfirmOrder() {
            if(!$(".checkNum").is(':checked')){
                layer.msg("请选择要购买的商品再进行结算", {icon:6});
                return;
            }
            window.location.href = "<%=request.getContextPath()%>/user/toConfirmOrder?TOKEN="+getToken();
        }

        //修改复选框是否选中
        function updateChecked(cartId, checked) {
            checked = checked == true ? 0 : 1;
            token_post(
                "<%=request.getContextPath()%>/user/cart/updateCart",
                {"cartId":cartId, "_method":"PUT", "checked":checked},
                function (data) {
                    if (data.code != 200) {
                        layer.alert(data.msg);
                    }
                }
            )
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
            </ul>
        </div>
    </div><br/>
        <div class="layui-container">
            <div class="layui-row layui-col-space5">
                <div class="layui-col-md8">
                    <div class="layui-col-md2">
                        <input type="checkbox" onclick="checkAll()" id="allCheck"><span id="whetherToSelectAll">全选</span>
                    </div>
                    <div class="layui-col-md5">
                        <a href="javascript:removeChecked()" style="color: #1E9FFF">删除选中商品</a>
                    </div>
                </div>
            </div>
        </div><br/>
        <c:forEach items="${cartList}" var="cart">
            <div class="layui-container">
                <fieldset style="width: 1000px; height: 200px">
                    <legend>商品信息:</legend>
                    <form id="fm">
                        <div class="layui-row layui-col-space5">
                            <div class="layui-col-md8">
                                <div class="layui-col-md2" style="height: 200px">
                                    <c:if test="${cart.skuCount < 1}">
                                        <input type="checkbox" id="disable" class="layui-btn layui-btn-xs layui-btn-disabled">
                                    </c:if>
                                    <c:if test="${cart.skuCount > 1}">
                                        <input type="checkbox" name="checked" class="checkNum" <c:if test="${cart.checked == 0}">checked</c:if> value="${cart.cartId}" onchange="updateChecked(this.value, this.checked)" onclick="getTotal()">
                                    </c:if>
                                </div>
                                <div class="layui-col-md5" style="height: 70px">
                                    名称：${cart.productName}
                                </div>
                                <div class="layui-col-md5" style="height: 70px">
                                    原价：￥${cart.skuPrice}元
                                </div>
                                <div class="layui-col-md5" style="height: 70px">
                                    商品信息：${cart.skuAttrValueNames}
                                </div>
                                <div class="layui-col-md5" style="height: 70px">
                                    折扣：<c:if test="${cart.skuRate == '100'}">无,按照原价</c:if><c:if test="${cart.skuRate != '100'}">${cart.skuRate}%</c:if>
                                </div>
                                <div class="layui-col-md5" style="height: 70px">
                                    邮费：<c:if test="${cart.freight == '0.00'}">包邮</c:if><c:if test="${cart.freight != '0.00'}">${cart.freight}元</c:if>
                                </div>
                                <div class="layui-col-md5" style="height: 70px">
                                    现价：￥<span style="color: red"><fmt:formatNumber type="number" value="${cart.skuPrice * cart.skuRate * 0.01}" maxFractionDigits="2"/>元</span>
                                </div>
                            </div>
                            <div class="layui-col-md4">
                                购买数量：
                                <input type="button" value="-"  onclick="subCount(this, ${cart.skuCount})" class="layui-btn layui-btn-normal">
                                <input type="number" name="quantity" id="quantity" value="${cart.quantity}" onkeyup="check_count(this, ${cart.skuCount})" max="200" min="1" style="width: 120px; height: 38px">
                                <input type="button" value="+"  onclick="plusCount(this, ${cart.skuCount})" class="layui-btn layui-btn-normal">
                                <div align="center">
                                    <br><span class="countSpan" style="color: #FF5722">货源充足</span><br><br>
                                    <a href="javascript:removeThis(${cart.cartId})" style="color: #1E9FFF">后悔了，不要了</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </fieldset>
            </div><br/>
        </c:forEach>

        <div id="virtualTotal" align="center">
            <span style="color: red; font-size: 31px; font-family: 楷体, serif" >合计：0元</span>
        </div>
        <div id="total" align="center">
            已选择<b><span style="color: red; font-size: 21px" id="number"></span></b>件商品，
            总商品金额：<b><span id="totalPrice">￥</span>元</b>，
            商品折后金额：<b><span id="totalDiscountedPrice">￥</span>元</b>，
            运费：<b><span id="totalFreight">￥</span></b><br/>
            应付总额：<b><span style="color: red; font-size: 21px" id="finalPrice">￥</span>元</b>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <label class="layui-form-label"></label>
                <div style="position: fixed; left: 200px; bottom: 0px; width: 1010px;">
                    <input type="button" value="去结算" onclick="toConfirmOrder()" class="layui-btn layui-btn-fluid layui-btn-radius layui-btn-normal">
                </div>
            </div>
        </div>
    </body>
</html>
