<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">
创建图集</div>
<div class="mod_content">
<br/>
	<div>
		<c:set var="form_action" scope="request"><%=path %>/h4/op/venue/photo_createphotoset.do</c:set>
		<jsp:include page="form.jsp"></jsp:include>
	</div>
</div>
</div>
<script type="text/javascript">
function createerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function createok(error,msg,v){
	tourl("<%=path %>/h4/op/venue/photo_photosetlist.do?companyId=${companyId}");
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>