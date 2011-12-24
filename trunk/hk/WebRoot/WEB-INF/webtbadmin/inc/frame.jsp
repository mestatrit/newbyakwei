<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.frame.util.MessageUtil"%><%@page import="com.hk.web.util.HkWebUtil"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/><link rel="stylesheet" type="text/css" href="${ctx_path }/webtb/css/a.css" /><link rel="shortcut icon" href="${ctx_path }/favicon.ico?v=2" /><script type="text/javascript" language="javascript" src="${ctx_path }/webtb/js/jquery-1.3.2.min.js"></script>	<script type="text/javascript" language="javascript" src="${ctx_path }/webtb/js/pub.js"></script>
	<script type="text/javascript">var path="${ctx_path }";var is_user_login=false;<c:if test="${login_user!=null}">is_user_login=true;</c:if></script>
	<c:if test="${html_head_value!=null}">${html_head_value}</c:if><title>${html_head_title } - 顾问家后台管理</title>
</head>
<body>
<div id="hk"><iframe id="hideframe" name="hideframe" class="hide"></iframe>
<div id="hkinner">
	<div id="header">
		<div id="top">
			<ul id="topNav">
				<c:if test="${login_user==null}">
					<li><a href="${ctx_path }/tb/login">登录</a></li>
					<li class="last"><a href="${ctx_path }/tb/register">注册</a></li>
				</c:if>
				<c:if test="${login_user!=null}">
					<li class="first withImage">
						<a href="/p/${login_user.userid }"><img alt="${login_user.show_nick }" title="${login_user.show_nick }" src="${login_user.pic_url_80 }"/></a><a href="/p/${login_user.userid }">我的主页</a>
					</li>
					<li><a href="${ctx_path }/tb/ask_prvask">我要提问</a></li>
					<li><a href="${ctx_path }/tb/item_prvcreate">添加商品</a></li>
					<li class="last"><a href="${ctx_path }/tb/logout">退出</a></li>
				</c:if>
			</ul>
			<div id="logo">
				<a href="/"><img src="${ctx_path }/webtb/img/guwenjia.png" style="vertical-align: bottom;"/></a>
				<div class="test">内测</div>
			</div>
			<div class="clr"></div>
		</div>
		<div id="bottom">
			<ul id="mainNav">
			</ul>
		</div>
		<div id="bottomGrad"></div>
	</div>
	<div id="body"><br class="linefix" />
	<%String msg = MessageUtil.getMessage(request);
	%><%if(msg!=null ){%><div class="alerts_notice"><%=msg %></div><%} %>
	<jsp:include page="mgrmenu.jsp"></jsp:include>
	<div style="margin-left: 140px">${html_body_content}</div>
	<div class="clr"></div>
	</div>
	<div id="footer">
		<div>
			<a>关于顾问家</a>
			|<a> 广告服务</a>
			|<a>招聘信息</a>
			|<a>建议和意见</a>
			|<a>友情链接</a>
			|<a>联系我们 </a>
		</div>
		<div>* Copyright ® 2009 huoku.com All rights reserved 京ICP备09054036号</div>
	</div>
</div></div></body></html>