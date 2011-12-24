<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_body_content" scope="request">
	<div class="mod">
		<div class="mod_title">创建分类
		<a class="more" href="${ctx_path }/tb/admin/askcat?parent_cid=${parent_cid}">返回</a>
		</div>
		<div class="mod_content">
		<c:set var="form_action" scope="request">${ctx_path }/tb/admin/askcat_create</c:set>
		<jsp:include page="form.jsp"></jsp:include>
		</div>
	</div>
<script type="text/javascript">
function createerr(err,msg,v){
	setHtml('_info',msg);
	hideGlass();
}
function createok(err,msg,v){
	tourl('${ctx_path}/tb/admin/askcat?parent_cid=${parent_cid}');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>