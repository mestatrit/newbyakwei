<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="reflabaId"><%=request.getParameter("reflabaId") %></c:set>
<c:set var="ref"><%=request.getParameter("ref") %></c:set>
<c:if test="${fn:length(labavolist)>0}">
<c:if test="${empty odd}"><c:set var="odd" value="odd"/></c:if>
<c:if test="${empty even}"><c:set var="even" value="even"/></c:if>
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<c:forEach var="vo" items="${labavolist}" varStatus="idx">
		<hk:timeline date="${vo.laba.createTime}" group="true"/>
		<c:if test="${hk_request_time_show}">
			<c:set var="hk_request_css"><hk:iteratorcss css1="${odd}" css2="${even}"/></c:set>
			<c:if test="${vo.replyMe}"><c:set var="hk_request_css" value="reply" /></c:if>
			<tr class="${hk_request_css }"><td colspan="2"><strong class="ruo s"><hk:fmttime value="${hk_request_time }" pattern="yyyy/MM/dd"/></strong></td></tr>
		</c:if>
		<c:set var="hk_request_css"><hk:iteratorcss css1="${odd}" css2="${even}"/></c:set>
		<c:if test="${vo.replyMe}"><c:set var="hk_request_css" value="reply" /></c:if>
		<tr class="${hk_request_css }">
			<c:if test="${!noneedhead}">
			<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.laba.user.head32Pic }"/></td></c:if>
			<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.laba.user.head48Pic }"/></td></c:if>
			</c:if>
			<td>
				<c:set var="replyurl" value="/laba/laba.do?labaId=${vo.laba.labaId}&sip=1&${addlabastr }"/>
				<c:set var="replyurl2" value="/laba/laba.do?labaId=${vo.laba.labaId}&${addlabastr }"/>
				<hk:a href="/home.do?userId=${vo.laba.userId}">${vo.laba.user.nickName}</hk:a> <c:if test="${!showMode.showImg}">&nbsp;</c:if>
				<c:if test="${vo.fav}"><hk:a name="laba" nameadd="${vo.laba.labaId}" page="true" href="/laba/op/op_delfav.do?labaId=${vo.laba.labaId}&${addlabastr }"><%=HkWebUtil.getDelFavLabel(request) %></hk:a></c:if>
				<c:if test="${!vo.fav}"><hk:a name="laba" nameadd="${vo.laba.labaId}" page="true" href="/laba/op/op_fav.do?labaId=${vo.laba.labaId}&${addlabastr }"><%=HkWebUtil.getFavLabel(request) %></hk:a></c:if>
				<c:if test="${loginUser.userId!=vo.laba.userId}">
					<c:if test="${ref==1 && vo.laba.labaId==reflabaId}"><a href="#"><%=HkWebUtil.getRefLabel2(request) %></a></c:if>
					<c:if test="${ref!=1 || (ref==1 && reflabaId!=vo.laba.labaId)}"><hk:a name="laba${vo.laba.labaId}" page="true" href="/laba/op/op_share.do?labaId=${vo.laba.labaId}&ref=1&${addlabastr }"><%=HkWebUtil.getRefLabel(request) %></hk:a></c:if>
				</c:if>
				<c:if test="${loginUser.userId==vo.laba.userId}">
					<hk:a page="true" clazz="line" href="/laba/op/op_del.do?labaId=${vo.laba.labaId}&${addlabastr }"><%=HkWebUtil.getDelLabel(request) %></hk:a> 
				</c:if>
				<c:if test="${loginUser.userId!=vo.laba.userId}">
					<hk:a clazz="line" href="${replyurl}">回应</hk:a> 
				</c:if>
				<br/>
				<c:set var="innerhtml"><c:if test="${not empty vo.laba.longContent}"><hk:a clazz="line" href="${replyurl2}">...</hk:a></c:if> <hk:a clazz="tml" href="${replyurl2}"><c:if test="${vo.laba.replyCount>0}">${vo.laba.replyCount}评论</c:if> <hk:time value="${vo.laba.createTime}" onlytoday="true"/></hk:a></c:set>
				<c:if test="${not empty vo.mainContent}"><div>${vo.mainContent } ${innerhtml}</div><div class="refcon">${vo.refContent }</div></c:if> 
				<c:if test="${empty vo.mainContent}">${vo.content }	${innerhtml}</c:if> 
			</td>
		</tr>
	</c:forEach>
	<c:remove var="hk_request_css" scope="request"/>
	</tbody></table>
</c:if>