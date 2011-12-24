<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
	<c:if test="${fn:length(feedvolist)>0}">
		<ul class="feedlist" id="feedvolist">
			<c:forEach var="vo" items="${feedvolist}">
				<li class="feed">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<c:if test="${feedvo_not_show_head==null || !feedvo_not_show_head}"><td class="head"><a href="<%=path%>/home_web.do?userId=${vo.feed.userId }"><img src="${vo.feed.user.head48Pic }" title="${vo.feed.user.nickName }" /></a></td></c:if>
							<td class="content content-all">${vo.content }</td>
							<td class="time"><span class="ruo"><fmt:formatDate value="${vo.first.createTime}" pattern="yy-MM-dd HH:mm" /></span></td>
						</tr>
					</table>
				</li>
			</c:forEach>
		</ul>
		<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
	</c:if>