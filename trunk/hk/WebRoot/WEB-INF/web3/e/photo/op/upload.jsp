<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.uploadimage"/></c:set>
<c:set var="body_hk_content" scope="request">
<style>
body{background:#DEDFE1;}
</style>
<div class="mod-8 text_14">
	<%=Hkcss2Util.rd_bg %>
	<div class="cont">
		<div style="padding: 20px;">
			<h2><hk:data key="view.uploadimage"/></h2>
			<div class="bdbtm"></div>
			<hk:form oid="uploadfrm" enctype="multipart/form-data" onsubmit="return subupload(this.id)"  action="/op/uploadcmpphoto_upload.do"  target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<%for(int i=0;i<6;i++){ %>
				<input type="file" name="f<%=i %>"  size="50" class="fileipt"/><br/>
				<%} %>
				<hk:submit value="view.upload" res="true" clazz="btn"/> 
				<c:if test="${!mgr}">
				<a href="<%=path %>/cmp.do?companyId=${companyId }"><hk:data key="view.return"/></a>
				</c:if>
				<c:if test="${mgr}">
				<a href="<%=path%>/e/op/op.do?companyId=${companyId }"><hk:data key="view.return"/></a>
				</c:if>
			</hk:form>
			<hk:form oid="photofrm" action="/op/uploadcmpphoto_toedit.do">
				<hk:hide name="companyId" value="${companyId}"/>
				<div id="photocon"></div>
			</hk:form>
		</div>
	</div>
	<%=Hkcss2Util.rd_bg_bottom %>
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
<jsp:include page="../../../inc/frame.jsp"></jsp:include>