<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="row">
<hk:form action="/epp/login_validate.do" needreturnurl="true">
	<hk:hide name="companyId" value="${companyId}"/>
	<div class="ha">
		<hk:data key="epp.login.input"/>:<br/>
		<hk:text name="input" value="${input}"/>
	</div>
	<div class="ha">
		<hk:data key="epp.login.pwd"/>:<br/>
		<hk:pwd name="password"/>
	</div>
	<div class="ha"><hk:submit value="epp.login" res="true"/></div>
</hk:form>
</div>
</c:set>
<jsp:include page="inc/frame.jsp"></jsp:include>