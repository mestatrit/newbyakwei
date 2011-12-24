<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.review"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">${o.name }的<hk:data key="view.site.review"/></div>
	<c:if test="${fn:length(volist)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="r" items="${volist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }">
				<td class="h0"><img src="${r.user.head32Pic }"/></td>
				<td>
					<hk:a href="http://www.huoku.com/home.do?userId=${r.companyReview.userId }">${r.user.nickName}</hk:a> 
					<c:if test="${r.companyReview.userId==loginUser.userId}"><hk:a href="/epp/review_del.do?companyId=${companyId}&labaId=${r.companyReview.labaId }"><hk:data key="view.delete"/></hk:a></c:if>
					<c:if test="${r.companyReview.userId==loginUser.userId}"><hk:a href="/epp/review_toupdate.do?companyId=${companyId}&labaId=${r.companyReview.labaId }"><hk:data key="view.update"/></hk:a></c:if>
					<br/>
					${r.content }
					<c:if test="${r.hasMoreContent}"><hk:a href="/epp/laba_tore.do?labaId=${r.companyReview.labaId }">...</hk:a></c:if>
					<span class="ruo s"><fmt:formatDate value="${r.companyReview.createTime}" pattern="yy-MM-dd HH:mm"/></span>
				</td>
			</tr>
			</c:forEach>
		</tbody></table>
	</c:if>
	<c:if test="${fn:length(volist)==0}"><div class="hang even">目前没有点评</div></c:if>
	<c:if test="${fn:length(volist)>0}">
		<hk:simplepage2 href="/epp/review_list.do?companyId=${companyId}"/>
	</c:if>
	<c:if test="${loginUser!=null && companyReview==null}">
		<div class="hang even" onkeydown="submitLaba(event)">
			<c:set var="review_form_action" value="/review_create.do" scope="request"/>
			<jsp:include page="reviewform.jsp"></jsp:include>
		</div>
	</c:if>
	<c:if test="${loginUser!=null && companyReview!=null}">
		<div class="hang even"><hk:a href="/epp/review_tocreate.do?companyId=${companyId}">写新评论</hk:a></div>
	</c:if>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>