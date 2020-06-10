<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/3
  Time: 22:09
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
            },
            callback: {
                onClick: function (event, treeId, treeNote) {
                    if (!treeNote.isParent) {
                        parent.right.location.href = "<%=request.getContextPath()%>" + treeNote.url;
                    }
                }
            }
        };

        //展示
        $(function(){
            show();
        });
        function show(){
            var index = layer.load(0, {shade:0.5});
            $.get("<%=request.getContextPath()%>/auth/user/showMenu",
                function (data){
                    layer.close(index);
                    $.fn.zTree.init($("#resourceTree"), setting, data.data);
                })
        }
        //JavaScript代码区域
        layui.use('element', function(){
            var element = layui.element;
        });
    </script>
    <body>
        <div id="resourceTree" class="ztree"></div>
    </body>
</html>
<%-- <div class="layui-side layui-bg-black">
            <div class="layui-side-scroll">
                <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                    <li class="layui-nav-item"><a href="<%=request.getContextPath()%>/auth/user/toShow" target="right">用户展示</a></li>
                    <li class="layui-nav-item"><a href="<%=request.getContextPath()%>/auth/role/toShow" target="right">角色展示</a></li>
                    <li class="layui-nav-item"><a href="<%=request.getContextPath()%>/auth/resource/toShow" target="right">资源展示</a></li>
                </ul>
            </div>
        </div>--%>