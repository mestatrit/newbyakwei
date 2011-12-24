<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="js_value" scope="request">
<link type="text/css" href="<%=path%>/cmpwebst4/css/smoothness/jquery-ui-1.7.custom.css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/cmpwebst4/js/jquery-ui-1.7.custom.min.js"></script>
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
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">申请假期</div>
		<div class="mod_content">
		<br/>
			<div>
			<c:set var="form_action" scope="request"><%=path %>/epp/web/op/webadmin/actor_createactorsptime.do</c:set>
			<jsp:include page="actorsptimefrom.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function createerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function createok(error,msg,v){
	tourl("<%=path %>/epp/web/op/webadmin/actor_sptimelist.do?companyId=${companyId}&actorId=${actorId}");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>