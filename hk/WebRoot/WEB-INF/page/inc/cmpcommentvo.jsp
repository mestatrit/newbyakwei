<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${fn:length(cmtvolist)>0}">
<c:if test="${empty odd}"><c:set var="odd" value="odd"/></c:if>
<c:if test="${empty even}"><c:set var="even" value="even"/></c:if>
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<c:forEach var="vo" items="${cmtvolist}" varStatus="idx">
		<c:set var="hk_request_css"><hk:iteratorcss css1="${odd}" css2="${even}"/></c:set>
		<tr class="${hk_request_css }">
			<c:if test="${!noneedhead}">
			<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.cmpComment.user.head32Pic }"/></td></c:if>
			<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.cmpComment.user.head48Pic }"/></td></c:if>
			</c:if>
			<td>
				<a href="/home.do?userId=${vo.cmpComment.userId }">${vo.cmpComment.user.nickName}</a>
				<c:if test="${loginUser.userId==vo.cmpComment.userId}">
					<hk:a page="true" href="/cmt/op/op_delcmt.do?cmtId=${vo.cmpComment.cmtId}&${addcmtstr }"><hk:data key="view.delete"/></hk:a>   
				</c:if>
				<br/>
				${vo.content } 
				<span class="ruo s"><fmt:formatDate value="${vo.cmpComment.createTime}" pattern="yyyy-MM-dd"/></span> 
			</td>
		</tr>
	</c:forEach>
	</tbody></table>
</c:if>