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
        //validate
        //自定义validate验证输入的数字小数点位数不能大于两位
        jQuery.validator.addMethod("minNumber",function(value, element){
            var returnVal = true;
            inputZ=value;
            var ArrMen= inputZ.split(".");    //截取字符串
            if(ArrMen.length==2){
                if(ArrMen[1].length>2){    //判断小数点后面的字符串长度
                    returnVal = false;
                    return false;
                }
            }
            return returnVal;
        },"小数点后最多为两位");         //验证错误信息
        $(function(){
            $("#fm").validate({
                rules:{
                    skuCount:{
                        required:true,              //必输字段
                        digits:true,                //必须输入整数
                        min:1                       //输入值不能小于1
                    },
                    skuPrice:{
                        required: true,    //要求输入不能为空
                        number: true,     //输入必须是数字
                        min: 0.01,          //输入的数字最小值为0.01，不能为0或者负数
                        minNumber: $("#skuPrice").val()  //调用自定义验证
                    },
                    skuRate:{
                        required:true,              //必输字段
                        digits:true,                //必须输入整数
                        max:99,
                    }
                },
                messages:{
                    skuCount:{
                        required:"请输入库存",                                 //必输字段
                        digits:"库存请输入整数",                               //必须输入整数
                        min:"库存最少为一个"                   //输入值不能小于1
                    },
                    skuPrice:{
                        required: "请填写价格",
                        number: "请正确输入金额",
                        min: "输入最小金额为0.01元",
                        length: "输入数字最多小数点后两位"
                    },
                    skuRate:{
                        required:"请输入折扣",                   //必输字段
                        digits:"折扣请输入整数",                            //必须输入整数
                        max:"最大折扣为99",
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
                <label class="layui-form-label">SKU属性</label>
                <div class="layui-form-mid">${sku.skuAttrValueNames}</div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">库存</label>
                <div class="layui-input-inline">
                    <input type="text" name="skuCount" placeholder="请输入库存" value="${sku.skuCount}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">价格</label>
                <div class="layui-input-inline">
                    <input type="text" name="skuPrice" placeholder="请输入价格" id="skuPrice" value="${sku.skuPrice}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">折扣</label>
                <div class="layui-input-inline">
                    <input type="text" name="skuRate" placeholder="请输入折扣" value="${sku.skuRate}" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <label class="layui-form-label"></label>
                    <input type="submit" value="保存" class="layui-btn layui-btn-normal">
                </div>
            </div>
        </form>
    </body>
</html>
