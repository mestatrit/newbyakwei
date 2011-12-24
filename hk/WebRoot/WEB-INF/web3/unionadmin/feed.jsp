<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">商家动态</c:set>
<c:set var="mgr_content" scope="request">
<div class="text_14">
	<ul class="orderlist">
		<c:if test="${fn:length(volist)==0}">
			<li>
				<div class="heavy" align="center"><hk:data key="nodatainthispage"/></div>
			</li>
		</c:if>
		<c:if test="${fn:length(volist)>0}">
			<c:forEach var="vo" items="${volist}">
				<li onmouseover="this.className='bg1';" onmouseout="this.className='';">
					<table class="infotable" cellpadding="0" cellspacing="0">
						<tr>
							<td width="600px">${vo.content }</td>
							<td width="100px"><fmt:formatDate value="${vo.first.createTime}" pattern="yy-MM-dd HH:mm"/></td>
						</tr>
					</table>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div>
		<hk:page midcount="10" url="/cmpunion/op/message_feed.do?uid=${uid}"/>
		<div class="clr"></div>
	</div>
</div>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>