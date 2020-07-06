<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：related_resource.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/5
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <script>
        var setting = {
            check:{
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "parentId"
                }
            }
        };
        //已关联资源展示
        $(function(){
            getRelatedResource();
        });
        function getRelatedResource(){
            var index = layer.load(0, {shade:0.5});
            $.get("<%=request.getContextPath()%>/auth/role/getRelatedResource/"+${roleId},
                function (data){
                    layer.close(index);
                    if (data.code == 200) {
                        $.fn.zTree.init($("#resourceTree"), setting, data.data);
                    }
                })
        }
        function saveRelevance() {
            var index = layer.load(0,{shade:0.5});
            //获取树对象
            var zTree = $.fn.zTree.getZTreeObj("resourceTree");
            var checkedNodes = zTree.getCheckedNodes();
            var resourceIds = "";
            for (var i = 0; i < checkedNodes.length; i++) {
                resourceIds += checkedNodes[i].id + ",";
            }
            var resourceIdList = resourceIds.substring(0, resourceIds.length - 1);
            $.post("<%=request.getContextPath()%>/auth/role/saveRelevance",
                {"roleId" : ${roleId}, "resourceIdList" : resourceIdList},
                function (data) {
                    layer.close(index);
                    if(data.code != 200) {
                        layer.msg(data.msg, {icon:5,time:2000});
                        return;
                    }
                    layer.msg(data.msg, {icon: 6, time: 2000});
            })
        }
    </script>
    <body>
        <button type="button" onclick="saveRelevance()" class="layui-btn layui-btn layui-btn-normal layui-btn-sm">保存</button>
        <div id="resourceTree" class="ztree"></div>
    </body>
</html>
