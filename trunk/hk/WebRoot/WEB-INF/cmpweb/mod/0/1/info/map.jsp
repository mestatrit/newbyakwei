<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<h3 class="">${cmpNav.name }</h3>
	<div class="mod_content">
		<iframe scrolling="no" width="600" height="600" frameborder="0" style="border: none;" src="http://www.huoku.com/map4.jsp?companyId=${companyId }"></iframe>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>