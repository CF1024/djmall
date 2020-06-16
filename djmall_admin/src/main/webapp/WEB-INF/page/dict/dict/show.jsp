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
                "<%=request.getContextPath()%>/dict/base/",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var base = data.data.list[i];
                        html += "<tr>";
                        html += "<td>" + base.baseCode +"</td>";
                        html += "<td>" + base.baseName +"</td>";
                        html += "<td>" + base.parentCode +"</td>";
                        html += "<td>";
                        html += "<shiro:hasPermission name='DICT_UPDATE_BTN'><a class='layui-btn layui-btn-xs' href='javascript:toUpdate(\""+base.baseCode+"\")'>修改</a></shiro:hasPermission>";
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

        //去修改
        function toUpdate(baseCode) {
            //iframe层
            layer.open({
                type: 2,
                title: '修改',
                shadeClose: true,
                maxmin: true, //开启最大化最小化按钮
                shade: 0.8,
                area: ['500px', '40%'],
                content: '<%=request.getContextPath()%>/dict/base/'+baseCode
            });
        }

        //去新增
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
                                window.location.href = "<%=request.getContextPath()%>/dict/base/toShow";
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

        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });
    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" value="1" name="pageNo" id="pageNo">
            <div class="layui-form-item">
                <label class="layui-form-label">分类上级</label>
                <div class="layui-input-inline">
                    <select name="parentCode">
                        <option value="SYSTEM">SYSTEM</option>
                        <c:forEach items="${systemList}" var="SYSTEM">
                            <option value="${SYSTEM.baseCode}">${SYSTEM.baseName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">字典名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="baseName" id="baseName" placeholder="请输入字典名称" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">字典编码</label>
                <div class="layui-input-inline">
                    <input type="text" name="baseCode" id="baseCode" placeholder="请输入字典编码" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <shiro:hasPermission name="DICT_ADD_BTN">
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
                <th>CODE</th>
                <th>字典名</th>
                <th>上级CODE</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
        <div id="pageInfo"></div>
    </body>
</html>
