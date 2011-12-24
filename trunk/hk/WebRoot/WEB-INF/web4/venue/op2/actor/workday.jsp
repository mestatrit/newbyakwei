<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">设置${cmpActor.name }工作日</div>
<div class="mod_content">
	<div>
		<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/h4/op/venue/actor_setworkday.do" target="hideframe">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="actorId" value="${actorId}"/>
			<hk:hide name="ch" value="1"/>
			<div class="divrow">
				<hk:checkbox oid="day_MONDAY" name="day" checkedvalue="${day_2}" value="<%=Calendar.MONDAY %>"/>
				<label for="day_MONDAY">星期一</label>
			</div>
			<div class="divrow">
				<hk:checkbox oid="day_TUESDAY" name="day" checkedvalue="${day_3}" value="<%=Calendar.TUESDAY %>"/>
				<label for="day_TUESDAY">星期二</label>
			</div>
			<div class="divrow">
				<hk:checkbox oid="day_WEDNESDAY" name="day" checkedvalue="${day_4}" value="<%=Calendar.WEDNESDAY %>"/>
				<label for="day_WEDNESDAY">星期三</label>
			</div>
			<div class="divrow">
				<hk:checkbox oid="day_THURSDAY" name="day" checkedvalue="${day_5}" value="<%=Calendar.THURSDAY %>"/>
				<label for="day_THURSDAY">星期四</label>
			</div>
			<div class="divrow">
				<hk:checkbox oid="day_FRIDAY" name="day" checkedvalue="${day_6}" value="<%=Calendar.FRIDAY %>"/>
				<label for="day_FRIDAY">星期五</label>
			</div>
			<div class="divrow">
				<hk:checkbox oid="day_SATURDAY" name="day" checkedvalue="${day_7}" value="<%=Calendar.SATURDAY %>"/>
				<label for="day_SATURDAY">星期六</label>
			</div>
			<div class="divrow">
				<hk:checkbox oid="day_SUNDAY" name="day" checkedvalue="${day_1}" value="<%=Calendar.SUNDAY %>"/>
				<label for="day_SUNDAY">星期日</label>
			</div>
			<div class="divrow">
				<hk:submit clazz="btn split-r" value="提交"/>
				<a href="${denc_return_url }">返回</a>
			</div>
		</form>
	</div>
</div>
</div>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function updateok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../mgr.jsp"></jsp:include>