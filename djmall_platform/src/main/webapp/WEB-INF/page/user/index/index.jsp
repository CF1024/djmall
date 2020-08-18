<%--
  ~ 作者：CF
  ~ 日期：2020-07-19 12:16
  ~ 项目：djmall
  ~ 模块：djmall_platform
  ~ 类名：index.jsp
  ~ 版权所有(C), 2020. 所有权利保留
  --%>

<%--
  Created by IntelliJ IDEA.
  User: CF
  Date: 2020/7/19
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>个人中心</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css" media="all">
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer/layer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\cookie.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/static\js\token.js"></script>
    </head>
    <script type="text/javascript">
        //JavaScript代码区域
        layui.use('element', function(){
            var element = layui.element;
        });

        //左侧菜单点击右侧显示
        $(function(){
            //获取src值
            $(".main_left a").on("click",function(){
                var address =$(this).attr("data-src");
                $("iframe").attr("src",address);
            });
            //一下代码是根据窗口高度在设置iframe的高度
            var frame = $("#iframeShow");
            var frameHeight = $(window).height();
            console.log(frameHeight);
            frame.css("height",frameHeight);
        });

        //是否登录
        $(function () {
            $("#hiddenLogout").hide();
            var token = cookie.get("TOKEN");
            var nickName = cookie.get("NICK_NAME");
            // 是否登录
            if (token === undefined && nickName === undefined) {
                $("#login").html("<i class='layui-icon layui-icon-username' style='font-size: 30px'></i>点我登录");
                $("#login").attr("href", "<%=request.getContextPath()%>/user/toLogin");
                $("#hiddenRegister").show();
                $("#hiddenLogout").hide();
            }  else if (token !== '' && nickName !== '') {
                $("#login").html("<img src='http://qeu5389un.bkt.clouddn.com/${user.userImg}' class='layui-nav-img'>" + nickName /*+ "<dl class='layui-nav-child'><dd><a href=''>基本资料</a></dd><dd><a href=''>安全设置</a></dd></dl>"*/);
                $("#login").attr("href", "<%=request.getContextPath()%>/user/index/toIndex?TOKEN=" + getToken());
                $("#hiddenRegister").hide();
                $("#hiddenLogout").show();
            }
        })

        //退出登录
        function signOut() {
            var index = layer.load(0,{offset: '230px', shade:0.5});
            token_post(
                "<%=request.getContextPath()%>/user/toLogout?TOKEN="+getToken(),
                {"_method": "DELETE"},
                function (data) {
                    layer.close(index);
                    layer.msg(data.msg, {offset: '230px', icon: 6, time: 2000},
                        function(){
                            cookie.clear("TOKEN");
                            cookie.clear("NICK_NAME");
                            window.location.href = "<%=request.getContextPath()%>/product/toShow";
                        });
                })
        }

    </script>
    <body class="layui-layout-body">
        <div class="layui-layout layui-layout-admin">
            <div class="layui-header">
                <div class="layui-logo" style="color: coral">个人中心</div>
                <div id="hiddenLogout">
                    <ul class="layui-nav layui-layout-left">
                        <li class="layui-nav-item" >
                            <a href="javascript:signOut()" style="color: springgreen"><i class="layui-icon layui-icon-logout" style="font-size: 30px"></i>退出登录</a>
                        </li>
                    </ul>
                </div>
                <ul class="layui-nav layui-layout-right">
                    <li class="layui-nav-item">
                        <a href="<%=request.getContextPath()%>/product/toShow"  style="color: aqua"><i class="layui-icon layui-icon-home" style="font-size: 30px"></i>首页</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="<%=request.getContextPath()%>/user/toLogin" id="login" style="color: violet"><i class="layui-icon layui-icon-username" style="font-size: 30px"></i>点我登录</a>
                    </li>
                    <li class="layui-nav-item" id="hiddenRegister">
                        <a href="<%=request.getContextPath()%>/user/toRegister" style="color: tomato"><i class="layui-icon layui-icon-user" style="font-size: 30px"></i>注册</a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="<%=request.getContextPath()%>/user/cart/toMyShoppingCart?TOKEN=${TOKEN}" style="color: dodgerblue"><i class="layui-icon layui-icon-cart" style="font-size: 30px"></i>我的购物车</a>
                    </li>
                </ul>
            </div>

            <div class="layui-side layui-bg-black">
                <div class="layui-side-scroll">
                    <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                    <ul class="layui-nav layui-nav-tree site-demo-nav">
                        <li class="layui-nav-item">
                            <dd class="main_left"><a data-src="/user/toShowUserDetails?TOKEN=${TOKEN}" target="_top">个人中心</a></dd>
                            <dd class="main_left"><a data-src="/user/address/toShowAddress?TOKEN=${TOKEN}" target="_top">收货地址</a></dd>
                            <dd class="main_left"><a data-src="/order/toShow?TOKEN=${TOKEN}" target="_top">我的订单</a></dd>
                        </li>
                    </ul>
                </div>
            </div>

            <div class="layui-body">
                <div style="padding: 15px;">
                    <div class="main_right">
                        <iframe frameborder="0" scrolling="yes" style="width: 100%" src="" id="iframeShow">
                        </iframe>
                    </div>
                </div>
            </div>

            <div class="layui-footer">
                <!-- 底部固定区域 -->
                <span class="layui-breadcrumb" lay-separator="|">
                    <a href="https://music.qugeek.com/app/player" target="_blank">墨灵音乐</a>
                    <a href="https://www.bilibili.com/" target="_blank">哔哩哔哩</a>
                    <a href="http://www.4399.com/" target="_blank">4399小游戏</a>
                    <a href="https://www.yy.com/dancing" target="_blank">YY直播</a>
                    <a href="https://www.baidu.com/" target="_blank">百度</a>
                    <a href="http://www.hao123.com/sports/wangzhi" target="_blank">体育</a>
                    <a href="http://v.hao123.baidu.com/" target="_blank">视频</a>
                    <a href="https://tv.cctv.com/lm/jrsf/" target="_blank">综艺</a>
                </span>
            </div>
        </div>
    </body>
</html>
