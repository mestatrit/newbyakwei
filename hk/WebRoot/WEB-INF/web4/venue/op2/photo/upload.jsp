<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">图片列表
	</div>
	<div class="mod_content">
		<div style="width: 400px">
			<br/><a name="upload"></a>
			<h2>上传图片</h2>
			<form id="uppicfrm" onsubmit="return subuppicfrm(this.id)" method="post" enctype="multipart/form-data" action="<%=path %>/h4/op/venue/photo_upload.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/> 
				<hk:hide name="ch" value="1"/> 
				<c:forEach var="b" begin="0" end="4">
					<div class="divrow">
						<input type="file" name="f${b }" size="50"/>
					</div>
				</c:forEach>
				<div class="inforwarn" id="uploadmsg"></div>
				<div align="right">
				<hk:submit value="上传" clazz="btn split-r"/>
				<a href="<%=path%>/h4/op/venue/photo.do?companyId=${companyId}">返回</a>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
function subuppicfrm(frmid){
	setHtml('uploadmsg','');
	showGlass(frmid);
	return true;
}
function uploaderror(error,msg,v){
	setHtml('uploadmsg',msg);
	hideGlass();
}
function uploadok(error,msg,v){
	tourl('<%=path%>/h4/op/venue/photo.do?companyId=${companyId}');
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>