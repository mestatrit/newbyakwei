<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
</div>
<div class="row">
${coupon.name }
</div>
<div class="row">
	<form method="post" action="<%=path %>/epp/web/coupon_wapprvinputemail.do">
		<hk:hide name="ch" value="1"/>
		<hk:hide name="companyId" value="${companyId}"/>
		<hk:hide name="navId" value="${navId}"/>
		<hk:hide name="couponId" value="${couponId}"/>
		<hk:data key="epp.coupon.inputemail"/>：<br/>
		<hk:text name="email" clazz="text"/><br/>
		<hk:submit clazz="btn" value="epp.submit" res="true"/>
	</form>
</div>
<div class="row">
<a href="<%=path %>/epp/web/coupon_wapview.do?companyId=${companyId}&navId=${navId}"><hk:data key="epp.return"/></a>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>