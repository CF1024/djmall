<%--
  ~ 作者：CF
  ~ 日期：2020-08-16 17:53
  ~ 项目：djmall
  ~ 模块：djmall_admin
  ~ 类名：comment.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/8/16
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
    <head>
        <title>查看评论</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
    </head>
    <style type="text/css">
        a{color: #0000FF;}
    </style>
    <script type="text/javascript">
        $(function () {
            search();
        })

        function search() {
            var index = layer.load(1);
            $.post(
                "<%=request.getContextPath()%>/product/spu/getCommentByProductId",
                $("#fm").serialize(),
                function (data) {
                    layer.close(index);
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var html = "";
                    for (var i = 0; i < data.data.list.length; i++) {
                        var review = data.data.list[i];
                        html += "<tr>";
                        html += "<td width='200px'>";
                        html += review.nickName + "<br>";
                        html += review.createTime;
                        html += "</td>";
                        html += "<td>";
                        html += "<div id='rateDiv_"+i+"'></div><br>";
                        html += review.comment + "<br>";
                        if (review.replyList.length <= 0) {
                            html += '<button type="button" onclick="addReply('+review.id+')" class="layui-btn layui-btn-primary">回复</button>';
                        }
                        html += "</td>";
                        html += "</tr>";
                        for (var j = 0; j < review.replyList.length; j++) {
                            var reply = review.replyList[j];
                            html += "<tr>";
                            html += "<td width='200px'>";
                            html += "<span style='color: red'>商家回复:</span><br>";
                            html += reply.createTime;
                            html += "</td>";
                            html += "<td style='color: red'>";
                            html += reply.comment + "<br>";
                            html += "</td>";
                            html += "</tr>";
                        }
                    }
                    $("#tbd").html(html);
                    layui.use(['rate'], function(){
                        var rate = layui.rate;

                        for (var i = 0; i < data.data.list.length; i++) {
                            var review = data.data.list[i];
                            //渲染
                            //只读
                            rate.render({
                                elem: '#rateDiv_'+i  //绑定元素
                                ,value: review.rate
                                ,readonly: true
                            });
                        }
                    });
                    //星星回显 一定要放在拼接赋值后边
                    var pageHtml = "";
                    var pageNo = parseInt($("#pageNo").val());
                    pageHtml += "<a href='javascript:page("+0+","+data.data.pages+","+(pageNo + 1)+")'>点击查看更多</a>";
                    $("#pageInfo").html(pageHtml);
                }
            );
        }

        //加载
        function page(index, pages, pageNo) {
            if (pageNo > pages) {
                var pageHtml = "我是有底线的~";
                $("#pageInfo").html(pageHtml);
                return;
            }
            $("#pageNo").val(pageNo);
            search();
        }

        function addReply(id) {
            layer.open({
                type: 1,
                content: '回复内容：<textarea id="comment"></textarea>',
                title: "回复",
                shadeClose: true,
                shade: 0.5,
                area: ['50%', '80%'],
                btn: ['确认', '取消'],
                yes: function (index) {
                    $.post(
                        "<%=request.getContextPath()%>/product/spu/addReply",
                        {"replyId":id, "comment":$("#comment").val()},
                        function (data) {
                            if (data.code != 200) {
                                layer.alert(data.msg);
                                return;
                            }
                            window.location.reload();
                        }
                    );
                    layer.close(index);
                }
            });
        }
        //声明from模块，否则select、checkbox、radio等将无法显示，并且无法使用form相关功能
        layui.use('form', function () {
            var form = layui.form;
            form.render();//重点在这里
            form.on('radio(commentType)', function(){
                search();
            });
        });
    </script>
    <body>
        <form class="layui-form" id="fm">
            <input type="hidden" name="pageNo" id="pageNo" value="1">
            <input type="hidden" name="productId" value="${productId}">
            好评率:<i style="font-size: 30px; color: red">${goodRate}%</i>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <input type="radio" name="commentType" value="0" lay-filter="commentType" title = "所有评论" checked>
                    <input type="radio" name="commentType" value="1" lay-filter="commentType" title = "好评">
                    <input type="radio" name="commentType" value="2" lay-filter="commentType" title = "中评">
                    <input type="radio" name="commentType" value="3" lay-filter="commentType" title = "差评">
                </div>
            </div>
            <hr>
            <table class="layui-table" id="tbd">

            </table>
        </form>
        <div id="pageInfo" align="center"></div>
    </body>
</html>
