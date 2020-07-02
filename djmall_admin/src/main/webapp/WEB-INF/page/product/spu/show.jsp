<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/6/7
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>Title</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <script type="text/javascript">
      /*  $(function () {
            show();
        });

        function show() {
            var index = layer.load(0, {shade:0.5});
            $.get(
                "<%=request.getContextPath()%>/product/spu",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var pro = data.data.list[i];
                        html += "<tr>";
                        html += "<td><input type='checkbox' name='ids' value='"+pro.productId +"'></td>";
                        html += "<td>" + pro.productName +"</td>";
                        html += "<td>" + pro.productType +"</td>";
                        html += "<td>" + pro.productStatus +"</td>";
                        html += "<td>" + pro.freightShow +"</td>";
                        html += "<td>" + pro.productImg +"</td>";
                        html += pro.productDescribe == null ? "<td>暂无描述</td>" : "<td>" + pro.productDescribe +"</td>";
                        html += pro.praiseNumber == null ? "<td>暂无点赞量</td>" : "<td>" + pro.praiseNumber +"</td>";
                        html += pro.orderNumber == null ? "<td>暂无订单量</td>" : "<td>" + pro.orderNumber +"</td>";
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
*/
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
        //模糊查
        function fuzzySearch() {
            $("#pageNo").val(1);
            show();
        }
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();//重点在这里
            form.on('checkbox(userRole)', function(){
                fuzzySearch();
            });
        });

        //获取id
        function getIds() {
            var ids=new Array();
            $(":checkbox[name='ids']:checked").each(function () {
                ids.push($(this).val());
            });
            return ids;
        }

        //去新增商品
        function toAddProduct() {
            window.location.href = "<%=request.getContextPath()%>/product/spu/toAddProduct";
        }

        //去修改
        function toUpdateProduct() {
            var ids = getIds();
            if (ids.length < 1){
                layer.msg("请选择一个商品进行修改", {icon:6});
                return;
            }
            if (ids.length > 1) {
                layer.msg("只能选择一个商品进行修改！", {icon:6});
                return;
            }
            window.location.href = "<%=request.getContextPath()%>/product/spu/toUpdateProduct";
        }

        //上下架
        function shelf() {
            var ids = getIds();
            if (ids.length < 1){
                layer.msg("请选择一个商品进行操作", {icon:6});
                return;
            }
            if (ids.length > 1) {
                layer.msg("只能选择一个商品进行操作！", {icon:6});
                return;
            }
            layer.confirm('确认进行相关操作吗？', {icon: 3, title:'提示'}, function(index){
                $.post(
                    "<%=request.getContextPath()%>/product/spu/shelf",
                    {"id":ids[0]},
                    function (data) {
                        if(data.code != 200) {
                            layer.msg(data.msg, {icon:5,time:2000});
                            return;
                        }
                        layer.msg(data.msg, {icon: 6, time: 2000},
                            function() {
                                window.location.href = "<%=request.getContextPath()%>/product/spu/toShow";
                        });
                    }
                );
                layer.close(index);
            });
        }

    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" value="1" name="pageNo" id="pageNo">
            <%--<div class="layui-form-item">
                <label class="layui-form-label">搜索</label>
                <div class="layui-input-inline">
                    <input type="text" name="userName" placeholder="用户名/昵称/手机号/邮箱" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <input type="button" value="查询" onclick="fuzzySearch()" class="layui-btn layui-btn-normal">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">角色</label>
                <div class="layui-input-block">
                    <c:forEach items="${roleList}" var="role">
                        <input type="checkbox"  name="userRoleList" lay-filter="userRole" value="${role.roleId}" title="${role.roleName}">
                    </c:forEach>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label" >性别</label>
                <div class="layui-input-block" >
                    <c:forEach items="${sexList}" var="sex">
                        <input type="checkbox" name="userSexList" lay-filter="sex" value="${sex.baseCode}" title="${sex.baseName}">
                    </c:forEach>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">激活状态</label>
                <div class="layui-input-inline">
                    <select name="userStatus" lay-filter="status">
                        <option value="-1">请选择</option>
                        <c:forEach items="${statusList}" var="status">
                            <option value="${status.baseCode}">${status.baseName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>--%>
        </form>
        <shiro:hasPermission name="USER_UPDATE_BTN">
            <input type="button" value="增量索引" onclick="toUpdateUser()" class="layui-btn layui-btn-radius layui-btn-primary">
        </shiro:hasPermission>
        <shiro:hasPermission name="USER_UPDATE_BTN">
            <input type="button" value="重构索引" onclick="toUpdateUser()" class="layui-btn layui-btn-radius layui-btn-primary"><br /><br />
        </shiro:hasPermission>
        <shiro:hasPermission name="USER_UPDATE_BTN">
            <input type="button" value="新增" onclick="toAddProduct()" class="layui-btn layui-btn-radius layui-btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </shiro:hasPermission>
        <shiro:hasPermission name="USER_UPDATE_BTN">
            <input type="button" value="修改" onclick="toUpdateProduct()" class="layui-btn layui-btn-radius layui-btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </shiro:hasPermission>
        <shiro:hasPermission name="USER_ACTIVATION_BTN">
            <input type="button" value="上架/下架" onclick="shelf()" class="layui-btn layui-btn-radius">
        </shiro:hasPermission>
        <shiro:hasPermission name="USER_RESET_PASSWORD_BTN">
            <input type="button" value="查看评论" onclick="resetPwd()" class="layui-btn layui-btn-radius layui-btn-normal">
        </shiro:hasPermission>
        <shiro:hasPermission name="USER_DELETE_BTN">
            <input type="button" value="下载导入模板" onclick="removeUser()" class="layui-btn layui-btn-radius layui-btn-danger">
        </shiro:hasPermission>
        <shiro:hasPermission name="USER_AUTH_BTN">
            <input type="button" value="导入" onclick="toAuthUserRole()" class="layui-btn layui-btn-radius layui-btn-warm">
        </shiro:hasPermission>
        <table border="0px" class="layui-table" >
            <colgroup>
                <col width="100">
                <col width="100">
                <col>
            </colgroup>
            <thead>
            <tr>
                <th></th>
                <th>名称</th>
                <th>类型</th>
                <th>状态</th>
                <th>邮费</th>
                <th>商品图片</th>
                <th>描述</th>
                <th>点赞量</th>
                <th>订单量</th>
            </tr>
            </thead>
            <tbody id="tbd"></tbody>
        </table>
        <div id="pageInfo"></div>
    </body>
</html>
