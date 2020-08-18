<%--
  Created by IntelliJ IDEA.
  User: zhangzhikai
  Date: 2020/6/5
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>订单评价</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
</head>
<body>
<form id="fm">
    <input type="hidden" name="orderNo" value="${orderNo}">
<table class="layui-table">
    <c:forEach items="${productList}" var="p" varStatus="i">
        <tr>
            <td>${p.productName} : ${p.skuInfo}</td>
            <td>
                <input type="hidden" name="reviewsList[${i.index}].detailId" value="${p.id}">
                <input type="hidden" name="reviewsList[${i.index}].productId" value="${p.productId}">
                <input type="hidden" name="reviewsList[${i.index}].rate" value="5" id="rate_${i.index}">
                <label class="layui-form-label"> 商品评分</label>
                <div id="rateDiv_${i.index}"></div><br>
                <label class="layui-form-label"> 商品评价</label>
               <textarea name="reviewsList[${i.index}].comment"></textarea>
            </td>
        </tr>
    </c:forEach>
</table>
</form>
<input class="layui-btn" type="button" value="提交评论" onclick="addComment()">
</body>
<script>
    layui.use('rate', function(){
        var rate = layui.rate;

        <c:forEach items="${productList}" var="p" varStatus="i">
            //渲染
            var ins1 = rate.render({
                elem: '#rateDiv_${i.index}'  //绑定元素
                ,value: 5
                ,text: true
                ,setText: function(value){
                    var arrs = {
                        '1': '极差'
                        ,'2': '差'
                        ,'3': '中等'
                        ,'4': '好'
                        ,'5': '极好'
                    };
                    this.span.text(arrs[value] || ( value + "星"));
                }
                ,choose: function(value){
                    //if(value > 4) layer.msg('吼哈，一giao窝里giao！！！', {icon:6})
                    $("#rate_${i.index}").val(value);
                }
            });
        </c:forEach>
    });

    //新增评论
    function addComment() {
        var index = layer.load(1);
        token_post(
            "<%=request.getContextPath()%>/product/addComment",
            $("#fm").serialize(),
            function (data) {
                layer.close(index);
                if (data.code != 200) {
                    layer.alert(data.msg);
                    return;
                }
                window.history.go(-1);        //返回+刷新
                //window.history.back();//返回上一页
            }
        );
    }
</script>
</html>
