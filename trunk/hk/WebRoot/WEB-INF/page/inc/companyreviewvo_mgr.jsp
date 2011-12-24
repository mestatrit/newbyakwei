<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CompanyReview"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${fn:length(reviewvolist)>0}">
<c:if test="${empty odd}"><c:set var="odd" value="odd"/></c:if>
<c:if test="${empty even}"><c:set var="even" value="even"/></c:if>
<c:set var="normal" value="<%=CompanyReview.CHECKFLG_NORMAL %>"></c:set>
<c:set var="dissentious" value="<%=CompanyReview.CHECKFLG_DISSENTIOUS %>"></c:set>
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
					<c:if test="${vo.companyReview.normal}">
						<hk:a href="/e/op/auth/op_chgcheckflg.do?companyId=${companyId}&labaId=${vo.companyReview.labaId }&flg=${dissentious }&checkflg=${checkflg }"><hk:data key="view.delete"/></hk:a>
					</c:if>
					<c:if test="${!vo.companyReview.normal}">
						<hk:a href="/e/op/auth/op_chgcheckflg.do?companyId=${companyId}&labaId=${vo.companyReview.labaId }&flg=${normal }&checkflg=${checkflg }"><hk:data key="view.company.setcheckflg_normal"/></hk:a>
					</c:if>
				</div>
				<div>
					${vo.content }<c:if test="${not empty vo.companyReview.longContent}"><hk:a clazz="line" href="/laba/laba.do?labaId=${vo.companyReview.labaId}">...</hk:a></c:if> 
					<span class="ruo s"><fmt:formatDate value="${vo.companyReview.createTime}" pattern="yyyy-MM-dd"/></span> 
				</div>
			</td>
		</tr>
	</c:forEach>
	</tbody></table>
</c:if>