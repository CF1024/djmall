<%--
  ~ 作者：CF
  ~ 日期：2020-07-19 18:49
  ~ 项目：djmall
  ~ 模块：djmall_platform
  ~ 类名：show.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/19
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>收货地址</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
    </head>
    <script type="text/javascript">
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use(['form', 'table'], function () {
            var form = layui.form;
            form.render();//重点在这里
        });

        $(function () {
            show();
        });

        function show() {
            var index = layer.load(0, {offset: '230px', shade:0.5});
            token_get(
                "<%=request.getContextPath()%>/user/address/show",
                function (data) {
                    layer.close(index);
                    if (data.code !== 200) {
                        layer.alert(data.msg,{offset: '230px'});
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        var address = data.data[i];
                        html += "<tr>";
                        html += "<td>" + address.id +"</td>";
                        html += "<td>" + address.receiverName +"</td>";
                        html += "<td>" + address.detailedAddress +"</td>";
                        html += "<td>" + address.phone +"</td>";
                        html += "<td>";
                        html += "<a class='layui-btn layui-btn-xs' href='javascript:toUpdate("+address.id+")'>修改</a>";
                        html += "<a class='layui-btn layui-btn-danger layui-btn-xs' href='javascript:remove("+address.id+")'>删除</a>";
                        html += "</td>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                }
            );
        }

        //新增收货地址
        function toNewShippingAddress() {
            window.location.href = "<%=request.getContextPath()%>/user/address/toNewShippingAddress?TOKEN="+getToken();
        }

        //去修改
        function toUpdate(id) {
            //iframe层
            layer.open({
                type: 2,
                title: '修改',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['800px', '50%'],
                content: '<%=request.getContextPath()%>/user/address/toUpdate?TOKEN='+getToken()+"&id="+id
            });
        }

        //删除
        function remove(id) {
            layer.confirm('确认删除？', {icon: 3, title:'提示'}, function(index){
                token_post(
                    "<%=request.getContextPath()%>/user/address/remove",
                    {"id":id, "_method":"PUT"},
                    function (data) {
                        if(data.code !== 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                window.location.href = "<%=request.getContextPath()%>/user/address/toShowAddress?TOKEN="+getToken();
                            });
                    }
                );
                layer.close(index);
            });
        }
    </script>
    <body>
        <form class="layui-form" id="fm">
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <label class="layui-form-label"></label>
                    <input type="button" value="新增收货地址" onclick="toNewShippingAddress()" class="layui-btn layui-btn-normal">
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
                <th>收货人</th>
                <th>详细地址</th>
                <th>手机号</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
    </body>
</html>
