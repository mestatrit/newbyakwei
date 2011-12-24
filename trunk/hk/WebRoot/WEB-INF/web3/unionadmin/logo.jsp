<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request">修改logo</c:set>
<c:set var="mgr_content" scope="request">
<div>
<div>
<c:if test="${not empty o.logo}">
	<div class="14">
		<strong>现在的logo</strong>
	</div>
	<span class="split-r">
		<img src="${o.logo48Pic }?v=<%=Math.random() %>"/>
	</span>
	<span class="split-r">
		<img src="${o.logo80Pic }?v=<%=Math.random() %>"/>
	</span>
</c:if>
</div>
<hk:form oid="editfrm" enctype="multipart/form-data" onsubmit="return subuploadfrm(this.id)" action="/cmpunion/op/union_uploadlogo.do" target="hideframe">
	<hk:hide name="uid" value="${uid}"/>
	<input type="file" name="f" size="50" class="fileipt"/>
	<hk:submit clazz="btn" value="上传"/>
</hk:form>
</div>
<script type="text/javascript">
function subuploadfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function uploadok(error,error_msg,respValue){
	refreshurl();
}
function uploaderror(error,error_msg,respValue){
	hideSubmitDiv();
	alert(error_msg);
}
</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>