<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">
创建服务
<a class="more" href="<%=path %>/h4/op/venue/svr.do?companyId=${companyId}">返回</a>
</div>
<div class="mod_content">
<br/>
	<div>
		<c:set var="form_action" scope="request"><%=path %>/h4/op/venue/svr_create.do</c:set>
		<jsp:include page="form.jsp"></jsp:include>
	</div>
</div>
</div>
<script type="text/javascript">
function createerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function createok(error,msg,v){
	tourl("<%=path %>/h4/op/venue/svr.do?companyId=${companyId}");
}
</script>
</c:set><jsp:include page="../mgr.jsp"></jsp:include>