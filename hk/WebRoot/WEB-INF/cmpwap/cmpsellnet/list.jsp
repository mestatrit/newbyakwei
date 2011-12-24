<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
</div>
<c:if test="${fn:length(list)==0}">
<div class="row ${clazz_var }">
<hk:data key="nodatalist"/>
</div>
</c:if>
<c:if test="${fn:length(list)>0}">
	<c:forEach var="sellnet" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
	<div class="row ${clazz_var }">
		${sellnet.name }<br/>
		<c:if test="${not empty sellnet.tel}">${sellnet.tel}<br/></c:if>
		<c:if test="${not empty sellnet.addr}">${sellnet.addr}</c:if>
	</div>
	</c:forEach>
</c:if>
<div class="row">
	<hk:simplepage2 href="/epp/web/cmpsellnet_wap.do?companyId=${companyId}&navId=${navId}"/>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>