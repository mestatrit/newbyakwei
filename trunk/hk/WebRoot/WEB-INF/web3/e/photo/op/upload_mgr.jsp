<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request"><hk:data key="view.uploadimage"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
	<hk:form oid="uploadfrm" enctype="multipart/form-data" onsubmit="return subupload(this.id)" action="/e/op/photo/photo_upload.do" target="hideframe">
		<hk:hide name="companyId" value="${companyId}"/>
		<%for(int i=0;i<6;i++){ %>
		<input type="file" name="f<%=i %>" size="50" class="fileipt"/><br/>
		<%} %>
		<hk:submit value="view.upload" res="true" clazz="btn"/> 
	</hk:form>
	<hk:form oid="photofrm" action="/e/op/photo/photo_mgrtoedit.do">
		<hk:hide name="companyId" value="${companyId}"/>
		<div id="photocon"></div>
	</hk:form>
</div>
<script type="text/javascript">
function subupload(frmid){
	showSubmitDiv(frmid);
	return true;
}
function afterSuccess(value,op_func){
	var pid=value.split(',');
	var s="";
	for(var i=0;i<pid.length;i++){
		s+='<hk:hide name="pid" value="'+pid[i]+'"/>';
	}
	setHtml("photocon",s);
	getObj('photofrm').submit();
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>