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
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
    </head>
    <style type="text/css">
        .error{
            color: red;
        }
        a{color: #0000FF;}
    </style>
    <script type="text/javascript">
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });

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

        //商品sku联动
        function getSkuBySkuId(skuId) {
            $.get(
                "<%=request.getContextPath()%>/product/getSkuBySkuId",
                {"skuId" : skuId},
                function (data) {
                    if (data.code !== 200) {
                        layer.msg(data.msg, {offset: '230px'});
                        return;
                    }
                    var sku = data.data;
                    var html = "";
                    html +="<div class='layui-col-md6' style='height: 70px'> 原价：￥"+sku.skuPrice+"元</div>";
                    if (sku.skuRate === "100") {
                        html +="<div class='layui-col-md6' style='height: 70px'> 折扣：无，按照原价</div>";
                    }else {
                        html +="<div class='layui-col-md6' style='height: 70px'> 折扣："+sku.skuRate+"%</div>";
                    }
                    var quantity = parseInt($("#quantity").val());
                    if (sku.skuCount <= 0) {                            //无货
                        $(".countSpan").text("无货");
                    } else if (sku.skuCount < quantity) {               //库存不足
                        $(".countSpan").text("库存不足");
                    } else if (quantity < sku.skuCount) {
                        $(".countSpan").text("货源充足");
                    }
                    html += '<input type="hidden" value="'+sku.skuCount+'" id="skuCount">';
                    $("#skuSpan").html(html);
            })
        }

        //购买数量
        $(function () {
            check_count();
        })
        //加库存
        function plusCount() {
            $("#quantity").val(parseInt($("#quantity").val()) + 1);
            check_count();
        }
        //减库存
        function subCount() {
            $("#quantity").val(parseInt($("#quantity").val()) - 1);
            check_count();
        }
        //校验数量
        function check_count() {
            var skuCount = parseInt($("#skuCount").val());
            var quantity = parseInt($("#quantity").val());
            if (skuCount <= 0) {                            //无货
                $(".countSpan").text("无货");
                $("#quantity").val(1);
                /*$("#cartBtn").attr("disabled", "true");*/
                /*$("#buyBtn").attr("disabled", "true");*/
            } else if (skuCount < quantity) {               //库存不足
                $(".countSpan").text("库存不足");
                $("#quantity").val(skuCount);
            } else if (quantity < skuCount) {
                $(".countSpan").text("货源充足");
                if (quantity < 1) {
                    $("#quantity").val(1);
                }
            }
        }

        //加入购物车
        function addToShoppingCart() {
            var index = layer.load(0,{shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/user/cart/addToShoppingCart?TOKEN="+getToken(),
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code !== 200) {
                        layer.msg(data.msg, {offset: '230px', icon: 5, time: 2000})
                        return;
                    }
                    layer.msg(data.msg, {offset: '230px', icon: 6, time: 2000},
                        function(){
                            window.location.href = "<%=request.getContextPath()%>/product/toProductDetails?id="+${product.productId};
                        });
                }
            )
        }

        //立即购买
        function buyNow() {
            var index = layer.load(0,{shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/user/cart/addToShoppingCart?TOKEN="+getToken(),
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code !== 200) {
                        layer.msg(data.msg, {offset: '230px', icon: 5, time: 2000})
                        return;
                    }
                    window.location.href = "<%=request.getContextPath()%>/user/toConfirmOrder?TOKEN="+getToken();
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
                    <li class="layui-nav-item">
                        <a href="javascript:toMyShoppingCart()" style="color: dodgerblue"><i class="layui-icon layui-icon-cart" style="font-size: 30px"></i>我的购物车</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="layui-container">
            <fieldset style="width: 1000px; height: 700px">
                <legend>商品信息:</legend>
                <form id="fm">
                    <input type="hidden" value="${product.productId}" name="productId">
                    <input type="hidden" value="${product.productName}" name="productName">
                    <div class="layui-row layui-col-space5">
                        <div class="layui-col-md6">
                            <img src="http://qeu5389un.bkt.clouddn.com/${product.productImg}" height="400px" width="450px">
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
                                            <c:if test="${sku.skuRate == '100'}">无，按照原价</c:if>
                                            <c:if test="${sku.skuRate != '100'}">${sku.skuRate}%</c:if>
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
                                点赞量：共有<i style="color: red">${product.praiseNumber}</i>个点赞
                            </div>
                            <div class="layui-col-md6" style="height: 70px">
                                评论量：共有<i style="color: red">${product.reviewsNumber}</i>条评论
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

                                <input type="button" value="-"  onclick="subCount()" class="layui-btn layui-btn-normal">
                                <input type="number" name="quantity" id="quantity" value="1" max="200" min="1" onkeyup="check_count()" placeholder="请输入购买数量"  style="width: 120px; height: 38px">
                                <input type="button" value="+"  onclick="plusCount()" class="layui-btn layui-btn-normal"><br/>
                                <label class="layui-form-label"></label><span class="countSpan" style="color: #FF5722">库存充足</span>
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
            <fieldset style="width: 1000px; height: 700px">
                <legend>商品评论</legend>
                <form class="layui-form" id="commentFm">
                    <input type="hidden" name="pageNo" id="pageNo" value="1">
                    <input type="hidden" name="productId" value="${product.productId}">
                    好评率:<i style="font-size: 30px; color: red">${goodRate}%</i>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <input type="radio" name="commentType" value="0" lay-filter="commentType" title = "所有评论" checked>
                            <input type="radio" name="commentType" value="1" lay-filter="commentType" title = "好评">
                            <input type="radio" name="commentType" value="2" lay-filter="commentType" title = "中评">
                            <input type="radio" name="commentType" value="3" lay-filter="commentType" title = "差评">
                        </div>
                    </div>
                    <hr>
                    <table class="layui-table" id="tbd">

                    </table>
                </form>
                <div id="pageInfo" align="center"></div>
            </fieldset>
        </div>
    </body>
    <script type="text/javascript">
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();//重点在这里
            form.on('radio(commentType)', function(){
                search();
            });
        });

        $(function () {
            search();
        })

        function search() {
            var index = layer.load(1);
            $.post(
                "<%=request.getContextPath()%>/product/getCommentByProductId",
                $("#commentFm").serialize(),
                function (data) {
                    layer.close(index);

                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var review = data.data.list[i];
                        html += "<tr>";
                        html += "<td>";
                        html += review.nickName + "<br>";
                        html += review.createTime;
                        html += "</td>";
                        html += "<td>";
                        html += "<div id='rateDiv_"+i+"'></div><br>";
                        html += review.comment + "<br>";
                        html += "</td>";
                        html += "</tr>";
                        for (var j = 0; j < review.replyList.length; j++) {
                            var reply = review.replyList[j];
                            html += "<tr>";
                            html += "<td>";
                            html += "商家回复：" + "<br>";
                            html += reply.createTime;
                            html += "</td>";
                            html += "<td>";
                            html += reply.comment + "<br>";
                            html += "</td>";
                            html += "</tr>";
                        }
                    }
                    $("#tbd").html(html);
                    //星星回显 一定要放在拼接赋值后边
                    layui.use(['rate'], function(){
                        var rate = layui.rate;

                        for (var i = 0; i < data.data.list.length; i++) {
                            var review = data.data.list[i];
                            //渲染
                            //只读
                            rate.render({
                                elem: '#rateDiv_'+i  //绑定元素
                                ,value: review.rate
                                ,readonly: true
                            });
                        }
                    });
                    var pageHtml = "";
                    var pageNo = parseInt($("#pageNo").val());
                    pageHtml += "<a href='javascript:page("+0+","+data.data.pages+","+(pageNo + 1)+")'>点击查看更多</a>";
                    $("#pageInfo").html(pageHtml);
                }
            );
        }

        //加载
        function page(index, pages, pageNo) {
            if (pageNo > pages) {
                var pageHtml = "我是有底线的~";
                $("#pageInfo").html(pageHtml);
                return;
            }
            $("#pageNo").val(pageNo);
            search();
        }
    </script>
</html>
