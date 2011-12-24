<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.laba"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<c:if test="${userLogin}">
				<div class="laba_input">
					<jsp:include page="../inc/labainput.jsp"></jsp:include>
				</div>
			</c:if>
			<div id="laba_content">
				<div id="laba_all" class="active_tips_tab">喇叭搜索：${sw }</div>
				<div class="clr"></div>
				<div class="listbox">
					<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
				</div>
				<c:set var="page_url" scope="request"><%=path %>/laba_webs.do?sw=${enc_sw } </c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<div class="mod" align="center">
				<div class="divrow bdtm" style="padding-bottom: 20px">
				<input type="button" value="<hk:data key="view2.addvenueandtip"/>" onclick="tourl('/venue/search')" class="btn"/>
				</div>
			</div>
			<div class="mod">
				<div class="mod_content divrow bdtm" align="center">
					<form method="get" action="<%=path %>/laba_webs.do">
						<hk:text name="sw" value="${sw}" clazz="text" style="width:200px;"/>
						<hk:submit value="喇叭搜索" clazz="btn"/>
					</form>
				</div>
			</div>
			<jsp:include page="../inc/rcon_usertip_inc.jsp"></jsp:include>
		</div>
	</div>
	<div class="clr"></div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>