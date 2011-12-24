<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache"%>
<%String path = request.getContextPath();%>
<c:set scope="request" var="html_body_content">
<div class="tpad">
	<ul class="actorlist">
		<c:forEach var="actor" items="${list}">
			<li>
				<div class="head">
					<c:if test="${not empty actor.picPath}">
					<a href="/actor/${companyId }/${actor.actorId }/"><img src="${actor.pic240Url }"/></a>
					</c:if>
				</div>
				<div class="info">
					<a href="/actor/${companyId }/${actor.actorId }/" class="b">${actor.name }</a><br/>
					${actor.intro }
				</div>
				<div class="clr"></div>
			</li>
		</c:forEach>
	</ul>
	<div>
		<c:set var="page_url" scope="request">/actors/${companyId}/${navId}</c:set>
		<c:set var="url_rewrite" scope="request"  value="true"/>
		<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>