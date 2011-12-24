<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.bean.CompanyKindUtil"%>
<%@page import="com.hk.bean.ParentKind"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%List<ParentKind> parentlist=CompanyKindUtil.getParentList();
request.setAttribute("parentlist",parentlist);
%>
<hk:wap title="确认删除 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang">
	<hk:form action="/e/admin/admincmpkind_delcmpchildkind.do">
		<hk:hide name="cfm" value="1"/>
		<hk:hide name="oid" value="${oid}"/>
		<hk:hide name="kindId" value="${kindId}"/>
		<hk:hide name="s_parentId" value="${s_parentId}"/>
		确定要删除此分类?<br/>
		<hk:submit name="ok" value="确定"/> 
		<hk:submit name="cancel" value="取消"/>
	</hk:form>
	</div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>