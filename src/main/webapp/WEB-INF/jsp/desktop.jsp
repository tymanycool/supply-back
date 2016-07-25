<%@ page language="java" import="java.util.*" contentType="text/html" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
   <head>
		<title>供应商管理后台</title>
		<script type="text/javascript">
			<%request.setAttribute("ctxPath", request.getContextPath());%>
		</script>
		<link rel="stylesheet" type="text/css" href="${ctxPath }/js/extjs/resources/css/ext-all.css"/>
		<link rel="stylesheet" type="text/css" href="${ctxPath }/js/desktop/css/desktop.css" />
		
		
		
		<!-- add by liuchunlong for ledger begin -->
		<link rel="stylesheet" type="text/css" href="${ctxPath }/js/desktop/css/desktop-custom.css?v=1.0.0" />  <!-- 修改了css,解决本地浏览器缓存问题 -->
		<!-- add by liuchunlong for ledger end -->
		
		
		
		
		<link rel="stylesheet" type="text/css" href="${ctxPath }/js/extjs/src/ux/css/ItemSelector.css"/>
	  <script type="text/javascript" src="${ctxPath }/js/extjs/ext-all.js"></script>
	  <script type="text/javascript" src="${ctxPath }/js/extjs/locale/ext-lang-zh_CN.js"></script>
	  <script type="text/javascript">
			var _appctx="<%=request.getContextPath() %>";
			var username = '<%=session.getAttribute("username")%>';
			var userSid = '<%=session.getAttribute("sid")%>';
			var roleUser = '<%=session.getAttribute("roleUser")%>';
			var shopname = '<%=session.getAttribute("shopname")%>';
			var shopid = '<%=session.getAttribute("shopid")%>';
			var allResource = '<%=session.getAttribute("resources")%>';
			var userInfor = '<%=session.getAttribute("userInfor")%>';
			var user = Ext.JSON.decode(userInfor);
			
			Ext.tip.QuickTipManager.init();
			Ext.Loader.setConfig({enabled:true});
			Ext.Loader.setPath({				//命名空间的设置
				'Ext.ux.desktop': 'js/desktop/core',
				'MyDesktop': 'js/desktop/modules',
				'Member':'js/member',
				'Util':'js/util',
				'Supply':'js/supply',
				'ShopinDesktop':'js/desktop/shopinModules'
			});
			Ext.require('MyDesktop.App');
			var myDesktopApp;
			Ext.onReady(function () {
				myDesktopApp = new MyDesktop.App();
			});
		</script>
	</head>


	<body style="background-image: url(wallpapers/Dark-Sencha-back.jpg)">
	</body>
</html>
