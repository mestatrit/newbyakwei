<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">
修改服务分类</div>
<div class="mod_content">
<br/>
	<div>
		<c:set var="form_action" scope="request"><%=path %>/h4/op/venue/svr_updatekind.do</c:set>
		<jsp:include page="kindform.jsp"></jsp:include>
	</div>
</div>
</div>
<script type="text/javascript">
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,msg,v){
	tourl("<%=path %>/h4/op/venue/svr_kindlist.do?companyId=${companyId}");
}
</script>
</c:set><jsp:include page="../mgr.jsp"></jsp:include>