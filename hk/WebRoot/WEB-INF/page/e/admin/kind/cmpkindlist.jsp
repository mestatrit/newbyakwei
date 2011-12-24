<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.bean.CompanyKindUtil"%>
<%@page import="com.hk.bean.ParentKind"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%List<ParentKind> parentlist=CompanyKindUtil.getParentList();
request.setAttribute("parentlist",parentlist);
%>
<hk:wap title="${parentKind.name} - 分类管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang">${parentKind.name} <hk:a href="/e/admin/admincmpkind_toaddcmpkind.do?s_parentId=${s_parentId}">创建分类</hk:a> </div>
	<div class="hang">
		<hk:form method="get" action="/e/admin/admincmpkind_cmpkindlist.do">
			<hk:select name="s_parentId" checkedvalue="${s_parentId}">
				<c:forEach var="parent" items="${parentlist}">
					<hk:option value="${parent.kindId}" data="${parent.name}" />
				</c:forEach>
			</hk:select>
			<hk:submit value="查询" />
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="k" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				${k.name}
				<hk:a href="/e/admin/admincmpkind_toeditcmpkind.do?s_parentId=${s_parentId}&kindId=${k.kindId }">改</hk:a>
				<hk:a href="/e/admin/admincmpkind_delcmpkind.do?s_parentId=${s_parentId}&kindId=${k.kindId }">删</hk:a>
				<hk:a href="/e/admin/admincmpkind_cmpchildkindlist.do?kindId=${k.kindId}&s_parentId=${s_parentId}">子分类</hk:a>
				<hk:a href="/e/admin/admin_tlist.do?kindId=${k.kindId}&s_parentId=${s_parentId}">标签</hk:a>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(list)==0}">没有记录</c:if>
	<div class="hang">
		<hk:a href="/e/admin/admincmpkind.do">返回</hk:a>
	</div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>