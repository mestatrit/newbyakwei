<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">${cmpNav.name } 修改组
			<a class="more" href="<%=path %>/epp/web/op/webadmin/cmparticle_grouplist.do?companyId=${companyId}&navoid=${navoid }">返回</a>
		</div>
		<div class="mod_content">
			<div>
				<c:set var="cmpnav_form_action" scope="request"><%=path %>/epp/web/op/webadmin/cmparticle_updategroup.do</c:set>
				<jsp:include page="groupform.jsp"></jsp:include>
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
	tourl("<%=path %>/epp/web/op/webadmin/cmparticle_grouplist.do?companyId=${companyId}&navoid=${navoid}");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>