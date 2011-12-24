<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${fn:length(companyvolist)>0}">
	<c:if test="${empty odd}"><c:set var="odd" value="odd"/></c:if>
	<c:if test="${empty even}"><c:set var="even" value="even"/></c:if>
	<c:forEach var="vo" items="${companyvolist}" varStatus="idx">
		<c:set var="hk_request_css"><hk:iteratorcss css1="${odd}" css2="${even}"/></c:set>
		<div class="hang ${hk_request_css }">
			<hk:a href="/e/cmp.do?companyId=${vo.company.companyId}">${vo.company.name}</hk:a>
		</div>
	</c:forEach>
</c:if>