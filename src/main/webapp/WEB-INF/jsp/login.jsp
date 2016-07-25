<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上品折扣供应商管理平台</title>
<script type="text/javascript">
	<%request.setAttribute("ctxPath", request.getContextPath());%>
</script>
<script type="text/javascript">
	var __ctxPath="<%=request.getContextPath() %>";
	var baseUrl = "${ctxPath}";
</script>
<style type="text/css">
html, body { width:100%; height:100%; overflow:hidden}
body { margin:0; padding:0; background:-webkit-gradient(radial, center center, 0, center center, 460, from(#0064a4), to(#2F2727)); background:-webkit-radial-gradient(circle, #0064a4, #2F2727); background:-moz-radial-gradient(circle, #0064a4, #2F2727); background:-ms-radial-gradient(circle, #0064a4, #2F2727); filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#0064a4', endColorstr='#2F2727'); -ms-filter:"progid:DXImageTransform.Microsoft.gradient(startColorstr='#0064a4', endColorstr='#2F2727')"}
.wrap { position:relative; top:50%; width:380px; height:260px; margin:-140px auto 0}
.titleBg { position:relative; top:51%; height:60px; margin:0 auto; background:#007aa4; box-shadow:0 0 20px #0a3e5f; animation:centerToAll 1s linear; -moz-animation:centerToAll 1s ease; -ms-animation:centerToAll 1s ease; -webkit-animation:centerToAll 1s ease; -o-animation:centerToAll 1s ease}
.loginForm { box-shadow:5px 5px 10px rgba(0,0,0,.2); position:absolute; z-index:0; background-color:#fff; border-radius:4px; height:100%; width:100%; background:-webkit-gradient(linear, left top, left 24, from(#eee), color-stop(4%, #fff), to(#eee)); background:-moz-linear-gradient(top, #eee, #fff 1px, #eee 24px); background:-o-linear-gradient(top, #eee, #fff 1px, #eee 24px); animation:loginForm 2s ease; -moz-animation:loginForm 2s ease; -ms-animation:loginForm 2s ease; -webkit-animation:loginForm 2s ease; -o-animation:loginForm 2s ease}
.loginForm:before { content:''; position:absolute; z-index:-1; border:1px dashed #ccc; top:5px; bottom:5px; left:5px; right:5px; box-shadow:0 0 0 1px #fff}
.loginForm h1 { text-transform:uppercase; text-align:center; color:#666; margin:25px 0 0 0; letter-spacing:4px; text-shadow:1px 1px 1px #fff; font:normal 26px/1.5 'microsoft YaHei', sans-serif;}
fieldset { border:none; padding:10px 20px 0}
fieldset input[type=text], fieldset input[type=password] { width:275px; height:34px; padding:3px 2.2em 3px 2.2em; border-radius:3px; outline:none; box-shadow:1px 1px 2px rgba(0,0,0,.1), inset 0 2px 5px rgba(0,0,0,.1); border:1px solid #ccc}
fieldset input[type=text]:focus, fieldset input[type=password]:focus { border-color:#f40}
fieldset input[type=text] { background:url(${ctxPath}/images/login-sprite.png) 5px 0px no-repeat}
fieldset input[type=password] { background:url(${ctxPath}/images/login-sprite.png) 5px -40px no-repeat}
fieldset input[type=submit] { height:30px; line-height:2em; padding:2px 20px; margin:6px 0 0 120px; text-align:center; letter-spacing:4px; cursor:pointer; font-weight:700; border:1px solid #ff1500; border-radius:3px; color:#fff; background-color:#ff6900; background:-webkit-gradient(linear, left top, left 24, from(#ff6900), color-stop(0%, #ff9800), to(#ff6900)); background:-moz-linear-gradient(top, #ff6900, #ff9800 0, #ff6900 24px); background:-o-linear-gradient(top, #ff6900, #ff9800 0, #ff6900 24px); filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff9800', endColorstr='#ff6900'); -ms-filter:"progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff9800', endColorstr='#ff6900')"}
fieldset input[type=submit]:hover { background-color:#e8670d; background:-webkit-gradient(linear, left top, left 24, from(#ff9800), color-stop(0%, #ff6900), to(#ff9800)); background:-moz-linear-gradient(top, #ff9800, #ff6900 0, #ff9800 24px); background:-o-linear-gradient(top, #ff6900, #ff6900 0, #ff9800 24px); filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff6900', endColorstr='#ff9800'); -ms-filter:"progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff6900', endColorstr='#ff9800')"}
.errorSpan li {list-style-type:none;}
.errorSpan li span {color:red }
.inputWrap { position:relative; margin:10px 0 0}
.inputWrap i { position:absolute; right:10px; top:12px; width:16px; height:16px; background:url(${ctxPath}/images/login-sprite.png) no-repeat 0 -93px}
.inputWrap i.errors { background:url(${ctxPath}/images/login-sprite.png) no-repeat 0 -133px}
.transition, .transition:focus, .transition:hover { transition:all 0.2s ease-in-out 0s; -moz-transition:all 0.2s ease-in-out 0s; -ms-transition:all 0.2s ease-in-out 0s; -o-transition:all 0.2s ease-in-out 0s; -webkit-transition:all 0.2s ease-in-out 0s}
.transition { transition:all 0.2s ease-in-out; -moz-transition:all 0.2s ease-in-out; -webkit-transition:all 0.2s ease-in-out; -ms-transition:all 0.3s ease-in-out; -o-transition:all 0.3s ease 0s}
</style>
</head>

<body>
<div class="titleBg"></div>
<div class="wrap">
<form action="${ctxPath}/login.html" method="post" id="login">
    <div class="loginForm">
        <h1>上品折扣供应商管理平台</h1>
        <div class="loginContent">

            <fieldset>
            	<div class="errorSpan">
            		<li class="error" > <span >${error}</span></li>
            	</div>
                <div class="inputWrap">
                    <input class="transition" name="username" id="username" value="${username}" type="text" placeholder="员工编号" autofocus required>
                    <i class="errors"></i>
                </div>
                <div class="inputWrap">
                    <input class="transition"  type="password" name="password" placeholder="请输入密码" required>
                    <i class="tip"></i>
                </div>
            </fieldset>
            <fieldset class="submit">
                <input class="transition" type="submit" value="登录">
            </fieldset>
        </div>
    </div>
</form>
</div>
</body>
</html>
