<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.Date"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache"%>
<%String path = request.getContextPath();%>
<c:if test="${out_of_svr_limit}">
每天只能预约${reser_limimt_count }次，所选日期不能继续预约
</c:if>
<c:if test="${!out_of_svr_limit}">
	<c:if test="${cannotwork}"><span class="b">所选时间暂不接受预约</span></c:if>
	<c:if test="${!cannotwork}">
		<div><br class="linefix"/>
			<c:forEach var="tvo" items="${reservetimevolist}" varStatus="idx" end="27">
				<div class="timelist">
					<div class="status">
						<fmt:formatDate var="min" value="${tvo.time}" pattern="mm"/>
						<fmt:formatDate var="hour" value="${tvo.time}" pattern="HH"/>
						<c:if test="${min==00}">
							<div style="width: 30px;height: 20px;"><fmt:formatDate value="${tvo.time}" pattern="H"/></div>
						</c:if>
						<c:if test="${min!=00}">
							<div style="width: 30px;height: 20px;"></div>
						</c:if>
						<div class="clr"></div>
					</div>
					<div class="clr"></div>
				</div>
			</c:forEach>
			<div class="clr"></div>
		</div>
		<div style="margin-bottom: 20px;">
			<c:forEach var="tvo" items="${reservetimevolist}" varStatus="idx" end="27">
				<div class="timelist">
					<div class="status">
						<c:forEach var="vo" items="${tvo.list}">
							<fmt:formatDate var="timestr" value="${vo.beginTime}" pattern="HHmm"/>
							<div id="time_${timestr }" hour="<fmt:formatDate value="${vo.beginTime}" pattern="HH"/>" min="<fmt:formatDate value="${vo.beginTime}" pattern="mm"/>" class="sls <c:if test="${vo.canReserve}">free</c:if><c:if test="${!vo.canReserve}">inuse</c:if> <c:if test="${vo.currentUser}">usersel</c:if>">
								<div id="tiptime_${timestr }" class="flstatus"><fmt:formatDate value="${vo.beginTime}" pattern="HH:mm"/></div>
							</div>
						</c:forEach>
						<div class="clr"></div>
					</div>
					<div class="clr"></div>
				</div>
			</c:forEach>
			<div class="clr"></div>
		</div>
		<div>
			<c:forEach var="tvo" items="${reservetimevolist}" varStatus="idx" begin="28">
				<div class="timelist">
					<div class="status">
						<fmt:formatDate var="min" value="${tvo.time}" pattern="mm"/>
						<fmt:formatDate var="hour" value="${tvo.time}" pattern="HH"/>
						<c:if test="${min==00}">
							<div style="width: 30px;height: 20px;"><fmt:formatDate value="${tvo.time}" pattern="H"/></div>
						</c:if>
						<c:if test="${min!=00}">
							<div style="width: 30px;height: 20px;"></div>
						</c:if>
						<div class="clr"></div>
					</div>
					<div class="clr"></div>
				</div>
			</c:forEach>
			<div class="clr"></div>
		</div>
		<div>
			<c:forEach var="tvo" items="${reservetimevolist}" varStatus="idx" begin="28">
				<div class="timelist">
					<div class="status">
						<c:forEach var="vo" items="${tvo.list}">
							<fmt:formatDate var="timestr" value="${vo.beginTime}" pattern="HHmm"/>
							<div id="time_${timestr }" hour="<fmt:formatDate value="${vo.beginTime}" pattern="HH"/>" min="<fmt:formatDate value="${vo.beginTime}" pattern="mm"/>" class="sls <c:if test="${vo.canReserve}">free</c:if><c:if test="${!vo.canReserve}">inuse</c:if> <c:if test="${vo.currentUser}">usersel</c:if>">
								<div id="tiptime_${timestr }" class="flstatus"><fmt:formatDate value="${vo.beginTime}" pattern="HH:mm"/></div>
							</div>
						</c:forEach>
						<div class="clr"></div>
					</div>
					<div class="clr"></div>
				</div>
			</c:forEach>
			<div class="clr"></div>
		</div>
	</c:if>
</c:if>