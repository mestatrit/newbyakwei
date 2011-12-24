<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="epp.updatebbs"/> - ${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="">
	<div class="mod"><%EppViewUtil.loadCmpNavTop(request); %>
		<div class="mod_title"><hk:data key="epp.updatebbs"/></div>
		<div class="mod_content">
		<br/>
			<c:set scope="request" var="form_action"><%=path%>/epp/web/cmpbbs_update.do</c:set>
			<jsp:include page="bbsform.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,msg,v){
	tourl('<%=path%>/epp/web/cmpbbs_view.do?companyId=${companyId}&bbsId=${bbsId}');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>