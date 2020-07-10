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
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <script type="text/javascript">
      $(function () {
            show();
        });

        function show() {
            var index = layer.load(0, {offset: '230px', shade:0.5});
            $.get(
                "<%=request.getContextPath()%>/product/",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code !== 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var pro = data.data.list[i];
                        html += "<tr>";
                        html += "<td width='200px'>" + pro.productName +"</td>";
                        html += "<td>" + pro.skuPriceShow +"</td>";
                        html += "<td>" + pro.skuCountShow +"</td>";
                        html += "<td>" + pro.productType +"</td>";
                        html += pro.skuRateShow === "0" ? "<td>无折扣</td>" : "<td>"+ pro.skuRateShow +"%</td>";
                        html += pro.freightShow === "0.00" ? "<td>"+ pro.company +"-包邮</td>" : "<td>" +pro.company +" - "+ pro.freightShow +"元</td>";
                        html += "<td><img src='http://qcxz8bvc2.bkt.clouddn.com/"+pro.productImg+"' style='width: 70px; height: 70px'></td>";
                        html += "<td>" + pro.productDescribe +"</td>";
                        html += pro.praiseNumber == null ? "<td>暂无点赞量</td>" : "<td>" + pro.praiseNumber +"</td>";
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
        //模糊查
        function fuzzySearch() {
            $("#pageNo").val(1);
            show();
        }
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();//重点在这里
            form.on('checkbox(productType)', function(){
                fuzzySearch();
            });
        });

        //去新增商品
        function toAddProduct() {
            window.location.href = "<%=request.getContextPath()%>/product/spu/toAddProduct";
        }

    </script>
    <body class="layui-layout-body" style="overflow: hidden">
        <div class="layui-layout layui-layout-admin">
            <div class="layui-header">
                <div class="layui-logo" style="color: coral">点金商城</div>
                <ul class="layui-nav layui-layout-left">
                    <li class="layui-nav-item"><a href="<%=request.getContextPath()%>/auth/user/logout" style="color: aqua">退出登录</a></li>
                </ul>
                <ul class="layui-nav layui-layout-right">
                    <li class="layui-nav-item">欢迎登录</li>
                </ul>
            </div>
        </div>
        <form class="layui-form" id="fm">
            <input type="hidden" value="1" name="pageNo" id="pageNo"><br/>
            <div class="layui-form-item">
                <label class="layui-form-label">商品名称</label>
                <div class="layui-input-inline" style="width: 225px;">
                    <input type="text" name="productName" placeholder="请输入商品名称" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <input type="button" value="搜索" onclick="fuzzySearch()" class="layui-btn layui-btn-normal">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">价格</label>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="skuPriceMin" placeholder="￥" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-mid">-</div>
                    <div class="layui-input-inline" style="width: 100px;">
                        <input type="text" name="skuPriceMax" placeholder="￥" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">商品类型</label>
                <div class="layui-input-block">
                    <jsp:useBean id="productTypeList" scope="request" type="java.util.List"/>
                    <c:forEach items="${productTypeList}" var="type">
                        <input type="checkbox"  name="productTypeList" lay-filter="productType" value="${type.productType}" title="${type.productType}">
                    </c:forEach>
                </div>
            </div>
        </form>
        <table border="0px" class="layui-table" >
            <colgroup>
                <col width="100">
                <col width="100">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>名称</th>
                <th>价格</th>
                <th>库存</th>
                <th>分类</th>
                <th>折扣</th>
                <th>邮费</th>
                <th>商品图片</th>
                <th>描述</th>
                <th>点赞量</th>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
        <div id="pageInfo"></div>
    </body>
</html>
