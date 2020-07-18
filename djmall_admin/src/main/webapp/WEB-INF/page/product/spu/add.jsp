<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：add.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

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
    <style>
        .error{
            color: red;
        }
        .layui-form-label{
            white-space:nowrap!important;
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
                    });
                },
            });
        });
        //联动加载
        $(function () {
            loadSkuGmRelatedAttr($("#productType").val());
        });
        //联动商品类型
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
                            html += "<input type='checkbox' lay-skin='primary' value='"+attrValueId+", "+attrValueList[j]+"'  /><span>"+ attrValueList[j]+"</span>&nbsp;&nbsp;";
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
                shade: 0.5,
                fixed: false,
                offset: '230px',
                anim: 1,
                area: ['400px', '200px'],
                content: content,
                btn: ['确认', '取消'],
                yes: function(index) {
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
                        html += "<input type = 'checkbox' value='-1, "+ customAttrValue[i] + "'/><span>"+customAttrValue[i]+"</span>&nbsp;&nbsp;";
                    }
                    html += "</td>";
                    html += "</tr>";
                    $("#tbd").append(html);
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }
            });
        }
        //笛卡尔积
        function cartesian (array) {
            var total = 1;
            for (var i = 0; i < array.length; i++) {
                total *= array[i].length;
            }
            var cartesianList = [];
            var itemLoopNum = 1;
            var loopPerItem = 1;
            var now = 1;
            for (var i = 0; i < array.length; i++) {
                now *= array[i].length;
                var index = 0;
                var currentSize = array[i].length;
                itemLoopNum = total / now;
                loopPerItem = total / (itemLoopNum * currentSize);
                var myIndex = 0;
                for (var j = 0; j < array[i].length; j++) {
                    for (var z = 0; z < loopPerItem; z++) {
                        if (myIndex == array[i].length) {
                            myIndex = 0;
                        }
                        for (var k = 0; k < itemLoopNum; k++) {
                            cartesianList[index] = (cartesianList[index] == null ? "" : cartesianList[index] + "-") + array[i][myIndex];
                            index++;
                        }
                        myIndex++
                    }
                }
            }
            return cartesianList;
        }
        //生成SKU
        function generateSku() {
            var skuId = [];            //属性ID集合
            var sku = [];              //属性集合
            var $tr = $("#tbd tr");             //获取全部属性tr
            for (var i = 0; i < $tr.length; i++) {
                var $td = $($tr[i]).find("td")[1];              //获取全部td
                var checkedValues = $($td).find(":checked");    //已选中
                if (checkedValues.length > 0 ) {
                    var attrValueIds = [];            //属性值ID集合
                    var attrValues = [];              //属性值集合
                    for (var j = 0; j < checkedValues.length; j++) {
                        attrValueIds.push(checkedValues[j].value.split(",")[0]);
                        attrValues.push(checkedValues[j].value.split(",")[1]);
                    }
                    skuId.push(attrValueIds);
                    sku.push(attrValues);
                }
            }
            //生成sku表
            var skuIdList = cartesian(skuId);
            var skuList = cartesian(sku);
            var html = "";
            for (var l = 0; l < skuList.length; l++) {
                html += "<tr>";
                html += "<td>"+ (l + 1) +"<input type = 'hidden' name = 'productSkuList["+ l +"].skuAttrIds' value = '"+ skuId +"'> <input type = 'hidden' name = 'productSkuList["+ l +"].skuAttrValueIds' value = '"+ skuIdList[l] +"'></td>";
                html += "<td>"+ skuList[l] +"<input type = 'hidden' name = 'productSkuList["+ l +"].skuAttrNames' value = '"+ sku +"'> <input type = 'hidden' name = 'productSkuList["+ l +"].skuAttrValueNames' value = '"+ skuList[l] +"'></td>";
                html += "<td><input type = 'text' name = 'productSkuList["+ l +"].skuCount' value = '10' class='layui-input'></td>";
                html += "<td><input type = 'text' name = 'productSkuList["+ l +"].skuPrice' value = '100' class='layui-input'></td>";
                html += "<td><input type = 'text' name = 'productSkuList["+ l +"].skuRate' value = '100' class='layui-input'></td>";
                html += "<td><button type='button' onclick='skuDel(this)' class='layui-btn layui-btn-danger layui-btn-xs'>移除</button></td>";
                html += "</tr>";
            }
            $("#skuTbd").html(html);
        }
        //移除
        function skuDel(obj) {
            $(obj).parent().parent().remove();
        }
        //validate
        $(function () {
            $("#fm").validate({
                errorPlacement: function(error, element) {
                    if (element.is("[name='file']")) {
                        error.appendTo($("#fileError"));   //错误信息增加在id为‘radio-lang’中
                    } else {
                        error.insertAfter(element);//其他的就显示在添加框后
                    }
                },
                rules: {
                    productName: {
                        required: true,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/product/spu/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
                    },
                    productDescribe:{
                        required:true
                    },
                    file:{
                        required:true
                    },
                },
                messages: {
                    productName: {
                        required: "请填写商品名称",
                        remote:"商品名称已存在"
                    },
                    productDescribe:{
                        required:"请填写描述"
                    },
                    file:{
                        required:"请上传商品图片"
                    },
                }
            });
        });

        //新增商品
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
            <div class="layui-form-item">
                <label class="layui-form-label">商品名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="productName" placeholder="请输入商品名称" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">邮费</label>
                <div class="layui-input-inline">
                    <select name="freightId" style="width: 190px; height: 38px">
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
            <label class="layui-form-label">图片</label>
            <div class="layui-upload">
                <div class="divBtn">
                <i class="layui-icon">&#xe67c;</i>上传图片
                    <input id="file" type="file" name="file"/>
                </div><span id="fileError"></span>
                <div class="layui-upload-list">
                    <div class="divImg">
                        <label class="layui-form-label"></label>
                        <img src="" id="imgShow" height="190px" width="190px">
                    </div>
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
                    <input type="button" value="生成SKU" onclick="generateSku()" class="layui-btn layui-btn-radius layui-btn-danger">
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
            <label class="layui-form-label" style="color: #1E9FFF; font-size: 20px; font-family: 楷体">生成后的SKU</label>
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
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="skuTbd"></tbody>
            </table>
            <input type = "submit" value = "新增" id="btn" class="layui-btn layui-btn-radius layui-btn-normal" />
        </form>
    </body>
</html>
