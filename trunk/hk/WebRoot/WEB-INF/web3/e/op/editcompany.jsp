<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.info"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
	<c:set var="company_form_action" scope="request">/e/op/op_editweb.do</c:set>
	<c:set var="show_companyform_return" scope="request">true</c:set>
	<c:set var="companyform_return_url" scope="request">cmp.do?companyId=${companyId}</c:set>
	<jsp:include page="companyform.jsp"></jsp:include>
</div>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>