<%--
  ~ 作者：CF
  ~ 日期：2020-07-06 10:25
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：related.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

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
    </head>
    <script type="text/javascript">
        //已选中的复选框
        function selected() {
            var boxObj = $("input:checkbox[name='attrId']");    //获取所有的复选框
            var attrIds = '${attrIds}';                     //用el表达式获取在控制层存放的复选框的值为字符串类型
            var attrIdsList = attrIds.substring(0, attrIds.length - 1);             //去掉它们之间的分割符“，”
            for(i = 0; i < boxObj.length; i++){
                for(j = 0; j < attrIdsList.length; j++){            //如果值与修改前的值相等则选中
                    if(boxObj[i].value == attrIdsList[j]) {
                        boxObj[i].checked= true;
                        break;
                    }
                }
            }
        }

        $(function () {
            show();
        });

        function show() {
            var index = layer.load(0, {shade:0.5});
            $.get(
                "<%=request.getContextPath()%>/dict/attr/",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.length; i++) {
                        var attr = data.data[i];
                        html += "<tr>";
                        html += "<td><input type = 'checkbox' name = 'attrId' value = '"+attr.attrId+"'>"+attr.attrId+"</td>";
                        html += "<td>" + attr.attrName +"</td>";
                        html += attr.attrValue == null ? "<td>暂无属性值</td>":"<td>" + attr.attrValue +"</td>";
                        html += "</tr>";
                    }
                    $("#tbd").html(html);
                    selected();
                }
            );
        }

        //保存
        function RelatedAttr() {
            var index = layer.load(0,{shade:0.5});
            var ids = "";
            $("input[name='attrId']:checked").each(function (index, item) {
                if ($("input[name='attrId']:checked").length - 1 == index) {
                    ids += $(this).val();
                } else {
                    ids += $(this).val() + ",";
                }
            });
            $.post(
                "<%=request.getContextPath()%>/dict/skuGm/",
                {"ids" : ids, "productType" : '${productType}'},
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
        };

        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });
    </script>
    <body>
        <form class="layui-form" id="fm">
            <div class="layui-form-item">
                <div class="layui-input-inline">
                    <input type="button" value="保存" onclick="RelatedAttr()" class="layui-btn layui-btn-normal">
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
                <th>属性名</th>
                <th>属性值</th>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
    </body>
</html>
