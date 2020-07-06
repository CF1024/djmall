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
  Date: 2020/6/15
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.validate.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    </head>
    <style type="text/css">
        .error{
            color: red;
        }
    </style>
    <script type="text/javascript">
        //修改
        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/dict/base/",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                parent.window.location.href = "<%=request.getContextPath()%>/dict/base/toShow";
                            });
                    });
            }
        });

        $(function(){
            $("#fm").validate({
                rules:{
                    baseName:{
                        required:true,//必输字段
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/dict/base/deDuplicate",
                            data:{
                                parentCode:function() {
                                    return $("#parentCode").val();
                                },
                                dataType:"json"
                            }
                        }
                    },
                    baseCode:{
                        required:true,//必输字段
                        remote: {
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/dict/base/deDuplicate",
                            data:{
                                parentCode:function() {
                                    return $("#parentCode").val();
                                },
                                dataType:"json"
                            }
                        }
                    }
                },
                messages:{
                    baseName:{
                        required:"字典名不能为空",//必输字段
                        remote:"字典名称已存在~"
                    },
                    baseCode:{
                        required:"不能为空",//必输字段
                        remote:"字典Code已存在~"
                    }
                }
            })
        });
    </script>
    <body>
        <form id="fm" class="layui-form" id="fm">
            <input type="hidden" name="_method" value="PUT"/>
            <input type="hidden" name="baseCode" value="${baseData.baseCode}" id="baseCode">
            <input type="hidden" name="parentCode" value="${baseData.parentCode}" id="parentCode">
            <div class="layui-form-item">
                <label class="layui-form-label">字典名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="baseName" id="baseName" value="${baseData.baseName}" placeholder="请输入字典名称" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <input type="submit" value="修改" class="layui-btn">
                </div>
            </div>
        </form>
    </body>
</html>