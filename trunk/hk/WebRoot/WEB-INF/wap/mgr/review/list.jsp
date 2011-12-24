<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.review"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:data key="view.mgrsite.review"/></div>
	<c:if test="${fn:length(volist)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="r" items="${volist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }">
				<td class="h0"><img src="${r.user.head32Pic }"/></td>
				<td>
					<a href="http://www.huoku.com/home.do?userId=${r.companyReview.userId }">${r.user.nickName}</a> 
					<hk:a href="/epp/mgr/review_del.do?companyId=${companyId}&labaId=${r.companyReview.labaId }"><hk:data key="view.delete"/></hk:a>
					<br/>
					${r.content }
					<c:if test="${r.hasMoreContent}">...</c:if>
					<span class="ruo s"><fmt:formatDate value="${r.companyReview.createTime}" pattern="yy-MM-dd HH:mm"/></span>
				</td>
			</tr>
			</c:forEach>
		</tbody></table>
		<hk:simplepage2 href="/epp/mgr/review_list.do?companyId=${companyId}"/>
	</c:if>
	<div class="hang even"><c:if test="${fn:length(volist)==0}">没有点评数据</c:if></div>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>