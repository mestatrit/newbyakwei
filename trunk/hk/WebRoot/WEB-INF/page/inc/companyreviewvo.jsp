<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${fn:length(reviewvolist)>0}">
<c:if test="${empty odd}"><c:set var="odd" value="odd"/></c:if>
<c:if test="${empty even}"><c:set var="even" value="even"/></c:if>
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<c:forEach var="vo" items="${reviewvolist}" varStatus="idx">
		<c:set var="hk_request_css"><hk:iteratorcss css1="${odd}" css2="${even}"/></c:set>
		<tr class="${hk_request_css }">
			<c:if test="${!noneedhead}">
			<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.user.head32Pic }"/></td></c:if>
			<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.user.head48Pic }"/></td></c:if>
			</c:if>
			<td>
				<div>
					<a href="/home.do?userId=${vo.user.userId }">${vo.user.nickName}</a> 
					<c:if test="${loginUser.userId!=vo.companyReview.userId}"><hk:a name="review${vo.companyReview.labaId}" page="true" clazz="line" href="/laba/laba.do?labaId=${vo.companyReview.labaId}&${addlabastr }"><hk:data key="view.response"/></hk:a></c:if>
					<c:if test="${loginUser.userId==vo.companyReview.userId}"><hk:a page="true" href="/review/op/op_toedit.do?labaId=${vo.companyReview.labaId}&${addlabastr }"><hk:data key="view.edit"/></hk:a></c:if>
				</div>
				<div>
					${vo.content }<c:if test="${not empty vo.companyReview.longContent}"><hk:a clazz="line" href="/laba/laba.do?labaId=${vo.companyReview.labaId}&${addlabastr }">...</hk:a></c:if> 
					<span class="ruo s"><fmt:formatDate value="${vo.companyReview.createTime}" pattern="yyyy-MM-dd"/></span> 
				</div>
			</td>
		</tr>
	</c:forEach>
	</tbody></table>
</c:if>