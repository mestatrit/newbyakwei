<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${fn:length(impressionvolist)>0}">
<c:if test="${empty odd}"><c:set var="odd" value="odd"/></c:if>
<c:if test="${empty even}"><c:set var="even" value="even"/></c:if>
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<c:forEach var="vo" items="${impressionvolist}" varStatus="idx">
		<c:set var="hk_request_css"><hk:iteratorcss css1="${odd}" css2="${even}"/></c:set>
		<tr class="${hk_request_css }">
			<c:if test="${!noneedhead}">
			<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.impression.sender.head32Pic }"/></td></c:if>
			<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.impression.sender.head48Pic }"/></td></c:if>
			</c:if>
			<td>
				<a href="/home.do?userId=${vo.impression.sender.userId }">${vo.impression.sender.nickName}</a>
				<c:if test="${loginUser.userId==vo.impression.senderId}">
					<hk:a page="true" href="/impression/op/op_del.do?oid=${vo.impression.oid}&${impression_addstr }"><hk:data key="view.delete"/></hk:a>   
				</c:if>
				<br/>
				${vo.content } 
				<span class="ruo s"><fmt:formatDate value="${vo.impression.createTime}" pattern="yyyy-MM-dd"/></span> 
			</td>
		</tr>
	</c:forEach>
	</tbody></table>
</c:if>