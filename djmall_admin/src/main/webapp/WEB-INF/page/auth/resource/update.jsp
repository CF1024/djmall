<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：update.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/4
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        //表单验证
        $(function(){
            $("#fm").validate({
                rules:{
                    resourceName:{
                        required:true,
                        rangelength:[0,10],
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/resource/deDuplicate",
                            data:{
                                resourceId:function() {
                                    return $("#resourceId").val();
                                },
                                dataType:"json"
                            }
                        }
                    },
                    url:{
                        required:true,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/resource/deDuplicate",
                            data:{
                                resourceId:function() {
                                    return $("#resourceId").val();
                                },
                                dataType:"json"
                            }
                        }
                    },
                    resourceCode:{
                        required:true,
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/auth/resource/deDuplicate",
                            data:{
                                resourceId:function() {
                                    return $("#resourceId").val();
                                },
                                dataType:"json"
                            }
                        }
                    }
                },
                messages:{
                    resourceName:{
                        required:"请输入角色名",
                        rangelength:"长度限制在{0}到{1}之间",
                        remote:"资源已存在~"
                    },
                    url:{
                        required:"请输入url~",
                        remote:"url已存在~"
                    },
                    resourceCode:{
                        required:"请输入资源编码",
                        remote:"资源编码已存在~"
                    }
                }
            })
        });

        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0, {shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/auth/resource/update",
                    $("#fm").serialize(),
                    function(data) {
                        layer.close(index);
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                parent.window.location.href = "<%=request.getContextPath()%>/auth/resource/toShow";
                        });
                    }
                );
            }
        });
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });
    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" name="_method" value="PUT"/>
            <input type="hidden" name="resourceId" value="${resource.resourceId}" id="resourceId">

            <div class="layui-form-item">
                <label class="layui-form-label">资源名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="resourceName" value="${resource.resourceName}" id="resourceName" placeholder="请输入资源名称" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">资源编码</label>
                <div class="layui-input-inline">
                    <input type="text" name="resourceCode" value="${resource.resourceCode}" id="resourceCode" placeholder="请输入资源名称" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">url路径</label>
                <div class="layui-input-inline">
                    <input type="text" name="url" value="${resource.url}" id="url" placeholder="请输入资源名称" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">类型</label>
                <div class="layui-input-inline">
                    <select name="resourceType" id="resourceType">
                        <c:forEach items="${typeList}" var="type">
                            <option value="${type.baseCode}" <c:if test="${resource.resourceType == type.baseCode}">selected</c:if>>${type.baseName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <input type="submit" value="修改" class="layui-btn">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </div>
            </div>
        </form>
    </body>
</html>
