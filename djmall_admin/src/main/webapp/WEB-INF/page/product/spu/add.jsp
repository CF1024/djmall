<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/2
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>商品新增</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <script type="text/javascript">
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use(['form', 'table'], function () {
            var form = layui.form;
            form.render();//重点在这里
            form.on('select(productType)', function(){
                loadSkuGmRelatedAttr($("#productType").val());
            });
        });

        $(function () {
            loadSkuGmRelatedAttr($("#productType").val());
        });

        function loadSkuGmRelatedAttr(productType) {
            var index = layer.load(0, {shade:0.5});
            $.get(
                "<%=request.getContextPath()%>/product/spu/"+productType,
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        var attr = data.data[i];
                        html += "<tr>";
                        html += "<td>" + attr.attrName +"";
                        html += "<input type='hidden' value='"+attr.attrId+"' />";
                        html += "<input type='hidden' value='"+attr.attrName+"' />";
                        html += "</td>";
                        html += "<td>";
                        var attrValueIdList = attr.attrValueIds.split(",");
                        var attrValueList = attr.attrValues.split(",");
                        for (var j = 0; j < attrValueIdList.length; j++) {
                            var attrValueId = attrValueIdList[j];
                            html += "<input type='checkbox' value='"+attrValueId+", "+attrValueList[j]+"'  />"+attrValueList[j];
                        }
                        html += "</td>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                }
            );
        }
        //自定义sku
        function addCustomSkuAttr() {
            var content = "<br/>自定义属性名：<input type = 'text' id='customAttrName'/><br/><br/>";
            content += "自定义属性值：<input type = 'text' id='customAttrValue'/><br/>";
            content += "<span style='color: red'>(多个属性值请用逗号分割(不区分中英文逗号))</span>";
            //页面层
            layer.open({
                type: 1,
                title: '自定义属性',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['40%', '70%'],
                content: content,
                btn: ['确认', '取消'],
                yes: function(index, layero) {
                    var customAttrName = $("#customAttrName").val();
                    var customAttrValue = $("#customAttrValue").val();

                    if (customAttrName == '' || customAttrName == 'undefined'
                        || customAttrName == 'null' || customAttrName == ' ') {
                        layer.alert('属性名不能为空!');
                        return;
                    }
                    if (customAttrValue == '' || customAttrValue == 'undefined'
                        || customAttrValue == 'null' || customAttrValue == ' ') {
                        layer.alert('属性值不能为空!');
                        return;
                    }
                    if (customAttrValue[customAttrValue.length-1] == ',') {
                        layer.alert('属性值最后一位不能为,');
                        return;
                    }
                    var html = "<tr>";
                    html += "<td>" + customAttrName;
                    html += "<input type = 'hidden' value='-1' />";
                    html += "<input type = 'hidden' value='" + customAttrName + "' />";
                    html += "</td>";

                    html += "<td>";
                    var customAttrValue = $("#customAttrValue").val().split(/,|，/);
                    for (var i = 0; i < customAttrValue.length; i++) {
                        html += "<input type = 'checkbox' value='-1, "+ customAttrValue[i] + "' />"+customAttrValue[i];
                    }
                    html += "</td>";
                    html += "</tr>";
                    $("#tbd").append(html);
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            });
        }

    </script>
    <body>
        <form id="fm">
            <div class="layui-form-item">
                <label class="layui-form-label">商品名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="productName" placeholder="请输入商品名称" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">邮费</label>
                <div class="layui-input-inline">
                    <select name="freightId"  style="width: 190px; height: 38px">
                        <c:forEach items="${freightList}" var="freight">
                            <option value="${freight.freightId}">${freight.company}-<c:if test="${freight.freight == '0.00'}">包邮</c:if><c:if test="${freight.freight != '0.00'}">${freight.freight}</c:if></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">描述</label>
                <div class="layui-input-block">
                    <textarea name="productDescribe" placeholder="请输入内容"  style="height: 80px; width: 190px"></textarea>
                </div>
            </div>
            <div class="layui-upload">
                <label class="layui-form-label">图片</label>
                <button class="layui-btn layui-btn-radius layui-btn-normal" id="upImg" type="button">上传图片</button>
                <div class="layui-upload-list">
                    <img class="layui-upload-img" id="img">
                    <p id="imgText"></p>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">商品类型</label>
                <div class="layui-input-inline">
                    <select name="productType" id="productType" onchange="loadSkuGmRelatedAttr(this.value)" lay-filter="productType" style="width: 190px; height: 38px">
                        <c:forEach items="${productTypeList}" var="type">
                            <option value="${type.productType}">${type.productType}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">SKU</label>
                <div class="layui-input-inline">
                    <input type="button" value="+" onclick="addCustomSkuAttr()"  class="layui-btn layui-btn-radius layui-btn-normal">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" value="生成SKU" onclick="toPhoneLogin()" class="layui-btn layui-btn-radius layui-btn-danger">
                </div>
            </div>
            <table border="0px" class="layui-table" >
                <colgroup>
                    <col width="100">
                    <col width="100">
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th>属性名</th>
                    <th>属性值</th>
                </tr>
                </thead>
                <tbody id="tbd"></tbody>
            </table>

        </form>

    </body>
</html>
