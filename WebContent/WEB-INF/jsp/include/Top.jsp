<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="core" uri="/core-tags"%>
<link type="text/css" rel="stylesheet" href="/iidooo-cms/css/Top.css">
<div class="top-wrap">
	<div class="logo-wrap">
		<a href="http://www.iidooo.com" target="_blank"> <img alt="IIDOOO CMS" src="/iidooo-cms/img/Logo_white_8.png">
		</a>
	</div>
	<div class="menu-wrap">
		<div class="login-info">
			<ul>
				<li><a href='logout.action'>退出</a></li>
				<li class="">欢迎：${sessionScope.LOGIN_USER.userName}</li>
			</ul>
		</div>
		<core:mainMenu />
	</div>
</div>