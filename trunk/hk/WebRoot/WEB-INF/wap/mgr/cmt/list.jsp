<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.cmt"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:data key="view.mgrsite.cmt"/></div>
	<c:if test="${fn:length(volist)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="r" items="${volist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }">
				<td class="h0"><img src="${r.cmpComment.user.head32Pic }"/></td>
				<td>
					<a href="http://www.huoku.com/home.do?userId=${r.cmpComment.userId }">${r.cmpComment.user.nickName}</a> 
					<hk:a href="/epp/mgr/cmt_del.do?companyId=${companyId}&cmtId=${r.cmpComment.cmtId }"><hk:data key="view.delete"/></hk:a><br/>
					${r.content } 
					<span class="ruo s"><fmt:formatDate value="${r.cmpComment.createTime}" pattern="yy-MM-dd HH:mm"/></span>
				</td>
			</tr>
			</c:forEach>
		</tbody></table>
	</c:if>
	<div class="hang even"><c:if test="${fn:length(volist)==0}">没有留言数据</c:if></div>
	<hk:simplepage2 href="/epp/mgr/cmt_list.do?companyId=${companyId}"/>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>