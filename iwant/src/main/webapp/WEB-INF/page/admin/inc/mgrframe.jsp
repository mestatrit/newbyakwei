<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="iwant.bean.enumtype.ActiveType"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_body_content" scope="request">
<div class="f_l" style="width: 160px">
	<ul class="mgrmenu">
		<c:if test="${adminUser.superAdmin}">
		<li>
			<a href="${appctx_path }/mgr/zone_provincelist.do" class="<c:if test="${op_zone }">sel</c:if>">地区管理</a>
		</li>
		</c:if>
		<li>
			<a href="${appctx_path }/mgr/project.do" class="<c:if test="${op_project }">sel</c:if>">项目管理</a>
		</li>
	</ul>
</div>
<div class="f_r" style="width: 710px;">
	${mgr_body_content }
</div>
<div class="clr"></div>
</c:set><jsp:include page="frame.jsp"></jsp:include>