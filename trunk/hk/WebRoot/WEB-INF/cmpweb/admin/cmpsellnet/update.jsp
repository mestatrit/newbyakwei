<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">修改销售网络</div>
		<div class="mod_content">
		<br/>
			<div>
				<c:set var="form_action" scope="request"><%=path %>/epp/web/op/webadmin/cmpsellnet_update.do</c:set>
				<jsp:include page="form.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,msg,v){
	tourl("<%=path %>/epp/web/op/webadmin/cmpsellnet.do?companyId=${companyId}&navoid=${navoid }");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>