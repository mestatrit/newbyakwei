<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@page import="java.util.Calendar"%><%@page import="com.hk.bean.CmpInfo"%>
<%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${title_value} ${o.name }</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/2/0/css/a.css" />
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/pub.js"></script>
		<script type="text/javascript" src="<%=path %>/cmpwebst4/mod/2/0/js/site_nav.js"></script>
		<script type="text/JavaScript" src="<%=path %>/cmpwebst4/mod/2/0/js/org.js"></script>
		<script type="text/JavaScript" src="<%=path %>/cmpwebst4/mod/2/0/js/org_002.js"></script>
		<script type="text/javascript" src="<%=path %>/cmpwebst4/mod/2/0/js/g.js"></script>
		<c:if test="${epp_other_value!=null}">${epp_other_value}</c:if>
	</head>
	<body><iframe id="hideframe" name="hideframe" class="hide"></iframe>
		<script type="text/javascript">
		var path="<%=path %>";
		</script>
		<div class="hk">
			<br class="linefix" />
			<div class="userop">
				<c:if test="${loginUser!=null}">
					<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${loginUser.userId}"><hk:data key="epp.myhome"/></a> | 
					<a href="<%=path %>/epp/web/op/user/set.do?companyId=${companyId}"><hk:data key="epp.user.setting"/></a> | 
					<c:if test="${sys_cmpadminuser}"><a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">站点管理</a> | </c:if>
					<a href="<%=path %>/epp/logout_web.do?companyId=${companyId}"><hk:data key="epp.logout"/></a>
				</c:if>
				<c:if test="${loginUser==null}">
					<a class="split-r" href="<%=path %>/epp/login_web.do?companyId=${companyId}"><hk:data key="epp.login"/></a>
					<a class="split-r" href="<%=path %>/epp/web/user_reg.do?companyId=${companyId}"><hk:data key="epp.signup"/></a>
				</c:if>
			</div>
			<div class="top">
				<div class="topleft"><%EppViewUtil.loadAllCmpNav(request,false); %>
					<c:if test="${not empty o.logopath}">
					<div class="logo" style="height: 100%">
						<div class="fl">
							<a href="http://<%=request.getServerName() %>"><img src="${o.logoPic }" /></a>
						</div>
						<c:if test="${companyId==223}">
							<div class="fr" style="margin-right: 200px;margin-top: 20px;line-height: 50px;">
								<span style="font-size: 40px;">
								<a href="http://58.83.224.94:8080/star/bm" target="_blank" style="color: #a10000;">在线报名</a>
								</span>
							</div>
						</c:if>
						<div class="clr"></div>
					</div>
					</c:if>
					<div class="clr"></div>
					<div class="site-nav" style="margin-top: 10px;">
						<div id="floor_nav">
							<ul class="floors">
								<li class="navli" id="floor_0">
									<a name="t-label-h" href="http://<%=request.getServerName() %>"><span class="rd_l"></span><span class="rd_mid"><hk:data key="epp.home"/></span><span class="rd_r"></span></a>
								</li>
								<c:forEach var="cmpnavvo" items="${cmpnavvolist}">
									<c:if test="${!cmpnavvo.cmpNav.homeNav}">
										<li class="navli" id="floor_${cmpnavvo.cmpNav.oid }">
											<a href="/column/${companyId }/${cmpnavvo.cmpNav.oid}" class="track<c:if test="${parent_cmpNav.oid==cmpnavvo.cmpNav.oid}"> selected</c:if>"><span class="rd_l"></span><span class="rd_mid">${cmpnavvo.cmpNav.name }</span><span class="rd_r"></span></a>
										</li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
						<div class="sub-floor-menus" id="sub_floor_menus">
							<c:forEach var="cmpnavvo" items="${cmpnavvolist}">
								<c:if test="${fn:length(cmpnavvo.children)>0}">
									<div class="menu" id="sub_floor_${cmpnavvo.cmpNav.oid }">
										<div class="dht">
											<c:forEach var="child" items="${cmpnavvo.children}">
												<c:if test="${child.parentId == cmpnavvo.cmpNav.oid}">
													<p><a href="/column/${companyId }/${child.oid}">${child.name }</a></p>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="topright"></div>
				<div class="clr"></div>
			</div>
			<div class="mod"><%EppViewUtil.loadCmpArticleTagForPink(request,15); %>
				<div class="hotkind">
				<c:if test="${fn:length(pinktaglist)>0}">
				</c:if>
					<hk:data key="epp.cmparticletag.hot"/>：
					<c:forEach var="pinktag" items="${pinktaglist}"><a href="/tag/${companyId }/${pinktag.tagId}" class="split-r">${pinktag.name }</a></c:forEach>
				</div>
				<div class="search">
					<div class="inner">
						<form method="get" action="<%=path %>/epp/web/cmparticle_search.do">
							<hk:hide name="companyId" value="${companyId}"/>
							<input type="text" name="key" value="请输入关键词" class="text" onfocus="search_onfucus(this)" onblur="search_onblur(this)"/>
							<hk:submit value="epp.search" res="true"/>
						</form>
					</div>
				</div>
			</div><%String msg = MessageUtil.getMessage(request);%>
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
<script>
function search_onfucus(o){
	if(o.value=='请输入关键词'){
		o.value='';
	}
}
function search_onblur(o){
	if(o.value==''){
		o.value='请输入关键词';
	}
}
</script>
<c:if test="${companyId==223}">

<!-- 在线客服代码开始-->
            <div style="display:none"><a href="http://www.looyu.com">在线客服</a><a href="http://www.looyu.com">乐语</a> </div>
            <script type="text/javascript" src="http://js.doyoo.net/j.jsp?c=35580&f=80938"></script>
            <!-- 在线客服代码结束-->
 
<!-- QQ:在线悬窗代码： -->
 
                 <script language="javascript" src="http://qqjs4.user.55.la:81/user_pic/qqjs/2010/11/10/10/190475.js"></script>
 
 
<!-- sogou留言窗： -->
 
                    <script type="text/javascript" charset="gb2312" src="http://image.p4p.sogou.com/accountjs/70/347270.js"></script>

</c:if>
	</body>
</html>