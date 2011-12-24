<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpJoinInApply"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<%
EppViewUtil.loadCmpNavFunc(request);
%>
<style type="text/css">
a.linkrow{
	display: block;
	font-size: 16px;
	line-height: 30px;
}
a.linkrow.active,
a.linkrow:hover{
background-color: #2398C9;
color: #ffffff;
font-weight: bold;
text-decoration: none;
}
</style>
<div class="mgrleft">
	<div class="divrow">
		<c:if test="${o.userId==loginUser.userId}">
			<a class="linkrow <c:if test="${active_0==1}">active</c:if>" href="<%=path %>/epp/mgr/web/adminuser.do?companyId=${companyId}">管理员</a>
		</c:if>
		<a class="linkrow <c:if test="${active_14==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/info_uploadlogo.do?companyId=${companyId}">网站logo</a>
		<a class="linkrow <c:if test="${active_1==1}">active</c:if>" href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">导航管理</a>
		<div style="margin-left: 20px;">
		<c:forEach var="mgrleft_nav" items="${mgrleft_navlist}">
			<c:if test="${!mgrleft_nav.homeNav}">
				<a href="<%=path %>/epp/web/op/webadmin/admincmpnav_view.do?oid=${mgrleft_nav.oid }&companyId=${companyId}">${mgrleft_nav.name }</a>
			</c:if>
		</c:forEach>
		</div>
		<a class="linkrow <c:if test="${active_16==1}">active</c:if>" href="<%=path %>/epp/logout_web.do?companyId=${companyId}">退出管理</a>
	</div>
</div>
