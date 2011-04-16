<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="${appctx_path }/static/css/a.css" />
		<script type="text/javascript" language="javascript" src="${appctx_path }/static/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" language="javascript" src="${appctx_path }/static/js/pub.js"></script>
		<script type="text/javascript">
var path = "${appctx_path }";
var loading_path = "${appctx_path }/static/img/blue-loading.gif";</script>
		<c:if test="${html_head_value!=null}">${html_head_value}</c:if>
		<title>${html_head_title } - 后台管理</title>
	</head>
	<body>
		<div id="hk">
			<iframe id="hideframe" name="hideframe" class="hide"></iframe>
			<hk:actioninvoke mappinguri="/sysnotice" />
			<div id="hkinner">
				<div id="header">
					<div id="top">
						<div id="logo">
						</div>
						<div class="clr"></div>
					</div>
					<div id="bottom">
					</div>
					<div id="bottomGrad"></div>
				</div>
				<div id="body">
					<p class="linefix" />
					<c:if test="${not empty com_hk_message_name_request_session}">
						<div class="alerts_notice">
							${com_hk_message_name_request_session }
						</div>
					</c:if>
					${html_body_content }
				</div>
				<div id="footer"></div>
			</div>
		</div>
	</body>
</html>