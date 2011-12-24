<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<h1>${cmpNav.name }</h1>
	<div class="mod_content">	
		<div class="hcenter">
			<c:if test="${fn:length(list)==0}">
				<hk:data key="epp.cmpdata.nodatalist"/>
			</c:if>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="contact" items="${list}">
					<div class="divrow fl imgnormal" style="width: 180px;overflow: hidden;">
					${contact.qqhtml }
					<c:if test="${not empty contact.name}">(${contact.name })</c:if>
					</div>
				</c:forEach>
			</c:if>
			<div class="clr"></div>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>