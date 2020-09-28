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
        <title>订单展示</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\echarts.min.js"></script>
    </head>
    <script type="text/javascript">
        //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
        layui.use('element', function(){
            var element = layui.element;
            element.on('tab(filter)', function(data){
                if (data.index == 0) {
                    if (${USER.userRole == 1}) {
                        getUserLoginNum();
                    } else {
                        layer.msg("无权限", {icon:4});
                    }
                } else if (data.index == 1) {
                    getOrderTotalVolume();
                } else if (data.index == 2) {
                    getOrderStatus();
                }
            });
        });
        //近七日登录用户量
        function getUserLoginNum() {
            $.get(
                "<%=request.getContextPath() %>/statement/getUserLoginNum",
                {},
                function(data){
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var days = [];
                    var peopleNumbers = [];
                    for(var i = 0; i < data.data.length; i++){
                        days.push(data.data[i].days);
                        peopleNumbers.push(data.data[i].number);
                    }
                    //获取dom容器并清空
                    var userLoginNumChart = echarts.init(document.getElementById('userLoginNum'));
                    userLoginNumChart.clear();
                    var option = {
                        title: {
                            subtext: '近七日登录用户量'
                        },
                        tooltip: {},
                        legend: {
                            data:['近七日']
                        },
                        xAxis: {
                            type: 'category',
                            data: days
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            name: '近七日',
                            data: peopleNumbers,
                            type: 'line',
                            smooth: true
                        }]
                    };
                    //赋值
                    userLoginNumChart.setOption(option);
                })
        }
        //一个月
        function getUserLoginJanuaryNum(january) {
            $.get(
                "<%=request.getContextPath() %>/statement/getUserLoginNum",
                {"january" : january},
                function(data){
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var days = [];
                    var peopleNumbers = [];
                    for(var i = 0; i < data.data.length; i++){
                        days.push(data.data[i].days);
                        peopleNumbers.push(data.data[i].number);
                    }
                    //获取dom容器并清空
                    var userLoginNum = echarts.init(document.getElementById('userLoginNum'));
                    userLoginNum.clear();
                    var option = {
                        title: {
                            subtext: '近一月登录用户量'
                        },
                        tooltip: {},
                        legend: {
                            data:['近一月']
                        },
                        xAxis: {
                            type: 'category',
                            data: days
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            name: '近一月',
                            data: peopleNumbers,
                            type: 'line',
                            smooth: true
                        }]
                    };
                    //赋值
                    userLoginNum.setOption(option);
                })
        }

        //近七日订单成交量
        function getOrderTotalVolume() {
            $.get(
                "<%=request.getContextPath() %>/statement/getOrderTotalVolume",
                {},
                function (data) {
                    if (data.code != 200) {
                        layer.alert(data.msg);
                        return;
                    }
                    var days = [];
                    var peopleNumbers = [];
                    for (var i = 0; i < data.data.length; i++) {
                        days.push(data.data[i].days);
                        peopleNumbers.push(data.data[i].number);
                    }
                    //获取dom容器并清空
                    var orderTotalVolume = echarts.init(document.getElementById('orderTotalVolume'));
                    orderTotalVolume.clear();
                    var option = {
                        title: {
                            subtext: '订单总成交量'
                        },
                        tooltip: {},
                        legend: {
                            data: ['近七日']
                        },
                        xAxis: {
                            type: 'category',
                            data: days
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [{
                            name: '近七日',
                            data: peopleNumbers,
                            type: 'bar',
                            smooth: true
                        }]
                    };
                    //赋值
                    orderTotalVolume.setOption(option);
                })
        }

        //订单状态饼状图
        function getOrderStatus() {
            $.get("<%=request.getContextPath() %>/statement/getOrderStatusByProduct",
                {},
                function(data){
                    if (data.code != 200) {
                        layer.msg(data.msg);
                        return;
                    }
                    var productNames = [];
                    for(var i = 0; i < data.data.length; i++){
                        productNames.push(data.data[i].productName);
                    }
                    var res=[];
                    $.each(data.data,function(key,v){
                        res.push({
                            value:v.number,
                            name:v.productName
                        });
                    });
                    var orderStatus = echarts.init(document.getElementById('orderStatus'));
                    orderStatus.clear();
                    var option = {
                        title: {
                            text: '各商品订单比例',
                            subtext: '纯属真实',
                            left: 'center',
                            textStyle: {
                                fontSize: 30,
                                color: "rgba(225, 0, 255, 1)",
                                fontStyle: "italic"
                            },
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data: productNames
                        },
                        series: [
                            {
                                name: '各商品订单比例',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data:res,
                                emphasis: {
                                    itemStyle: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    orderStatus.setOption(option);
                })
        }
    </script>
    <body>
        <div class="layui-tab" lay-filter="filter">
            <ul class="layui-tab-title">
                <li>用户统计</li>
                <li>订单总成交量</li>
                <li>订单状态</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item">
                    <c:if test="${USER.userRole == 1}">
                        <input type="button" value="近七日用户登录量" onclick="getUserLoginNum(1)" class="layui-btn layui-btn-normal">
                        <input type="button" value="近一月用户登录量" onclick="getUserLoginJanuaryNum(1)" class="layui-btn layui-btn-normal"><br/>
                        <div id="userLoginNum" style="width:49%;height:450px;float:left"></div>
                    </c:if>
                </div>
                <div class="layui-tab-item">
                    <div id="orderTotalVolume" style="width: 600px;height:400px;"></div>
                </div>
                <div class="layui-tab-item">
                    <div id="orderStatus" style="width: 600px;height:400px;"></div>
                </div>
            </div>
        </div>
    </body>
</html>
