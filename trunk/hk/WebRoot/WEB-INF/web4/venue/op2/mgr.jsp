<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);
HkWebUtil.loadCmpFuncInfo(request);
%>
<c:set var="html_title" scope="request">${company.name } - 设置</c:set>
<c:set var="html_body_content" scope="request">
<div class="f_l" style="width: 150px;">
<ul class="datalist">
	<li><a href="<%=path %>/h4/venue.do?companyId=${companyId}">返回</a></li>
	<li><a href="<%=path %>/h4/op/venue/cmp.do?companyId=${companyId}">足迹信息</a></li>
	<li><a href="<%=path %>/h4/op/venue/photo.do?companyId=${companyId}">图片管理</a></li>
	<li><a href="<%=path %>/h4/op/venue/photo_photosetlist.do?companyId=${companyId}">图集管理</a></li>
	<c:if test="${hasfuncoid_reserve}">
		<li><a href="<%=path %>/h4/op/venue/reserve_list.do?companyId=${companyId}">预约管理</a></li>
		<li><a href="<%=path %>/h4/op/venue/reserve_report.do?companyId=${companyId}">工作统计</a></li>
		<li><a href="<%=path %>/h4/op/venue/actor_rolelist.do?companyId=${companyId}">角色管理</a></li>
		<li><a href="<%=path %>/h4/op/venue/actor.do?companyId=${companyId}">工作人员管理</a></li>
		<li><a href="<%=path %>/h4/op/venue/svr_kindlist.do?companyId=${companyId}">服务分类管理</a></li>
		<li><a href="<%=path %>/h4/op/venue/svr.do?companyId=${companyId}">服务管理</a></li>
		<li><a href="<%=path %>/h4/op/venue/cmp_updatetime.do?companyId=${companyId}">营业时间设置</a></li>
		<li><a href="<%=path %>/h4/op/venue/cmp_updatesvrrate.do?companyId=${companyId}">用户预约数量</a></li>
	</c:if>
</ul>
</div>
<div class="f_r" style="width: 690px">${mgr_body_content }
</div>
<div class="clr"></div>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>