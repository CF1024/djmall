<%--
  ~ 作者：CF
  ~ 日期：2020-07-27 17:51
  ~ 项目：djmall
  ~ 模块：djmall_platform
  ~ 类名：add.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/27
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>填写收货地址</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style>
        .error{
            color: red;
        }
        .form-control{height: 40px;border:1px solid #dddddd}
    </style>
    <script type="text/javascript">
        layui.use('form', function(){
            var form = layui.form;
            form.render(); //更新全部
        });

        //表单验证
        jQuery.validator.addMethod("phone", function(value, element) {
            return /^1[3456789][0-9]{9}$/.test(value);
        }, "手机号码格式错误!");

        jQuery.validator.addMethod("validAddr", function(value, element, param) {
            return value != -1;
        }, $.validator.format("请输入有效地址"));

        $(function(){
            $("#fm").validate({
                rules:{
                    receiverName:{
                        required:true
                    },
                    phone:{
                        required:true,
                        phone:true
                    },
                    province:{
                        required:true,
                        validAddr:true
                    },
                    city:{
                        required:true,
                        validAddr:true
                    },
                    county:{
                        required:true,
                        validAddr:true
                    },
                    detailedAddress:{
                        required:true
                    }
                },
                messages:{
                    receiverName:{
                        required:"请输入收货人姓名~"
                    },
                    phone:{
                        required:"请输入收货人手机号码~",
                        phone:"请输入有效手机号码~"
                    },
                    province:{
                        required:"请选择省",
                        validAddr:"请选择省"
                    },
                    city:{
                        required:"请选择市",
                        validAddr:"请选择市"
                    },
                    county:{
                        required:"请选择县区",
                        validAddr:"请选择县区"
                    },
                    detailedAddress:{
                        required:"请输入收货人详细地址~"
                    }
                }
            })
        })

        //新增收货地址
        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0, {shade:0.5});
                token_post(
                    "<%=request.getContextPath()%>/user/address/add?TOKEN="+getToken(),
                    $("#fm").serialize(),
                    function(data) {
                        layer.close(index);
                        if(data.code !== 200){
                            layer.msg(data.msg, {offset: '230px', icon:5, time:5000});
                            return;
                        }
                        layer.msg(data.msg, {offset: '230px', icon: 6, time: 2000},
                            function(){
                                window.location.href = "<%=request.getContextPath()%>/user/address/toShowAddress?TOKEN="+getToken();
                            }
                        );
                    }
                );
            }
        });

        //三级联动
        $(function () {
            getSon(1, $("#province"));
        })

        function getCity(val) {
            getSon(val, $("#city"));
        }

        function getCounty(val) {
            getSon(val, $("#county"));
        }

        function getSon(val, areaObj) {
            var index = layer.load(0, {shade:0.5});
            token_get(
                "<%=request.getContextPath()%>/user/address/getAreaByParentId?parentId="+val,
                function (data) {
                    layer.close(index);
                    if(data.code !== 200){
                        layer.msg(data.msg, {offset: '230px', icon:5, time:5000});
                        return;
                    }
                    var html = "<option value='-1'>请选择</option>";
                    for (let i = 0; i < data.data.length; i++) {
                        var area = data.data[i];
                        html += "<option value='"+area.id+"'>" + area.areaName + "</option>";
                    }
                    $(areaObj).html(html);
                }
            );
        }
        function toShowAddress() {
            window.location.href = "<%=request.getContextPath()%>/user/address/toShowAddress?TOKEN="+getToken();
        }

    </script>
    <body>
        <form id="fm">
            <div class="layui-form-item">
                <label class="layui-form-label">收货人</label>
                <div class="layui-input-inline">
                    <input type="text" name="receiverName" id="receiverName" placeholder="请输入收货人" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-inline">
                    <input type="text" name="phone" id="phone" placeholder="请输入手机号" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">省/市/县</label>
                <select name="province" onchange="getCity(this.value)" id="province" class="form-control">
                    <option value="-1">请选择</option>
                </select>
                <select name="city" onchange="getCounty(this.value)" id="city" class="form-control">
                    <option value="-1">请选择</option>
                </select>
                <select name="county"  id="county" class="form-control">
                    <option value="-1">请选择</option>
                </select>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">详细位置</label>
                <div class="layui-input-inline">
                    <input type="text" name="detailedAddress" id="detailedAddress" placeholder="请输入详细位置" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <label class="layui-form-label"></label>
                    <input type="submit" value="确定" class="layui-btn layui-btn-normal">
                </div>
                <div class="layui-form-mid layui-word-aux">
                    <a href="javascript:toShowAddress()" class="layui-blue">取消</a>
                </div>
            </div>
        </form>
    </body>
</html>