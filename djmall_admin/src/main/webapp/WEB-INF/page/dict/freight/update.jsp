<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：update.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/15
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
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
        //修改
        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/dict/freight/",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                parent.window.location.href = "<%=request.getContextPath()%>/dict/freight/toShow";
                            });
                    });
            }
        });


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
                    freight:{
                        required: true,    //要求输入不能为空
                        number: true,     //输入必须是数字
                        min: 0,          //输入的数字最小值为0.01，不能为0或者负数
                        minNumber: $("#freight").val()  //调用自定义验证
                    }
                },
                messages:{
                    freight:{
                        required: "请填写运费金额",
                        number: "请正确输入金额",
                        min: "输入最小金额为0元（包邮）",
                        length: "输入数字最多小数点后两位"
                    }
                }
            })
        });
    </script>
    <body>
        <form id="fm" class="layui-form" id="fm">
            <input type="hidden" name="_method" value="PUT"/>
            <input type="hidden" name="freightId" value="${freight.freightId}" id="freightId">
            <div class="layui-form-item">
                <label class="layui-form-label">运费</label>
                <div class="layui-input-inline">
                    <input type="text" name="freight" id="freight" value="${freight.freight}" placeholder="请输入运费" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <input type="submit" value="修改" class="layui-btn">
                </div>
            </div>
        </form>
    </body>
</html>