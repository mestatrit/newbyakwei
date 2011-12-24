<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.bean.CompanyKindUtil"%>
<%@page import="com.hk.bean.ParentKind"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%
	List<ParentKind> parentlist = CompanyKindUtil.getParentList();
	request.setAttribute("parentlist", parentlist);
%>
<hk:wap title="分类管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang">点击查看分类</div>
	<c:forEach var="parent" items="${parentlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }"><hk:a href="/e/admin/admincmpkind_cmpkindlist.do?s_parentId=${parent.kindId}">${parent.name }</hk:a></div>
	</c:forEach>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>