<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><c:set scope="request" var="mgr_body_content">
	<div class="mod">
		<div class="mod_title">
			创建项目
		</div>
		<div class="mod_content">
			<div>
				<c:set var="form_action" scope="request">${appctx_path}/mgr/project_create.do</c:set>
				<jsp:include page="form.jsp"></jsp:include>
			</div>
		</div>
	</div>
</c:set><jsp:include page="../inc/mgrframe.jsp"></jsp:include>