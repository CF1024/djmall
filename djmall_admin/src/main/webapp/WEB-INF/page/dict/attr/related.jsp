<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：related.jsp
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
                "<%=request.getContextPath()%>/dict/attrValue/",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var attrValue = data.data.list[i];
                        html += "<tr>";
                        html += "<td>" + attrValue.attrValueId +"</td>";
                        html += "<td>" + attrValue.attrValue +"</td>";
                        html += "<shiro:hasPermission name='ATTR_VALUE_DELETE_BTN'><td><a class='layui-btn layui-btn-danger layui-btn-xs' href='javascript:deleteAttrValue("+attrValue.attrValueId+")'>移除</a></td></shiro:hasPermission>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                    var pageHtml = "";
                    var pageNo = parseInt($("#pageNo").val());
                    pageHtml += "<input type='button' value='上一页' onclick='page("+ data.data.pages + "," + (pageNo - 1)+")' class='layui-btn layui-btn-normal'/>";
                    pageHtml += "<input type='button' value='下一页' onclick='page("+ data.data.pages + "," + (pageNo + 1)+")' class='layui-btn layui-btn-normal'/>";
                    $("#pageInfo").html(pageHtml);
                }
            );
        }

        //分页
        function page(pages, pageNo) {
            if (pageNo < 1) {
                layer.msg("已是第一页");
                return;
            }
            if (pageNo > pages) {
                layer.msg("已是最后一页");
                return;
            }
            $("#pageNo").val(pageNo);
            show();
        }

        //移除
        function deleteAttrValue(attrValueId) {
            layer.confirm('确认删除吗？', {icon: 3, title:'提示'}, function(index){
                $.post(
                    "<%=request.getContextPath()%>/dict/attrValue/deleteAttrValue",
                    {"attrValueId":attrValueId},
                    function (data) {
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                               show();
                            });
                    }
                );
                layer.close(index);
            });
        }

        //去新增
        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/dict/attrValue/",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                show();
                            });
                    });
            }
        });

        $(function(){
            $("#fm").validate({
                rules:{
                    attrValue:{
                        required: true,    //要求输入不能为空
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/dict/attrValue/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
                    }
                },
                messages:{
                    attrValue:{
                        required: "请填写属性值",
                        remote:"属性值已存在"
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
            <input type="hidden" value="1" name="pageNo" id="pageNo">
            <input type="hidden" name="attrId" value="${attr.attrId}" id="attrId">
            <div class="layui-form-item">
                <label class="layui-form-label">属性名</label>
                <div class="layui-form-mid " style="color: red">
                    ${attr.attrName}
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">属性值</label>
                <div class="layui-input-inline">
                    <input type="text" name="attrValue" id="attrValue" placeholder="请输入属性值" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <shiro:hasPermission name="ATTR_VALUE_ADD_BTN">
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
                <th>属性值</th>
                <shiro:hasPermission name="ATTR_VALUE_DELETE_BTN">
                <th>操作</th>
                </shiro:hasPermission>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
        <div id="pageInfo"></div>
    </body>
</html>
