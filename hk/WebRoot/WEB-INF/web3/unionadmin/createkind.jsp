<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">分类管理</c:set>
<c:set var="mgr_content" scope="request">
<div>
	在
	<c:forEach var="k" items="${list}">
	<span class="split-r">${k.name }</span>
	</c:forEach>
	中创建子分类
	<div class="text_14">
		<hk:form oid="kindfrm" onsubmit="return subkindfrm(this.id)" action="/cmpunion/op/union_createkind.do" target="hideframe">
			<hk:hide name="uid" value="${uid}"/>
			名称：<hk:text name="name" clazz="text_short_2"/>
			<hk:submit value="创建分类" clazz="btn2"/>
		</hk:form>
		<br/>
	</div>
</div>
<script type="text/javascript">
function subkindfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function addkindsuccess(error,error_msg,respValue){
	refreshurl();
}
function addkinderror(error,error_msg,respValue){
	hideSubmitDiv();
	alert(error_msg);
}
</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>