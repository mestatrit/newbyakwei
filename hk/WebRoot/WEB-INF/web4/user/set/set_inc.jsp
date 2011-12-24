<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<div class="userop">
	<a href="<%=path %>/h4/op/user/set_setinfo.do" <c:if test="${op_func==1}">class="active"</c:if>>个人信息</a>
	<a href="<%=path %>/h4/op/user/set_sethead.do" <c:if test="${op_func==2}">class="active"</c:if>>头像</a>
	<!--
	<a href="<%=path %>/h4/op/user/set_setdomain.do" <c:if test="${op_func==3}">class="active"</c:if>>个性化域名</a> 
	 -->
	<%HkWebUtil.loadAdmin(request); %>
	<c:if test="${userAdmin}">
	<a href="<%=path %>/h4/admin/mgr.do">后台管理</a>
	</c:if>
</div>