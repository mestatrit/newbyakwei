<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache"%>
<%String path = request.getContextPath();%>
<c:if test="${cannotwork}"><span class="b">所选时间暂不接受预约</span></c:if>
<c:if test="${!cannotwork}">
	<c:forEach var="tvo" items="${reservetimevolist}">
		<div class="timelist">
			<div class="status">
				<c:forEach var="vo" items="${tvo.list}">
					<fmt:formatDate var="timestr" value="${vo.beginTime}" pattern="HHmm"/>
					<div id="time_${timestr }" hour="<fmt:formatDate value="${vo.beginTime}" pattern="HH"/>" min="<fmt:formatDate value="${vo.beginTime}" pattern="mm"/>" class="sls <c:if test="${vo.canReserve}">free</c:if><c:if test="${!vo.canReserve}">inuse</c:if>">
						<div id="tiptime_${timestr }" class="flstatus"><fmt:formatDate value="${vo.beginTime}" pattern="HH:mm"/></div>
					</div>
				</c:forEach>
				<div class="clr"></div>
			</div>
			<div class="clr"></div>
		</div>
	</c:forEach>
</c:if>