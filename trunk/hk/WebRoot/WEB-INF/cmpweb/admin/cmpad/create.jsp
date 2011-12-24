<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">
		<c:if test="${cmpAdGroup!=null}">${cmpAdGroup.name}</c:if> 
		添加广告</div>
		<div class="mod_content">
		<br/>
			<div>
				<c:set var="form_action" scope="request"><%=path %>/epp/web/op/webadmin/cmpad_create.do</c:set>
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
	<c:if test="${!(blockId>0)}">
	tourl("<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}&groupId=${groupId }");
	</c:if>
	<c:if test="${blockId>0}">
		tourl("<%=path %>/epp/web/op/webadmin/cmppageblock_content.do?companyId=${companyId}&blockId=${blockId }");
	</c:if>
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>