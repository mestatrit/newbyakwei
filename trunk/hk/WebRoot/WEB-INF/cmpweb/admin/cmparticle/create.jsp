<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpNav"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">${cmpNav.name } 发布文章
			<c:if test="${cmpNav.articleList}">
				<a class="more" href="<%=path %>/epp/web/op/webadmin/cmparticle.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</c:if>
		</div>
		<div class="mod_content">
		<c:if test="${cmpNav.hasApplyForm}">
			<div class="divrow">
				<a href="<%=path %>/epp/web/op/webadmin/cmpjoininapply.do?companyId=${companyId}&navoid=${cmpNav.oid}&readed=<%=CmpJoinInApply.READED_N %>">申请管理</a>
			</div>
		</c:if>
			<div>
				<c:set var="cmpnav_form_action" scope="request"><%=path %>/epp/web/op/webadmin/cmparticle_create.do</c:set>
				<jsp:include page="form.jsp"></jsp:include>
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
	tourl("<%=path %>/epp/web/op/webadmin/cmparticle_view.do?companyId=${companyId}&navoid=${navoid}&oid="+v);
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>