<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="js_value" scope="request">
<link type="text/css" href="<%=path%>/webst4/css/smoothness/jquery-ui-1.7.custom.css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/webst4/js/jquery-ui-1.7.custom.min.js"></script>
<script type="text/javascript" src="<%=path%>/webst4/js/ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(function(){
		$('.datepicker').datepicker({
			numberOfMonths: 1,
			showButtonPanel: false,
			dateFormat: 'yy-mm-dd'
		});
	});
});
</script>
</c:set>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">申请假期</div>
<div class="mod_content">
<br/>
	<div>
	<c:set var="form_action" scope="request"><%=path %>/h4/op/venue/actor_updateactorsptime.do</c:set>
	<jsp:include page="actorsptimefrom.jsp"></jsp:include>
	</div>
</div>
</div>
<script type="text/javascript">
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,msg,v){
	tourl("<%=path %>/h4/op/venue/actor_sptimelist.do?companyId=${companyId}&actorId=${actorId}");
}
</script>
</c:set><jsp:include page="../mgr.jsp"></jsp:include>