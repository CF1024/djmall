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
                "<%=request.getContextPath()%>/dict/skuGm/",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var skuGm = data.data.list[i];
                        html += "<tr>";
                        html += "<td>" + skuGm.skuGmId +"</td>";
                        html += "<td>" + skuGm.productType +"</td>";
                        html += skuGm.attrName == null ? "<td>暂无属性名</td>":"<td>" + skuGm.attrName +"</td>";
                        html += "<shiro:hasPermission name='RELATED_ATTR_BTN'><td><a class='layui-btn layui-btn-normal layui-btn-xs' href='javascript:toRelatedAttr(\""+skuGm.productType+"\")'>关联属性</a></td></shiro:hasPermission>";
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

        //去关联属性
        function toRelatedAttr(productType) {
            window.location.href = "<%=request.getContextPath()%>/dict/skuGm/"+productType;
        }

        //去新增
        $.validator.setDefaults({
            submitHandler : function() {
                var index = layer.load(0,{shade:0.5});
                $.post(
                    "<%=request.getContextPath()%>/dict/skuGm/addSkuGm",
                    $("#fm").serialize(),
                    function (data) {
                        layer.close(index);
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                window.location.href = "<%=request.getContextPath()%>/dict/skuGm/toShow";
                            });
                    });
            }
        });

        $(function(){
            $("#fm").validate({
                rules:{
                    productType:{
                        required: true,    //要求输入不能为空
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                            type: 'GET',
                            url: "<%=request.getContextPath()%>/dict/skuGm/deDuplicate",
                            data:{
                                dataType:"json"
                            }
                        }
                    }
                },
                messages:{
                    productType:{
                        required: "请填写商品类型",
                        remote:"商品类型已存在"
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
            <div class="layui-form-item">
                <label class="layui-form-label">商品类型</label>
                <div class="layui-input-inline">
                    <input type="text" name="productType" id="productType" placeholder="请输入商品类型" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <shiro:hasPermission name="SKU_GM_ADD_BTN">
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
                <th>商品类型</th>
                <th>属性名</th>
                <shiro:hasPermission name="RELATED_ATTR_BTN">
                <th>操作</th>
                </shiro:hasPermission>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
        <div id="pageInfo"></div>
    </body>
</html>
