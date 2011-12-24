<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<div class="listbox">
	<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
</div>
<%request.setAttribute("url_rewrite", true);%>
<c:set var="page_url" scope="request">/laba/${w}</c:set>
<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>