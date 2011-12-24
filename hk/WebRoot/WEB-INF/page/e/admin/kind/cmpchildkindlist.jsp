<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.bean.CompanyKindUtil"%>
<%@page import="com.hk.bean.ParentKind"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%List<ParentKind> parentlist=CompanyKindUtil.getParentList();
request.setAttribute("parentlist",parentlist);
%>
<hk:wap title="${companyKind.name} - 分类管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang">${companyKind.name} <hk:a href="/e/admin/admincmpkind_toaddcmpchildkind.do?s_parentId=${s_parentId}&kindId=${kindId}">创建小分类</hk:a> </div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="k" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				${k.name}
				<hk:a href="/e/admin/admincmpkind_toeditcmpchildkind.do?kindId=${kindId}&oid=${k.oid }&s_parentId=${s_parentId }">改</hk:a>
				<hk:a href="/e/admin/admincmpkind_delcmpchildkind.do?kindId=${kindId}&oid=${k.oid }&s_parentId=${s_parentId }">删</hk:a>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(list)==0}">没有记录</c:if>
	<div class="hang"><hk:a href="/e/admin/admincmpkind_cmpkindlist.do?s_parentId=${s_parentId }">返回</hk:a></div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>