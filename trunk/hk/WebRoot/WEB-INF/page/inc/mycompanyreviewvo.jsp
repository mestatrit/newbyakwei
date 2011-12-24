<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${fn:length(myreviewvolist)>0}">
<c:if test="${empty odd}"><c:set var="odd" value="odd"/></c:if>
<c:if test="${empty even}"><c:set var="even" value="even"/></c:if>
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<c:forEach var="vo" items="${myreviewvolist}" varStatus="idx">
		<c:set var="hk_request_css"><hk:iteratorcss css1="${odd}" css2="${even}"/></c:set>
		<tr class="${hk_request_css }">
			<c:if test="${!noneedhead}">
			<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.user.head32Pic }"/></td></c:if>
			<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.user.head48Pic }"/></td></c:if>
			</c:if>
			<td>
				<div>
					<a href="/home.do?userId=${vo.user.userId }">${vo.user.nickName}</a> 
					<hk:rmBlankLines rm="true">
						<hk:a href="/review/op/op_toedit.do?labaId=${lastLabaId}&companyId=${companyId}"><hk:data key="view.edit"/></hk:a>|
						<hk:a href="/review/op/op_toadd.do?companyId=${companyId}"><hk:data key="view.companyreview.createnewreview"/></hk:a>
					</hk:rmBlankLines>
				</div>
				${vo.content }<c:if test="${not empty vo.companyReview.longContent}"><hk:a clazz="line" href="/laba/laba.do?labaId=${vo.companyReview.labaId}&${addlabastr }">...</hk:a></c:if> 
				<span class="ruo s"><fmt:formatDate value="${vo.companyReview.createTime}" pattern="yyyy-MM-dd"/></span> 
			</td>
		</tr>
	</c:forEach>
	</tbody></table>
</c:if>
<c:if test="${fn:length(myreviewvolist)==0 && companyUserScore!=null}">
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
		<c:set var="hk_request_css"><hk:iteratorcss css1="${odd}" css2="${even}"/></c:set>
		<tr class="${hk_request_css }">
			<c:if test="${!noneedhead}">
			<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${loginUser.head32Pic }"/></td></c:if>
			<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${loginUser.head48Pic }"/></td></c:if>
			</c:if>
			<td>
				<div>
					<a href="/home.do?userId=${loginUser.userId }">${loginUser.nickName}</a> 
					<hk:a href="/review/op/op_toadd.do?companyId=${companyId}"><hk:data key="view.companyreview.createnewreview"/></hk:a>
				</div>
				<div>
					<img src="<%=request.getContextPath() %>/st/img/st${vo.starRate }.gif"/><hk:a href="/review/op/op_toaddscore.do?companyId=${companyId}"><hk:data key="view.edit"/></hk:a>
				</div>
			</td>
		</tr>
	</tbody></table>
</c:if>