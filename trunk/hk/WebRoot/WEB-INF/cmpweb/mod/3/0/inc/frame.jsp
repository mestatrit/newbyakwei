<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@page import="java.util.Calendar"%><%@page import="com.hk.bean.CmpInfo"%>
<%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${title_value} ${o.name }</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/3/0/css/a.css" />
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/pub.js"></script>
		<c:if test="${epp_other_value!=null}">${epp_other_value}</c:if>
	</head>
	<body><iframe id="hideframe" name="hideframe" class="hide"></iframe>
		<script type="text/javascript">
		var path="<%=path %>";
		</script>
		<div class="hk">
			<div class="top">
				<div class="userop">
					<c:if test="${loginUser!=null}">
						<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${loginUser.userId}"><hk:data key="epp.myhome"/></a> | 
						<a href="<%=path %>/epp/web/op/reserve.do?companyId=${companyId}">我的预约</a> | 
						<a href="<%=path %>/epp/web/op/user/set.do?companyId=${companyId}"><hk:data key="epp.user.setting"/></a> | 
						<c:if test="${sys_cmpadminuser}"><a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">站点管理</a> | </c:if>
						<a href="<%=path %>/epp/logout_web.do?companyId=${companyId}"><hk:data key="epp.logout"/></a>
					</c:if>
					<c:if test="${loginUser==null}">
						<a class="split-r" href="<%=path %>/epp/login_web.do?companyId=${companyId}"><hk:data key="epp.login"/></a>
						<a class="split-r" href="<%=path %>/epp/web/user_reg.do?companyId=${companyId}"><hk:data key="epp.signup"/></a>
					</c:if>
				</div>
				<c:if test="${not empty o.logopath}">
					<div class="logo">
						<a href="http://<%=request.getServerName() %>"><img src="${o.logoPic }"/></a>
					</div>
				</c:if>
				<ul class="nav"><%EppViewUtil.loadCmpNavTop(request); %>
					<c:forEach var="website_nav" items="${website_navlist}">
						<li><a class="<c:if test="${parent_cmpNav.oid==website_nav.oid || navId==website_nav.oid || (active_bbs_cmpnav_column && cmpnav_cmpbbs_column.oid==website_nav.oid)}">cur</c:if>" href="/column/${companyId}/${website_nav.oid}">${website_nav.name }</a></li>
					</c:forEach>
				</ul>
				<div class="clr"></div>
			</div>
			<%String msg = MessageUtil.getMessage(request);%>
			<%if(msg!=null){ %><div class="alerts_notice"><%=msg %></div><%} %>
			${html_body_content }
			<%Calendar now=Calendar.getInstance();
				CmpInfo cmpInfo=(CmpInfo)request.getAttribute("cmpInfo");
				if(cmpInfo!=null){
					String domain=cmpInfo.getDomain();
					int idx=domain.indexOf('.');
					String info_tmp_domain=domain.substring(0,idx);
					request.setAttribute("info_tmp_domain",info_tmp_domain);
				}
				%>
			<div class="foot">* Copyright ® <%=now.get(Calendar.YEAR) %> ${info_tmp_domain } All rights reserved</div>
		</div>
	</body>
</html>