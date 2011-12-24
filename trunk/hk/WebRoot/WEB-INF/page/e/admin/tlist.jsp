<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.admin.tlist.title"/></c:set>
<hk:wap title="${companyKind.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">${companyKind.name} <hk:a href="/e/admin/admin_toaddtag.do?s_parentId=${s_parentId}&kindId=${kindId }">创建标签</hk:a></div>
	<c:if test="${fn:length(tlist)>0}">
		<c:set var="addstr" value="kindId=${kindId}&s_parentId=${s_parentId}"></c:set>
		<c:forEach var="t" items="${tlist}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
			${t.name } 
			${t.kindData } 
			<hk:a href="/e/admin/admin_tocfmdeltag.do?tid=${t.tagId}&${addstr }" page="true">删</hk:a>
			<hk:a href="/e/admin/admin_toedittag.do?tid=${t.tagId}&${addstr }" page="true">改</hk:a>
			</div>
		</c:forEach>
		<hk:simplepage href="/e/admin/admin_tlist.do?${addstr}"/>
	</c:if>
	<c:if test="${fn:length(tlist)==0}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/e/admin/admincmpkind_cmpkindlist.do?s_parentId=${s_parentId}">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>