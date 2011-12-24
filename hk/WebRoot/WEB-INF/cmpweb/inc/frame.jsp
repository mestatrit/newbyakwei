<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@page import="com.hk.frame.util.MessageUtil"%><%@page import="com.hk.frame.web.action.PathProcesser"%><%@page import="java.util.List"%><%@page import="com.hk.bean.CmpNav"%><%@page import="java.util.Calendar"%><%@page import="com.hk.bean.CmpInfo"%><%@page import="web.pub.util.EppFuncTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%EppViewUtil.loadCmpNavTop(request);
EppViewUtil.loadCmpWebColor(request);%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<title>${title_value} ${o.name }</title>
		<c:if test="${epp_other_value!=null}">${epp_other_value}</c:if>
		<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/${o.cmpflg }/${cmpInfo.tmlflg }/css/a.css" />
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/pub.js"></script>
		<script type="text/javascript">
		var path="<%=path %>";
		function checkin(){
			$.ajax({
				type:"POST",
				url:"<%=path%>/epp/web/info_prvcheckin.do?companyId=${companyId}&func_tip=<%=EppFuncTip.CHECKIN %>&ajax=1",
				cache:false,
		    	dataType:"html",
				success:function(data){
					refreshurl();
				}
			});
		}
		</script>
		<style type="text/css">
		
		<c:if test="${empty parent_cmpNav.filepath}">
		.page_r{
		padding-top: 0;
		}
		</c:if>
		<c:if test="${cmpInfo.enableUserStyle}">
			<c:if test="${not empty cmpWebColor.fontColor}">body{color: #${cmpWebColor.fontColor};}</c:if>
			<c:if test="${not empty cmpWebColor.linkColor}">a{color:#${cmpWebColor.linkColor };}</c:if>
			<c:if test="${not empty cmpWebColor.userNavBgColor}">#topNav {background-color:#${cmpWebColor.userNavBgColor};}</c:if>
			<c:if test="${not empty cmpWebColor.userNavLinkColor}">#topNav li a {color:#${cmpWebColor.userNavLinkColor};}</c:if>
			<c:if test="${not empty cmpWebColor.columnBgColor}">.nav{background-color: #${cmpWebColor.columnBgColor};}</c:if>
			<c:if test="${not empty cmpWebColor.columnLinkColor}">.nav ul li a{color: #${cmpWebColor.columnLinkColor};}</c:if>
			<c:if test="${not empty cmpWebColor.columnLinkHoverBgColor}">
			.nav ul li a:hover{background-color: #${cmpWebColor.columnLinkHoverBgColor};}
			.nav ul li.active a{background-color: #${cmpWebColor.columnLinkHoverBgColor};}
			</c:if>
			
			<c:if test="${not empty cmpWebColor.fontColor}">div.mod div.mod_title a { color : #${cmpWebColor.fontColor};}</c:if>
			<c:if test="${not empty cmpWebColor.linkColor}">div.mod div.mod_title a.more{color:#${cmpWebColor.linkColor};}</c:if>
			
			<c:if test="${not empty cmpWebColor.homeTitleLinkColor}">ul.article li a{color:#${cmpWebColor.homeTitleLinkColor };}</c:if>
			<c:if test="${not empty cmpWebColor.homeProductBgColor}">.product_area_bd{background-color: #${cmpWebColor.homeProductBgColor};}</c:if>
			.nav3 .cmpnav{
			<c:if test="${not empty cmpWebColor.column2Color}">color: #${cmpWebColor.column2Color};</c:if>
			<c:if test="${not empty cmpWebColor.column2BgColor}">background-color: #${cmpWebColor.column2BgColor};</c:if>
			}
			.nav3 .cmpbanner{<c:if test="${not empty cmpWebColor.column2BgColor}">background-color: #${cmpWebColor.column2BgColor};</c:if>}
			<c:if test="${not empty cmpWebColor.homeModTitleLinkColor}">.home_mod_title{color:#${cmpWebColor.homeModTitleLinkColor }};</c:if>
			<c:if test="${not empty cmpWebColor.homeModTitleLinkColor}">.home_mod_title{color:#${cmpWebColor.homeModTitleLinkColor};}</c:if>
			<c:if test="${not empty cmpWebColor.column2NavLinkColor}">.nav_2 a.nav_2_a{color: #${cmpWebColor.column2NavLinkColor};}</c:if>
			<c:if test="${not empty cmpWebColor.column2NavLinkActiveColor}">.nav_2 a.nav2_active{color:#${cmpWebColor.column2NavLinkActiveColor};}</c:if>
			<c:if test="${not empty cmpWebColor.buttonColor}">.btn{color:#${cmpWebColor.buttonColor};}</c:if>
			<c:if test="${not empty cmpWebColor.buttonBgColor}">.btn{background-color:#${cmpWebColor.buttonBgColor};}</c:if>
			<c:if test="${not empty cmpWebColor.buttonBorderColor}">.btn{border-color:#${cmpWebColor.buttonBorderColor};}</c:if>
		</c:if>
		</style>
	</head>
	<body><iframe id="hideframe" name="hideframe" class="hide"></iframe>
		<div class="hk">
			<div class="hkinner">
				<div id="header">
					<div style="">
						<ul id="topNav">
							<c:if test="${loginUser!=null}">
								<li class="first withImage">
									<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${loginUser.userId}"><img alt="${loginUser.nickName }" title="${loginUser.nickName }" src="${loginUser.head32Pic }"/></a>
									<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${loginUser.userId}"><hk:data key="epp.myhome"/></a>
								</li>
								<li><a href="javascript:checkin()">报到</a></li>
								<li><a href="<%=path %>/epp/web/op/user/set.do?companyId=${companyId}"><hk:data key="epp.user.setting"/></a></li>
								<c:if test="${sys_cmpadminuser}">
									<li><a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">站点管理</a></li>
								</c:if>
								<li class="last"><a href="<%=path %>/epp/logout_web.do?companyId=${companyId}"><hk:data key="epp.logout"/></a></li>
							</c:if>
							<c:if test="${loginUser==null}">
								<li class="first"><a href="http://<%=request.getServerName() %>"><hk:data key="epp.home"/></a></li>
								<li><a href="<%=path %>/epp/web/user_reg.do?companyId=${companyId}"><hk:data key="epp.signup"/></a></li>
								<li><a href="<%=path %>/epp/login_web.do?companyId=${companyId}"><hk:data key="epp.login"/></a></li>
							</c:if>
						</ul>
						<div class="logo">
							<c:if test="${not empty o.logopath}">
								<a href="http://<%=request.getServerName() %>"><img src="${o.logoPic }"/></a>
							</c:if>
						</div><%EppViewUtil.loadCmpLanguageRef(request); %>
						<c:if test="${fn:length(cmpLanguageRefList)>0}">
						<div class="language">
							<c:forEach var="clr" items="${cmpLanguageRefList}" varStatus="idx">
							<a href="http://${clr.refCmpInfo.domain }"><hk:data key="epp.language.view${clr.refCmpInfo.language}"/></a>
							</c:forEach>
						</div>
						</c:if>
						<div class="clr"></div>
					</div>
				</div>
			</div>
			<div class="hkinner" style="width: 860px;">
				<div class="nav">
					<ul>
						<c:forEach var="website_nav" items="${website_navlist}">
							<li class="<c:if test="${parent_cmpNav.oid==website_nav.oid || (active_bbs_cmpnav_column && cmpnav_cmpbbs_column.oid==website_nav.oid)}">active</c:if>"><a href="/column/${companyId}/${website_nav.oid}">${website_nav.name }</a></li>
						</c:forEach>
					</ul>
					<div class="clr"></div>
				</div>
				<c:if test="${not empty parent_cmpNav.filepath}">
				<c:set var="file_cmpnav" value="${parent_cmpNav}"></c:set>
				</c:if>
				<c:if test="${parent_cmpNav==null}">
				<c:set var="file_cmpnav" value="${cmpNav}"></c:set>
				</c:if>
				<c:if test="${not empty file_cmpnav.filepath}">
					<div class="nav3">
						<c:if test="${file_cmpnav.fileShowRow}">
							<c:if test="${file_cmpnav.imageShow}">
								<c:if test="${not empty file_cmpnav.fileShowLink}"><a href="http://${file_cmpnav.fileShowLink }" target="_blank"><img src="${file_cmpnav.picHUrl }" /></a></c:if>
								<c:if test="${empty file_cmpnav.fileShowLink}"><img src="${file_cmpnav.picHUrl }" /></c:if>
							</c:if>
							<c:if test="${file_cmpnav.flashShow}">
								<script type="text/javascript">
								document.write('<embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${file_cmpnav.flashHUrl }" play="true" loop="true" menu="true"></embed>');
								</script>
							</c:if>
						</c:if>
						<c:if test="${!file_cmpnav.fileShowRow}">
							<div class="cmpnav">
							${file_cmpnav.name }
							</div>
							<div class="cmpbanner">
								<c:if test="${file_cmpnav.imageShow}">
									<c:if test="${not empty file_cmpnav.fileShowLink}"><a href="http://${file_cmpnav.fileShowLink }" target="_blank"><img src="${file_cmpnav.picHUrl }" /></a></c:if>
									<c:if test="${empty file_cmpnav.fileShowLink}"><img src="${file_cmpnav.picHUrl }" /></c:if>
								</c:if>
								<c:if test="${file_cmpnav.flashShow}">
									<script type="text/javascript">
									document.write('<embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${file_cmpnav.flashHUrl }" play="true" loop="true" menu="true"></embed>');
									</script>
								</c:if>
							</div>
							<div class="clr"></div>
						</c:if>
					</div>
				</c:if>
				<c:if test="${file_cmpnav==null || empty file_cmpnav.filepath}">
					<div style="padding: 10px;"></div>
				</c:if>
			</div>
		</div>
		<c:if test="${top_cmpNav.imageShow}">
			<div class="banner">
				<img src="${top_cmpNav.picHUrl }" />
			</div>
		</c:if>
		<c:if test="${top_cmpNav.flashShow}">
			<div class="banner">
				<script type="text/javascript">
				document.write('<embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${top_cmpNav.flashHUrl }" play="true" loop="true" menu="true"></embed>');
				</script>
			</div>
		</c:if>
		<div class="hk">
			<div class="hkinner">
				<div id="body">
				<%String msg = MessageUtil.getMessage(request);%>
				<%if(msg!=null){ %>
				<div class="alerts_notice"><%=msg %></div>
				<%} %>
				${html_body_content }
				</div>
				<div style="border-top: 1px solid #e5e5e5;margin-top: 10px;">
				<%Calendar now=Calendar.getInstance();
				CmpInfo cmpInfo=(CmpInfo)request.getAttribute("cmpInfo");
				if(cmpInfo!=null){
					String domain=cmpInfo.getDomain();
					int idx=domain.indexOf('.');
					String info_tmp_domain=domain.substring(0,idx);
					request.setAttribute("info_tmp_domain",info_tmp_domain);
				}
				%>
					<div class="f_l">
					* Copyright ® <%=now.get(Calendar.YEAR) %> ${info_tmp_domain } All rights reserved
					</div>
					<div class="f_r">
					<a href="/${companyId}/link.html"><hk:data key="epp.frlink"/></a>
					</div>
					<div class="clr"></div>
				</div>
				<div id="footer">
					<a href="/m"><hk:data key="epp.view.tomobile"/></a>
				</div>
			</div>
		</div>
	</body>
</html>