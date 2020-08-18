<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：right.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

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
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <script>
        //JavaScript代码区域
        layui.use('element', function(){
            var element = layui.element;

        });
        $(function(){
            show();
        })

        function show(){
            $.get("<%=request.getContextPath()%>/order/showInform",
                {},
                function(data){
                    //消息条数
                    var numberOfMessages = data.data.length;
                    //如果登录人是商户
                    if('${USER.userRole}' == 2 && numberOfMessages > 0){
                        layer.confirm('${USER.nickName}'+"您有"+numberOfMessages+"条待审核信息,是否前往处理",
                            function(index){
                                window.location.href="<%=request.getContextPath()%>/order/toShow";
                                layer.close(index);
                            });
                    }
                })
        }
    </script>
    <body>
        <div class="layui-body" >

        </div>
    </body>
</html>
