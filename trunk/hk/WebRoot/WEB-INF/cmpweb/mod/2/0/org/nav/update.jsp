<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">修改栏目信息</div>
	<div class="mod_content">
		<div>
			<c:set var="cmpnav_form_action" scope="request"><%=path %>/epp/web/org/org_updatenav.do</c:set>
			<jsp:include page="form.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,msg,v){
	tourl('<%=path %>/epp/web/org/org_navlist.do?companyId=${companyId}&orgId=${orgId}');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>