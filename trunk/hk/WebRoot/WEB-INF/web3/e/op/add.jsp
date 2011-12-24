<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request"><hk:data key="view.company.create"/></c:set>
<c:set var="body_hk_content" scope="request">
<style>
body{background:#DEDFE1;}
</style>
<div class="mod-8">
	<%=Hkcss2Util.rd_bg %>
	<div class="cont">
		<div style="padding: 20px;">
			<h2>创建足迹</h2>
			<div class="bdbtm"></div>
			<c:set var="company_form_action" scope="request">/e/op/op_addweb.do</c:set>
			<c:set var="show_companyform_return" scope="request">false</c:set>
			<c:set var="hide_kind" scope="request">false</c:set>
			<jsp:include page="companyform.jsp"></jsp:include>
		</div>
	</div>
	<%=Hkcss2Util.rd_bg_bottom %>
</div>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>