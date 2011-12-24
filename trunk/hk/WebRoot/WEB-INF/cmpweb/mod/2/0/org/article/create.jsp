<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">${cmpOrgNav.name } 发布文章
		<a class="more" href="/edu/${companyId }/${orgId}/column/${orgnavId}">返回</a>
	</div>
	<div class="mod_content">
		<div>
			<c:set var="cmpnav_form_action" scope="request"><%=path %>/epp/web/org/article_create.do</c:set>
			<jsp:include page="form.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
function createerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function createerror2(error,msg,v){alert('ss');
	tourl("<%=path %>/epp/web/org/article_update.do?companyId=${companyId}&orgnavId=${orgnavId}&oid="+v);
}
function createok(error,msg,v){
	tourl("/edu/${companyId}/${orgId}/article/${orgnavId}/"+v+".html");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>