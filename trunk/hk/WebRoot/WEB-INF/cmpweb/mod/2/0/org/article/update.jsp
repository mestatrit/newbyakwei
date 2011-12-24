<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">${cmpOrgNav.name } 修改文章
		<c:if test="${cmpOrgNav.articleList}">
			<a class="more" href="/edu/${companyId }/${orgId}/column/${orgnavId}">返回</a>
		</c:if>
	</div>
	<div class="mod_content">
		<div>
			<c:set var="cmpnav_form_action" scope="request"><%=path %>/epp/web/org/article_update.do</c:set>
			<jsp:include page="form.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}

function updateok(error,msg,v){
	tourl("/edu/${companyId}/${orgId}/article/${orgnavId}/"+v+".html");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>