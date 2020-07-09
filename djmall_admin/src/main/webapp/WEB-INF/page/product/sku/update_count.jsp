<%--
  ~ 作者：CF
  ~ 日期：2020-07-07 15:01
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：update_count.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/7
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>修改库存</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
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
            form.render();//重点在这里
        });

        //加库存
        function add() {
            var skuCount = $("#skuCount");
            skuCount.val(parseInt(skuCount.val()) + 1);
        }
        //减库存
        function subtract() {
            var skuCount = $("#skuCount");
            if (skuCount.val() < 2) {
                layer.msg("不能再减啦", {icon: 5});
                return;
            }
            skuCount.val(parseInt(skuCount.val()) - 1);
        }

        $(function(){
            $("#fm").validate({
                errorPlacement: function(error, element) {
                    if(element.is("[name='skuCount']")){//如果需要验证的元素名为level
                        error.appendTo($("#skuCountError"));   //错误信息增加在id为‘radio-lang’中
                    }  else {
                        error.insertAfter(element);//其他的就显示在添加框后
                    }
                },
                rules:{
                    skuCount:{
                        required: true,    //要求输入不能为空
                        number: true,     //输入必须是数字
                        min: 1            //输入的数字最小值为0.01，不能为0或者负数
                    }
                },
                messages:{
                    skuCount:{
                        required: "请填写库存数量",
                        number: "库存必须是整数哟~",
                        min: "库存不能少于一个哟~"
                    }
                }
            })
        });

        //修改
        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/product/sku/",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                parent.window.location.href = "<%=request.getContextPath()%>/product/spu/toUpdateProduct/"+${sku.productId};
                            });
                    });
            }
        });
    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" name="_method" value="PUT"/>
            <input type="hidden" name="skuId" value="${sku.skuId}" id="skuId">
            <div class="layui-form-item">
                <label class="layui-form-label">库存</label>
                <input type="button" value="-"  onclick="subtract()" class="layui-btn layui-btn-radius layui-btn-normal">
                <input type="text" name="skuCount" id="skuCount" value="${sku.skuCount}" placeholder="请输入库存"  style="width: 190px; height: 38px">
                <input type="button" value="+"  onclick="add()" class="layui-btn layui-btn-radius layui-btn-normal">
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"></label>
                <label class="layui-form-label"></label>
                <div class="layui-input-inline">
                    <input type="submit" value="确定" class="layui-btn layui-btn-normal">
                </div>
            </div>
            <label class="layui-form-label"></label><span id="skuCountError"></span>
        </form>
    </body>
</html>
