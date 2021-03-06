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
      $(function () {
            show();
        });

        function show() {
            var index = layer.load(0, {offset: '230px', shade:0.5});
            $.get(
                "<%=request.getContextPath()%>/product/spu/",
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
                        html += "<td width='200px'>" + pro.productName +"</td>";
                        html += "<td>" + pro.productType +"</td>";
                        html += "<td>" + pro.productStatus +"</td>";
                        html += pro.freight == "0.00" ? "<td>"+ pro.company +"-包邮</td>" : "<td>" +pro.company +" - "+ pro.freight +"元</td>";
                        html += "<td><img src='http://qeu5389un.bkt.clouddn.com/"+pro.productImg+"' style='width: 70px; height: 70px'></td>";
                        html += "<td>" + pro.productDescribe +"</td>";
                        html += "<td>" + pro.praiseNumber +"</td>";
                        html += "<td>" + pro.orderNumber +"</td>";
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
        //模糊查
        function fuzzySearch() {
            $("#pageNo").val(1);
            show();
        }
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();//重点在这里
            form.on('checkbox(productType)', function(){
                fuzzySearch();
            });
        });

        //获取id
        function getIds() {
            var ids=[];
            $(":checkbox[name='ids']:checked").each(function () {
                ids.push($(this).val());
            });
            return ids;
        }

        //增量索引
        function incrementalIndex() {
            var index = layer.load(0, {offset: '230px', shade:0.5});
            $.post(
                "<%=request.getContextPath()%>/product/spu/incrementalIndex",
                function (data) {
                    layer.close(index);
                    if(data.code !== 200) {
                        layer.msg(data.msg, {icon:5,time:2000});
                        return;
                    }
                    layer.msg(data.msg, {icon: 6, time: 2000},
                        function() {
                            window.location.href = "<%=request.getContextPath()%>/product/spu/toShow";
                        });
                })
        }

        //重构索引
        function refactoringTheIndex() {
            var index = layer.load(0, {offset: '230px', shade:0.5});
            $.post(
                "<%=request.getContextPath()%>/product/spu/refactoringTheIndex",
                function (data) {
                    layer.close(index);
                    if(data.code !== 200) {
                        layer.msg(data.msg, {icon:5,time:2000});
                        return;
                    }
                    layer.msg(data.msg, {icon: 6, time: 2000},
                        function() {
                            window.location.href = "<%=request.getContextPath()%>/product/spu/toShow";
                        });
                })
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
            window.location.href = "<%=request.getContextPath()%>/product/spu/toUpdateProduct/" + ids[0];
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
                        if(data.code !== 200) {
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
      //下载导入模板
      function toDownloadTheImportTemplate() {
          window.location.href = "<%=request.getContextPath()%>/product/spu/toDownloadTheImportTemplate";
      }

      //导入
      function importProduct() {
          layer.open({
              type: 1,
              content: '上传Excel：<input type="file" id="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"><br>',
              title: "导入文件",
              shadeClose: true,
              shade: 0.5,
              area: ['30%', '50%'],
              btn: ['确认', '取消'],
              yes: function (index) {
                  var formData = new FormData();
                  formData.append("file", $("#file")[0].files[0]);
                  var index1 = layer.load();
                  //上传文件只能ajax提交
                  $.ajax({
                      url:'<%= request.getContextPath() %>/product/spu/importProduct',
                      dataType:'json',
                      type:'POST',
                      data: formData,
                      processData : false, // 使数据不做处理
                      contentType : false, // 不要设置Content-Type请求头信息
                      shade: 0.5,
                      success: function(data){
                          layer.msg(data.msg,function () {
                              layer.close(index1);
                              if (data.code != 200) {
                                  return;
                              }
                              window.location.href = "<%= request.getContextPath() %>/product/toShow";
                          })
                      }
                  });
                  layer.close(index);
              }
          });
      }
        //查看评论
      function toViewComments() {
          var ids = getIds();
          if (ids.length < 1){
              layer.msg("请选择一个商品进行查看评论", {icon:6});
              return;
          }
          if (ids.length > 1) {
              layer.msg("只能选择一个商品进行查看评论！", {icon:6});
              return;
          }
          window.location.href = "<%= request.getContextPath() %>/product/spu/toViewComments/"+ids[0];
      }
    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" value="1" name="pageNo" id="pageNo">
            <div class="layui-form-item">
                <label class="layui-form-label">商品名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="productName" placeholder="请输入商品名称" class="layui-input">
                </div>
                <div class="layui-word-aux">
                    <input type="button" value="搜索" onclick="fuzzySearch()" class="layui-btn layui-btn-normal">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">商品类型</label>
                <div class="layui-input-block">
                    <jsp:useBean id="productTypeList" scope="request" type="java.util.List"/>
                    <c:forEach items="${productTypeList}" var="type">
                        <input type="checkbox"  name="productTypeList" lay-filter="productType" value="${type.productType}" title="${type.productType}">
                    </c:forEach>
                </div>
            </div>
        </form>

        <shiro:hasPermission name="INCREMENTAL_INDEX_BTN">
            <input type="button" value="增量索引" onclick="incrementalIndex()" class="layui-btn layui-btn-radius layui-btn-primary">
        </shiro:hasPermission>
        <shiro:hasPermission name="REFACTORING_THE_INDEX_BTN">
            <input type="button" value="重构索引" onclick="refactoringTheIndex()" class="layui-btn layui-btn-radius layui-btn-primary"><br /><br />
        </shiro:hasPermission>
        <shiro:hasPermission name="PRODUCT_ADD_BTN">
            <input type="button" value="新增" onclick="toAddProduct()" class="layui-btn layui-btn-radius layui-btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </shiro:hasPermission>
        <shiro:hasPermission name="PRODUCT_UPDATE_BTN">
            <input type="button" value="修改" onclick="toUpdateProduct()" class="layui-btn layui-btn-radius layui-btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </shiro:hasPermission>
        <shiro:hasPermission name="PRODUCT_SHELF_BTN">
            <input type="button" value="上架/下架" onclick="shelf()" class="layui-btn layui-btn-radius">
        </shiro:hasPermission>
        <shiro:hasPermission name="VIEW_COMMENTS_BTN">
            <input type="button" value="查看评论" onclick="toViewComments()" class="layui-btn layui-btn-radius layui-btn-normal">
        </shiro:hasPermission>
        <shiro:hasPermission name="DOWNLOAD_THE_IMPORT_TEMPLATE_BTN">
            <input type="button" value="下载导入模板" onclick="toDownloadTheImportTemplate()" class="layui-btn layui-btn-radius layui-btn-danger">
        </shiro:hasPermission>
        <shiro:hasPermission name="IMPORT_BTN">
            <input type="button" value="导入" onclick="importProduct()" class="layui-btn layui-btn-radius layui-btn-warm">
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
