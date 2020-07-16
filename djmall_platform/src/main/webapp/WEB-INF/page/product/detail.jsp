<%--
  ~ 作者：CF
  ~ 日期：2020-07-14 15:09
  ~ 项目：djmall
  ~ 模块：djmall_platform
  ~ 类名：detail.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/14
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>商品详情</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style type="text/css">
        .error{
            color: red;
        }
    </style>
    <script type="text/javascript">
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });

        //商品sku联动
        function getSkuBySkuId(skuId) {
            $.get(
                "<%=request.getContextPath()%>/product/getSkuBySkuId",
                {"skuId" : skuId},
                function (data) {
                    if (data.code !== 200) {
                        layer.msg(data.msg);
                        return;
                    }
                    var sku = data.data;
                    var html = "";
                    html +="<div class='layui-col-md6' style='height: 70px'> 原价：￥"+sku.skuPrice+"元</div>";
                    if (sku.skuRate === "0") {
                        html +="<div class='layui-col-md6' style='height: 70px'> 折扣：无，按照原价</div>";
                    }else {
                        html +="<div class='layui-col-md6' style='height: 70px'> 折扣："+sku.skuRate+"%</div>";
                    }
                    html += '<input type="hidden" value="'+sku.skuCount+'" id="skuCount">';
                    $("#skuSpan").html(html);
            })
        }
        //购买数量
        $(function () {
            check_count();
        })

        function check_count() {
            var skuCount = parseInt($("#skuCount").val());
            var quantity = parseInt($("#quantity").val());
            if (skuCount <= 0) {                            //无货
                $("#countAdequate").hide();
                $("#countSpan").text("无货");
                $("#cartBtn").attr("disabled", "true");
                $("#buyBtn").attr("disabled", "true");
            } else if (skuCount < quantity) {               //库存不足
                $("#countAdequate").hide();
                $("#countSpan").text("库存不足");
            }else if (quantity > 200) {
                $("#quantity").val(200);
            } else if (quantity <= 0) {
                $("#quantity").val(1);
            }
        }

        //加库存
        function plusCount() {
            if ($("#quantity").val() >199) {
                layer.msg("最大购买数量为200个",{icon:5, time:2000});
                return;
            }
            $("#quantity").val(parseInt($("#quantity").val()) + 1);
            check_count();
        }

        //减库存
        function subCount() {
            if ($("#quantity").val() < 2) {
                layer.msg("不能再减了",{icon:5, time:2000});
                return;
            }
            $("#quantity").val(parseInt($("#quantity").val()) - 1);
            check_count();
        }

        $(function(){
            $("#fm").validate({
                errorPlacement: function(error, element) {
                    if (element.is("[name='quantity']")) {
                        error.appendTo($("#quantityError"));   //错误信息增加在id为‘radio-lang’中
                    } else {
                        error.insertAfter(element);//其他的就显示在添加框后
                    }
                },
                rules:{
                    quantity:{
                        required: true,    //要求输入不能为空
                        number: true,     //输入必须是数字
                        min: 1,          //输入的数字最小值为0.01，不能为0或者负数
                        max: 200
                    }
                },
                messages:{
                    quantity:{
                        required: "请填写购买数量",
                        number: "输入必须是数字",
                        min: "数量低于范围~",
                        max: "数量高于范围~"
                    }
                }
            })
        });

        //加入购物车
        function addToShoppingCart() {
            var index = layer.load(0,{shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/user/cart/addToShoppingCart?TOKEN="+getToken(),
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code !== 200) {
                        layer.msg(data.msg, {icon: 5, time: 2000})
                        return;
                    }
                    layer.msg(data.msg, {icon: 6, time: 2000},
                        function(){
                            window.location.href = "<%=request.getContextPath()%>/product/toProductDetails?id="+${product.productId};
                        });
                }
            )
        }
    </script>
    <body>
    <div class="layui-container">
        <fieldset style="width: 1000px; height: 700px">
            <legend>商品信息:</legend>
            <form id="fm">
                <input type="hidden" value="${product.productId}" name="productId">
                <input type="hidden" value="${product.productName}" name="productName">
                <div class="layui-row layui-col-space5">
                    <div class="layui-col-md6">
                        <img src="http://qcxz8bvc2.bkt.clouddn.com/${product.productImg}" height="400px" width="450px">
                    </div>
                    <div class="layui-col-md6">
                        <div class="layui-col-md6" style="height: 70px">
                            商品名称：${product.productName}
                        </div>
                        <div id="skuSpan">
                            <c:forEach items="${sku}" var="sku">
                                <c:if test="${sku.isDefault == 'HAVE_DEFAULT'}">
                                    <div class="layui-col-md6" style="height: 70px">
                                        原价：￥${sku.skuPrice}元
                                    </div>
                                    <div class="layui-col-md6" style="height: 70px">
                                        折扣：
                                        <c:if test="${sku.skuRate == '0'}">无，按照原价</c:if>
                                        <c:if test="${sku.skuRate != '0'}">${sku.skuRate}%</c:if>
                                    </div>
                                    <input type="hidden" value="${sku.skuCount}" id="skuCount">
                                    <input type="hidden" value="${sku.skuId}" name="skuId">
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="layui-col-md6" style="height: 70px">
                            邮费：
                            <c:forEach items="${freight}" var="freight">
                                <c:if test="${product.freightId == freight.freightId}">
                                    <c:if test="${freight.freight == '0.00'}">包邮</c:if>
                                    <c:if test="${freight.freight != '0.00'}">${freight.freight}元</c:if>
                                </c:if>
                            </c:forEach>
                        </div>
                        <div class="layui-col-md12" style="height: 70px">
                            商品描述：${product.productDescribe}
                        </div>
                        <div class="layui-col-md6" style="height: 70px">
                            点赞量：10086
                        </div>
                        <div class="layui-col-md6" style="height: 70px">
                            评论量：10086
                        </div>
                        <div class="layui-col-md12" style="height: 40px">
                            选择商品信息:
                        </div>
                        <div class="layui-col-md12" style="height: 150px">
                            <c:forEach items="${sku}" var="sku" varStatus="status">
                                <input type="radio" value="${sku.skuId}"  onclick="getSkuBySkuId(this.value)" name="skuId" <c:if test="${sku.isDefault == 'HAVE_DEFAULT'}">checked</c:if>>${sku.skuAttrValueNames}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <c:if test="${status.count % 2 == 0}"><br/></c:if>
                            </c:forEach>
                        </div>
                        <div class="layui-col-md12" style="height: 100px">
                            购买数量：
                            <input type="button" value="-"  onclick="subCount()" class="layui-btn layui-btn-radius layui-btn-normal">
                            <input type="number" name="quantity" id="quantity" value="1" max="200" min="1" onchange="check_count()" placeholder="请输入购买数量"  style="width: 120px; height: 38px">
                            <input type="button" value="+"  onclick="plusCount()" class="layui-btn layui-btn-radius layui-btn-normal">&nbsp;&nbsp;<span id="quantityError"></span><br/>
                            <label class="layui-form-label"></label><span id="countAdequate" style="color: dodgerblue">库存充足</span><span id="countSpan"  style="color: red"></span><br/>
                        </div>
                        <div class="layui-col-md6" style="height: 70px">
                            <input type="button" value="加入购物车" onclick="addToShoppingCart()" id="cartBtn" class="layui-btn layui-btn-normal">
                        </div>
                        <div class="layui-col-md6" style="height: 70px">
                            <input type="button" value="立即购买" onclick="buyNow()" id="buyBtn" class="layui-btn layui-btn-normal">
                        </div>
                    </div>
                </div>
            </form>
        </fieldset>

        <fieldset>
            <legend>商品评论:</legend>
            奥利给
        </fieldset>
    </div>

    </body>
</html>
