<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.frame.util.DataUtil"%>
<%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/2/0/css/org.css" />
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/pub.js"></script>
		<title>${title_value} ${cmpOrg.name }</title>
		<script type="text/javascript">
		var path="<%=path%>";
		</script>
		<c:if test="${epp_other_value!=null}">${epp_other_value}</c:if>
		<style type="text/css">
			<c:if test="${not empty cmpOrg.path}">
			.banner{
			background: url("${cmpOrg.bgUrl}") no-repeat;
			}
			</c:if>
		</style>
		<c:if test="${cmpOrg.openStyle && not empty cmpOrg.styleData}">
			<style type="text/css">
			<c:if test="${not empty sys_CmpOrgStyle.bgColor}">body {background-color: #${sys_CmpOrgStyle.bgColor};}</c:if>
			<c:if test="${not empty sys_CmpOrgStyle.titleColor}">#top h1 {color: #${sys_CmpOrgStyle.titleColor};}</c:if>
			<c:if test="${not empty sys_CmpOrgStyle.linkColor}">a {color: #${sys_CmpOrgStyle.linkColor};}</c:if>
			<c:if test="${not empty sys_CmpOrgStyle.linkHoverColor}">a:hover {color: #${sys_CmpOrgStyle.linkHoverColor};}</c:if>
			<c:if test="${not empty sys_CmpOrgStyle.navLinkHoverBgColor}">ul.ullist2 li a.cur, ul.ullist2 li a:hover {background-color: #${sys_CmpOrgStyle.navLinkHoverBgColor};}</c:if>
			</style>
		</c:if>
	</head>
	<body><iframe id="hideframe" name="hideframe" class="hide"></iframe>
		<p class="linefix"></p>
		<div id="top">
			<div id="userop">
				<c:if test="${loginUser==null}">
					<a href="javascript:tologin()">登录</a>
				</c:if>
				<c:if test="${loginUser!=null}">
					<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${loginUser.userId}"><hk:data key="epp.myhome"/></a> | 
					<a href="<%=path %>/epp/web/op/user/set.do?companyId=${companyId}"><hk:data key="epp.user.setting"/></a> | 
					<c:if test="${sys_cmpadminuser}"><a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">站点管理</a> | </c:if>
					<a href="<%=path %>/epp/logout_web.do?companyId=${companyId}">退出</a>
				</c:if>
			</div>
			<div class="banner">
				<h1>${cmpOrg.name }</h1>
			</div>
		</div>
		<%String msg = MessageUtil.getMessage(request);%>
		<%if(msg!=null){ %>
		<div class="alerts_notice"><%=msg %></div>
		<%} %>
		<div class="hk">
			<p class="linefix"></p>
			<div id="body">
				<div class="inpad">
					<div class="l">
						<div class="mod">
							<ul class="ullist2">
								<c:forEach var="orgnav" items="${cmporgnavlist}">
								<li>
									<a<c:if test="${orgnavId==orgnav.navId}"> class="cur"</c:if> href="/edu/${companyId }/${orgId}/column/${orgnav.navId}">${orgnav.name }</a>
								</li>
								</c:forEach>
								<c:if test="${adminorg}">
									<li>
										<a<c:if test="${org_active_info==1}"> class="cur"</c:if> href="<%=path %>/epp/web/org/org_update.do?companyId=${companyId }&orgId=${orgId}">企业信息</a>
									</li>
									<li>
										<a<c:if test="${org_active_navlist==1}"> class="cur"</c:if> href="<%=path %>/epp/web/org/org_navlist.do?companyId=${companyId }&orgId=${orgId}">栏目管理</a>
									</li>
									<li>
										<a<c:if test="${org_active_userlist==1}"> class="cur"</c:if> href="<%=path %>/epp/web/org/studyad_userlist.do?companyId=${companyId }&orgId=${orgId}">报名管理</a>
									</li>
									<li>
										<a<c:if test="${org_active_bg==1}"> class="cur"</c:if> href="<%=path %>/epp/web/org/org_createbg.do?companyId=${companyId }&orgId=${orgId}">标题背景图</a>
									</li>
									<c:if test="${cmpOrg.openStyle}">
									<li>
										<a<c:if test="${org_active_style==1}"> class="cur"</c:if> href="<%=path %>/epp/web/org/org_updatestyle.do?companyId=${companyId }&orgId=${orgId}">配色</a>
									</li>
									</c:if>
								</c:if>
							</ul>
						</div>
					</div>
					<div class="r">
						${html_body_content }
					</div>
					<div class="clr"></div>
					<div id="footer">
						<a href="http://<%=request.getServerName() %>">${o.name }</a>
					</div>
				</div>
			</div>
		</div>
		<div class="clr"></div>
	<script type="text/javascript">
	function tologin(){
		tourl('<%=path %>/epp/login_web.do?companyId=${companyId}&return_url='+encodeLocalURL());
	}
	</script>
	</body>
</html>