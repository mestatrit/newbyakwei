<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<jsp:include page="../../inc/pub_inc.jsp"></jsp:include>
<c:set var="html_title" scope="request"><hk:data key="view.review.edit.title"/></c:set>
<c:set var="css_value" scope="request"><link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/company.css" /></c:set>
<c:set var="meta_value" scope="request">
<meta name="keywords" content="${bizCircle.name },火酷网,足迹"/>
<meta name="description" content="${bizCircle.name },火酷网,足迹"/>
</c:set>
<c:set var="body_hk_content" scope="request">
	<div class="mod">
		<h3><hk:data key="view.review.edit.title"/></h3>
		<c:set var="companyreview_form_showreturn" scope="request">true</c:set>
		<jsp:include page="../../inc/companyreviewform.jsp"></jsp:include>
	</div>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>