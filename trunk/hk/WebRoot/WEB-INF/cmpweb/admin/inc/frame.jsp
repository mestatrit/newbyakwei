<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.frame.util.MessageUtil"%><%@page import="com.hk.web.util.HkWebUtil"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path=request.getContextPath();%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/css/a.css" />
		<link rel="shortcut icon" href="<%=path %>/favicon.ico?v=2" />
		<script type="text/javascript" language="javascript" src="<%=path%>/cmpwebst4/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/cmpwebst4/js/pub.js"></script>
		<script type="text/javascript">
		var path="<%=path %>";
		var userLogin=false;
		<c:if test="${userLogin}">
		userLogin=true;
		</c:if>
		</script>
		<c:if test="${js_value!=null}">${js_value}</c:if>
		<title>${html_title }</title>
	</head>
	<body ${body_attr}>
		<div id="hk"><iframe id="hideframe" name="hideframe" class="hide"></iframe>
			<div id="hkinner">
				<div id="header">
					<div class="divrow" style="text-align: center;font-size: 48px;font-weight: bold;line-height: 50px;">
					${o.name } 后台管理
					</div>
					<div id="bottom">
                        <ul id="mainNav">
                           
                        </ul>
                    </div>
                    <div id="bottomGrad"></div>
				</div>
				<div id="body">
					<br class="linefix" />
					<%String msg = MessageUtil.getMessage(request);%>
					<%if(msg!=null){ %>
					<div class="alerts_notice"><%=msg %></div>
					<%} %>
					${html_body_content }
				</div>
				<div id="footer">
					<div>
						<a>关于本站</a>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>