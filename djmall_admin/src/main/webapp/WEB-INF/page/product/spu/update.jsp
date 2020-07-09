<%--
  ~ 作者：CF
  ~ 日期：2020-07-07 10:01
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：update.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/7
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>商品修改</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style>
        .error{
            color: red;
        }
        .layui-form-label{
            white-space:nowrap!important;
        }
        #login{
            position:absolute;
            left: 70%;
            top: 70%;
            margin-left:-200px;
            margin-top:-140px;
            border:1px;
            background-color:transparent;
            align:center;
        }
        #form{
            position:relative;
            left:50%;
            top:50%;
            margin-left:-150px;
            margin-top:-80px;
        }
        .divBtn{
            position:relative;
            display:inline-block;
            height:38px;
            line-height:38px;
            padding:0 60px;
            background-color:#1E9FFF;
            color:#fff;
            white-space:nowrap;
            text-align:center;
            font-size:14px;
            border:none;
            border-radius:100px;
            cursor:pointer
        }
        .divBtn input{
            position:absolute;
            font-size:10px;
            height: 40px;
            right:2px;
            top:0;
            opacity:0;
        }
    </style>
    <script type="text/javascript">
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use(['form', 'table', 'upload'], function () {
            var form = layui.form;
            var upload = layui.upload;
            form.render();//重点在这里
            form.on('select(productType)', function(){
                loadSkuGmRelatedAttr($("#productType").val());
            });
            //预览图片
            $(function(){
                $(".divImg").hide();
            });
            //选完文件后不自动上传
            upload.render({
                elem: '#file',
                auto: false,
                accept: 'images', //只允许上传图片
                acceptMime: 'image/gif,image/jpeg,image/jpg,image/png,image/svg', //只筛选图片
                choose: function(obj){
                    //将每次选择的文件追加到文件队列
                    var files = obj.pushFile();
                    //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                    obj.preview(function(index, file, result){
                        console.log(index); //得到文件索引
                        console.log(file); //得到文件对象
                        console.log(result); //得到文件base64编码，比如图片
                        $('#imgShow').attr('src', result); //图片链接（base64）
                        if(obj != null){
                            $(".divImg").show();
                        }else{
                            $(".divImg").hide();
                        }
                        $(".divProductImg").hide();
                    });
                },
            });
        });
        $(function () {
            show();
        });

        function show() {
            var index = layer.load(0, {shade:0.5});
            $.get(
                "<%=request.getContextPath()%>/product/sku/",
                {"productId": ${product.productId}},
                function (data) {
                    layer.close(index);
                    if (data.code !== 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        var sku = data.data[i];
                        html += "<tr>";
                        html += "<td><input type = 'checkbox' name = 'ids' value = '"+sku.skuId+","+sku.skuStatus+","+sku.isDefault+"'>"+sku.skuId+"</td>";
                        html += "<td width='400px'>" + sku.skuAttrValueNames +"</td>";
                        html += "<td>" + sku.skuCount +"</td>";
                        html += "<td>" + sku.skuPrice +"</td>";
                        html += "<td>" + sku.skuRate +"</td>";
                        html += sku.isDefault === "HAVE_DEFAULT" ? "<td>是</td>" : "<td>否</td>";
                        html += sku.skuStatus === "PRODUCT_STATUS_UP" ?
                            "<shiro:hasPermission name='PRODUCT_SKU_UPDATE_STATUS'><td><input type = 'button' value = '下架' onclick='updateStatus("+sku.skuId+",\""+sku.isDefault+"\")' class='layui-btn layui-btn-radius layui-btn-danger'></td></shiro:hasPermission>" :
                            "<shiro:hasPermission name='PRODUCT_SKU_UPDATE_STATUS'><td><input type = 'button' value = '上架' onclick='updateStatus("+sku.skuId+",\""+sku.isDefault+"\")' class='layui-btn layui-btn-radius layui-btn-normal'></td></shiro:hasPermission>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                }
            );
        }
        function updateStatus(skuId, isDefault) {
            if (isDefault === "HAVE_DEFAULT") {
                layer.msg("该商品是默认商品，请换个商品进行下架", {offset: '230px', icon: 6});
                return;
            }
            layer.confirm("确定进行上下架操作吗？", {offset: '230px', icon: 3, title:'提示'}, function(index){
                $.post(
                    "<%=request.getContextPath()%>/product/sku/updateStatus",
                    {"skuId":skuId},
                    function (data) {
                        if(data.code !== 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                window.location.href = "<%=request.getContextPath()%>/product/spu/toUpdateProduct/"+${product.productId};
                            });
                    }
                );
                layer.close(index);
            });
        }
        //获取id
        function getIds() {
            var ids=[];
            $(":checkbox[name='ids']:checked").each(function () {
                ids.push($(this).val());
            });
            return ids;
        }

        //去修改库存
        function toUpdateCount() {
            var ids = getIds();
            if (ids.length < 1){
                layer.msg("请选择一个商品SKU进行修改库存", {offset: '230px',icon:6});
                return;
            }
            if (ids.length > 1) {
                layer.msg("只能选择一个商品SKU进行修改库存！", {offset: '230px',icon:6});
                return;
            }
            var id = $("input[name='ids']:checked").val().split(",")[0];
            //iframe层
            layer.open({
                type: 2,
                title: '修改库存',
                shadeClose: true,
                shade: 0.8,
                fixed: false,
                offset: '230px',
                anim: 1,
                area: ['500px', '200px'],
                content: '<%=request.getContextPath()%>/product/sku/toUpdateCount/'+id
            });
        }

        //去编辑
        function toEdit() {
            var ids = getIds();
            if (ids.length < 1){
                layer.msg("请选择一个商品SKU进行编辑", {offset: '230px',icon:6});
                return;
            }
            if (ids.length > 1) {
                layer.msg("只能选择一个商品SKU进行编辑！", {offset: '230px',icon:6});
                return;
            }
            var id = $("input[name='ids']:checked").val().split(",")[0];
            //iframe层
            layer.open({
                type: 2,
                title: '编辑',
                shadeClose: true,
                shade: 0.8,
                fixed: false,
                offset: '200px',
                anim: 1,
                area: ['500px', '400px'],
                content: '<%=request.getContextPath()%>/product/sku/toEdit/'+id
            });
        }

        //设为默认
        function setAsDefault() {
            var ids = getIds();
            if (ids.length !== 1){
                layer.msg("请选择一条数据", {offset: '230px', icon: 6});
                return;
            }
            var skuObj = $("input[name='ids']:checked").val().split(",");
            var id = skuObj[0];
            var skuStatus = skuObj[1];
            var isDefault = skuObj[2];
            if (skuStatus !== "PRODUCT_STATUS_UP") {
                layer.msg("下架商品不能设为默认", {offset: '230px', icon: 6});
                return;
            }
            if (isDefault === "HAVE_DEFAULT") {
                layer.msg("该商品本就是默认，请换个商品设为默认吧", {offset: '230px', icon: 6});
                return;
            }
            layer.confirm("确定设为默认吗？", {offset: '230px', icon: 3, title:'提示'}, function(index){
                $.post(
                    "<%=request.getContextPath()%>/product/sku/",
                    {"skuId":id, "productId":${product.productId}},
                    function (data) {
                        if(data.code !== 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                window.location.href = "<%=request.getContextPath()%>/product/spu/toUpdateProduct/"+${product.productId};
                            });
                    }
                );
                layer.close(index);
            });
        }

        //validate
        $(function () {
            $("#fm").validate({
                rules: {
                    productName: {
                        required: true,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/product/spu/deDuplicate",
                            data:{
                                productId:function() {
                                    return $("#productId").val();
                                },
                                dataType:"json"
                            }
                        }
                    },
                    productDescribe:{
                        required:true
                    }
                },
                messages: {
                    productName: {
                        required: "请填写商品名称",
                        remote:"商品名称已存在"
                    },
                    productDescribe:{
                        required:"请填写描述"
                    }
                }
            });
        });
        //修改商品
        $.validator.setDefaults({
            submitHandler: function () {
                var formData = new FormData($("#fm")[0]);
                var index = layer.load(0, {shade:0.5});
                //七牛雲上传图片只能ajax提交
                $.ajax({
                    url:'<%= request.getContextPath() %>/product/spu/',
                    dataType:'json',
                    type:'post',
                    data: formData,
                    processData : false, // 使数据不做处理
                    contentType : false, // 不要设置Content-Type请求头信息
                    success: function (data) {
                        layer.close(index);
                        if(data.code !== 200){
                            layer.msg(data.msg, {offset: '230px', icon:5, time:5000});
                            return;
                        }
                        layer.msg(data.msg, {offset: '230px', icon: 6, time: 2000},
                            function(){
                                window.location.href = "<%=request.getContextPath()%>/product/spu/toShow";
                            });
                    }
                });
            }
        });
    </script>
    <body>
        <form id="fm">
            <input type="hidden" name="_method" value="PUT"/>
            <input type = "hidden" name = "productId" value="${product.productId}" id="productId" />
            <div id="login">
                <div id="form">
                    <input type = "submit" value = "修改" id="btn" class="layui-btn layui-btn-lg layui-btn-radius layui-btn-normal" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">商品类型</label>
                <div class="layui-form-mid">${product.productType}</div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">商品名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="productName" value="${product.productName}" placeholder="请输入商品名称"  class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">邮费</label>
                <div class="layui-input-inline">
                    <select name="freightId"  style="width: 190px; height: 38px">
                        <c:forEach items="${freightList}" var="freight">
                            <option value="${freight.freightId}" <c:if test="${product.freightId == freight.freightId}">selected</c:if>>
                                    ${freight.company}-
                                        <c:if test="${freight.freight == '0.00'}">包邮</c:if>
                                        <c:if test="${freight.freight != '0.00'}">${freight.freight}</c:if>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">描述</label>
                <div class="layui-input-block">
                    <textarea name="productDescribe" placeholder="请输入内容"  style="height: 80px; width: 190px">${product.productDescribe}</textarea>
                </div>
            </div>
            <label class="layui-form-label">图片</label>
            <div class="layui-upload">
                <div class="layui-upload-list">
                    <div class="divProductImg">
                        <img src="http://qcxz8bvc2.bkt.clouddn.com/${product.productImg}" height="190px" width="190px">
                        <input type="hidden" name="removeImg" value="${product.productImg}">
                    </div>
                </div>
                <div class="layui-upload-list">
                    <div class="divImg">
                        <img src="" id="imgShow" height="190px" width="190px">
                    </div>
                </div>
                <label class="layui-form-label"></label>
                <div class="divBtn">
                    <i class="layui-icon">&#xe67c;</i>上传图片
                    <input id="file" type="file" name="file"/>
                </div><span id="fileError"></span>
            </div><br/>

            <shiro:hasPermission name="PRODUCT_SKU_SHOW_BTN">
                <label class="layui-form-label" style="color: #1E9FFF; font-size: 20px; font-family: 楷体">SKU列表</label>
                <shiro:hasPermission name="PRODUCT_SKU_UPDATE_COUNT_BTN">
                    <input type="button" value="修改库存" onclick="toUpdateCount()"  class="layui-btn layui-btn-radius">
                </shiro:hasPermission>
                <shiro:hasPermission name="PRODUCT_SKU_EDIT_BTN">
                    <input type="button" value="编辑" onclick="toEdit()" class="layui-btn layui-btn-radius layui-btn-normal">
                </shiro:hasPermission>
                <shiro:hasPermission name="PRODUCT_SKU_SET_AS_DEFAULT">
                    <input type="button" value="设为默认" onclick="setAsDefault()" class="layui-btn layui-btn-radius layui-btn-warm">
                </shiro:hasPermission>
                <table border="0px" class="layui-table" >
                    <colgroup>
                        <col width="100">
                        <col width="100">
                        <col>
                    </colgroup>
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>SKU属性</th>
                        <th>库存</th>
                        <th>价格(元)</th>
                        <th>折扣(%)</th>
                        <th>是否默认</th>
                        <shiro:hasPermission name="PRODUCT_SKU_UPDATE_STATUS">
                        <th>操作</th>
                        </shiro:hasPermission>
                    </tr>
                    </thead>
                    <tbody id="tbd"></tbody>
                </table>
            </shiro:hasPermission>
        </form>
    </body>
</html>
