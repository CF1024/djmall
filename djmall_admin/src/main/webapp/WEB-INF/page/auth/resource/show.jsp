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
  Date: 2020/4/5
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/ztree/zTreeStyle/zTreeStyle.css" type="text/css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/ztree/jquery.ztree.all.min.js"></script>
    </head>
    <script type="text/javascript">

        var setting = {
            view: {
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "resourceId",
                    pIdKey: "parentId"
                },
                key: {
                    name: "resourceName",
                    url: "xUrl"
                }
            }
        };

        //展示
        $(function(){
            show();
        });
        function show(){
            var index = layer.load(0, {shade:0.5});
            $.get("<%=request.getContextPath()%>/auth/resource/",
                function (data){
                    layer.close(index);
                    $.fn.zTree.init($("#resourceTree"), setting, data.data);
                })
        }

        //去新增
        function toAdd() {
            var zTree = $.fn.zTree.getZTreeObj("resourceTree"),
                nodes = zTree.getSelectedNodes(),
                treeNode = nodes[0],
                parentId = treeNode == null ? 0: treeNode.resourceId;
            layer.open({
                type: 2,
                title: '新增',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['500px', '62%'],
                content: '<%=request.getContextPath()%>/auth/resource/toAdd/'+parentId
            });
        }

        //去修改
        function toUpdate() {
            var zTree = $.fn.zTree.getZTreeObj("resourceTree"),
                nodes = zTree.getSelectedNodes(),
                treeNode = nodes[0];
            if (nodes.length == 0) {
                layer.msg("请先选择一个节点再进行修改");
                return;
            }
            layer.open({
                type: 2,
                title: '修改',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['500px', '62%'],
                content: '<%=request.getContextPath()%>/auth/resource/toUpdate/'+treeNode.resourceId
            });
        }

        //删除
        function deleteResource() {
            var zTree = $.fn.zTree.getZTreeObj("resourceTree"),
                nodes = zTree.getSelectedNodes(),
                treeNode = nodes[0];
            if (nodes.length == 0) {
                layer.msg("请先选择一个节点再进行删除");
                return;
            }
            layer.confirm('确认删除吗？', {icon: 3, title:'提示'}, function(index){
                $.post(
                    "<%=request.getContextPath()%>/auth/resource/deleteResource",
                    {"id":treeNode.resourceId},
                    function (data) {
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                window.location.href = "<%=request.getContextPath()%>/auth/resource/toShow";
                        });
                    }
                );
                layer.close(index);
            });
        }
    </script>
    <body>
        <form class="layui-form" id="fm">
            <div class="layui-btn-group">
                <shiro:hasPermission name="RESOURCE_ADD_BTN">
                    <input type="button" value="新增" onclick="toAdd()" class="layui-btn">
                </shiro:hasPermission>
                <shiro:hasPermission name="RESOURCE_UPDATE_BTN">
                    <input type="button" value="修改" onclick="toUpdate()" class="layui-btn">
                </shiro:hasPermission>
                <shiro:hasPermission name="RESOURCE_DELETE_BTN">
                    <input type="button" value="删除" onclick=" deleteResource()" class="layui-btn">
                </shiro:hasPermission>
            </div>
            <div id="resourceTree" class="ztree"></div>
        </form>
    </body>
</html>
