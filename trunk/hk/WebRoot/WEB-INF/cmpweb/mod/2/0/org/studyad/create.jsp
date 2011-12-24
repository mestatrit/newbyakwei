<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<link type="text/css" href="<%=path %>/cmpwebst4/mod/pub/css/smoothness/jquery-ui-1.7.custom.css" rel="stylesheet" />
<script type="text/javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery-ui-1.7.custom.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(function(){
		$('.beginTime,.availableTime').datepicker({
			numberOfMonths: 1,
			showButtonPanel: true,
			dateFormat: 'yy-mm-dd'
		});
	});
});
</script>
</c:set>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">${cmpOrgNav.name } 发布招生简章
		<a class="more" href="/edu/${companyId }/${orgId}/column/${orgnavId}">返回</a>
	</div>
	<div class="mod_content">
		<div>
			<c:set var="cmpnav_form_action" scope="request"><%=path %>/epp/web/org/studyad_create.do</c:set>
			<jsp:include page="form.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
function createerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function createok(error,msg,v){
	tourl("/edu/${companyId}/${orgId}/zhaosheng/${orgnavId}/"+v+".html");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>