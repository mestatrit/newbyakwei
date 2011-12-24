<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.cmt"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:data key="view.site.cmt"/></div>
	<c:if test="${fn:length(volist)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="r" items="${volist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }">
				<td class="h0"><img src="${r.cmpComment.user.head32Pic }"/></td>
				<td>
					<hk:a href="http://www.huoku.com/home.do?userId=${r.cmpComment.userId }">${r.cmpComment.user.nickName}</hk:a> 
					<c:if test="${r.cmpComment.userId==loginUser.userId}"><hk:a href="/epp/cmt_del.do?companyId=${companyId}&cmtId=${r.cmpComment.cmtId }"><hk:data key="view.delete"/></hk:a></c:if>
					<br/>
					${r.content } 
					<span class="ruo s"><fmt:formatDate value="${r.cmpComment.createTime}" pattern="yy-MM-dd HH:mm"/></span>
				</td>
			</tr>
			</c:forEach>
		</tbody></table>
	</c:if>
	<c:if test="${fn:length(volist)==0}"><div class="hang even">目前没有留言</div></c:if>
	<hk:simplepage2 href="/epp/cmt_list.do?companyId=${companyId}"/>
	<c:if test="${loginUser!=null}">
		<div class="hang">
			<hk:form action="/epp/cmt_create.do">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:text name="content" clazz="ipt" maxlength="140"/>
				<hk:submit value="提交我的留言"/> 
			</hk:form>
		</div>
	</c:if>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>