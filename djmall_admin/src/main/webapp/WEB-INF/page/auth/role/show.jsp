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
  Date: 2020/6/4
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>角色展示</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <script type="text/javascript">
        $(function () {
            show();
        })

        function show() {
            var index = layer.load(0, {shade:0.5});
            $.post(
                "<%=request.getContextPath()%>/auth/role/show",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var role = data.data.list[i];
                        html += "<tr>";
                        html += "<td>" + role.roleId +"</td>";
                        html += "<td>" + role.roleName +"</td>";
                        html += "<td>";
                        html += "<shiro:hasPermission name='ROLE_RELATED_RESOURCE_BTN'><a class='layui-btn layui-btn-normal layui-btn-xs' href='javascript:toRelatedResource("+role.roleId+")'>关联资源</a></shiro:hasPermission>";
                        html += "<shiro:hasPermission name='ROLE_UPDATE_BTN'><a class='layui-btn layui-btn-xs' href='javascript:toUpdate("+role.roleId+")'>编辑</a></shiro:hasPermission>";
                        html += "<shiro:hasPermission name='ROLE_DELETE_BTN'><a class='layui-btn layui-btn-danger layui-btn-xs' href='javascript:remove("+role.roleId+")'>删除</a></shiro:hasPermission>";
                        html += "</td>";
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

        //去关联资源
        function toRelatedResource(roleId) {
            //iframe层
            layer.open({
                type: 2,
                title: '关联资源',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['400px', '60%'],
                content: '<%=request.getContextPath()%>/auth/role/toRelatedResource/'+roleId
            });
        }

        //去修改
        function toUpdate(roleId) {
            //iframe层
            layer.open({
                type: 2,
                title: '修改',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['500px', '40%'],
                content: '<%=request.getContextPath()%>/auth/role/toUpdate/'+roleId
            });
        }

        //删除
        function remove(roleId) {
            layer.confirm('确认删除？', {icon: 3, title:'提示'}, function(index){
                $.post(
                    "<%=request.getContextPath()%>/auth/role/remove",
                    {"roleId":roleId},
                    function (data) {
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                window.location.href = "<%=request.getContextPath()%>/auth/role/toShow";
                        });
                    }
                );
                layer.close(index);
            });
        }

        //去新增
        function toAdd() {
            layer.open({
                type: 2,
                title: '修改',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['500px', '40%'],
                content: '<%=request.getContextPath()%>/auth/role/toAdd'
            });
        }

    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" value="1" name="pageNo" id="pageNo">
        </form>
        <shiro:hasPermission name="ROLE_ADD_BTN">
            <input type="button" value="新增" onclick="toAdd()" class="layui-btn layui-btn-normal">
        </shiro:hasPermission>
        <table border="0px" class="layui-table" >
            <colgroup>
                <col width="100">
                <col width="100">
                <col width="500">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>编号</th>
                <th>角色名</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
        <div id="pageInfo"></div>
    </body>
</html>
