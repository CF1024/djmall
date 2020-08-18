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
                "<%=request.getContextPath()%>/dict/attr/",
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
                        var initialValue = 1;
                        var coding = initialValue + i;
                        html += "<tr>";
                        html += "<td>" + coding +"</td>";
                        html += "<td>" + attr.attrName +"</td>";
                        html += attr.attrValue == null ? "<td>暂无属性值</td>":"<td>" + attr.attrValue +"</td>";
                        html += "<shiro:hasPermission name='RELATED_ATTR_VALUE_BTN'><td><a class='layui-btn layui-btn-normal layui-btn-xs' href='javascript:toRelatedAttrValue("+attr.attrId+")'>关联属性值</a></td></shiro:hasPermission>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                }
            );
        }


        //去关联属性值
        function toRelatedAttrValue(attrId) {
            window.location.href = "<%=request.getContextPath()%>/dict/attr/toRelatedAttrValue/"+attrId;
        }

        //去新增
        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/dict/attr/",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                window.location.href = "<%=request.getContextPath()%>/dict/attr/toShow";
                            });
                    });
            }
        });

        $(function(){
            $("#fm").validate({
                rules:{
                    attrName:{
                        required: true,    //要求输入不能为空
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/dict/attr/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
                    }
                },
                messages:{
                    attrName:{
                        required: "请填写属性名",
                        remote:"属性名已存在"
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
                <label class="layui-form-label">属性名</label>
                <div class="layui-input-inline">
                    <input type="text" name="attrName" id="attrName" placeholder="请输入属性名" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <shiro:hasPermission name="ATTR_ADD_BTN">
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
                <th>编号</th>
                <th>属性名</th>
                <th>属性值</th>
                <shiro:hasPermission name="RELATED_ATTR_VALUE_BTN">
                <th>操作</th>
                </shiro:hasPermission>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
    </body>
</html>
