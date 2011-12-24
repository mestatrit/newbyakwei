<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpUserTableField"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><c:set var="html_title" scope="request">${o.name}</c:set><c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">自定义表格</div>
			<div class="mod_content">
				<div>
					<c:set var="form_action" scope="request"><%=path %>/epp/web/op/webadmin/cmpusertable_updateusertablefield.do</c:set>
					<jsp:include page="usertabledata_form.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function updateerr(error,err_msg,v){
	setHtml("_info",err_msg);
	submited=false;
	removeGlass(glassid);
}
function updateok(error,err_msg,v){
	tourl("<%=path %>/epp/web/op/webadmin/cmpusertable.do?companyId=${companyId}&navoid=${navoid}");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>