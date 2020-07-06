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
  Date: 2020/6/15
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style type="text/css">
        .error{
            color: red;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            show();
        });

        function show() {
            var index = layer.load(0, {shade:0.5});
            $.get(
                "<%=request.getContextPath()%>/dict/freight/",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        var freight = data.data[i];
                        html += "<tr>";
                        html += "<td>" + freight.company +"</td>";
                        html += freight.freight == 0 ? "<td>包邮</td>" : "<td>" + freight.freight +"元</td>";
                        html += "<shiro:hasPermission name='FREIGHT_UPDATE_BTN'><td><a class='layui-btn layui-btn-xs' href='javascript:toUpdate("+freight.freightId+")'>修改</a></td></shiro:hasPermission>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                }
            );
        }


        //去修改
        function toUpdate(freightId) {
            //iframe层
            layer.open({
                type: 2,
                title: '修改',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['500px', '40%'],
                content: '<%=request.getContextPath()%>/dict/freight/'+freightId
            });
        }

        //去新增
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
                                window.location.href = "<%=request.getContextPath()%>/dict/freight/toShow";
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

        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });
    </script>
    <body>
        <form class="layui-form" id="fm">
            <div class="layui-form-item">
                <label class="layui-form-label">物流公司</label>
                <div class="layui-input-inline">
                    <select name="company">
                        <c:forEach items="${freightList}" var="freight">
                            <option value="${freight.baseCode}">${freight.baseName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">运费</label>
                <div class="layui-input-inline">
                    <input type="text" name="freight" id="freight" placeholder="请输入运费金额" class="layui-input">
                </div>
                <div class="layui-form-mid" style="color:red;">包邮为：0</div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <shiro:hasPermission name="FREIGHT_ADD_BTN">
                        <input type="submit" value="新增" class="layui-btn layui-btn-normal">
                    </shiro:hasPermission>
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
                <th>物流公司</th>
                <th>运费</th>
                <shiro:hasPermission name="FREIGHT_UPDATE_BTN">
                <th>操作</th>
                </shiro:hasPermission>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
    </body>
</html>
