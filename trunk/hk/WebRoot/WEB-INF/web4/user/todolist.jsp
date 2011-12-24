<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.last"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div class="f_l">
				<div class="f_l" style="margin-right: 20px;">
					<h1><a href="/user/${userId }/">${user.nickName }</a></h1>
				</div>
				<div id="do" class="inactive_tips_tab"><a href="/user/${userId }/done"><hk:data key="view2.last"/></a></div>
				<div id="todo" class="active_tips_tab"><a href="/user/${userId }/todo"><hk:data key="view2.todo"/></a></div>
				<div class="clr"></div>
				<div id="listbox" class="listbox">
					<c:if test="${fn:length(cmptipvolist)==0}"><hk:data key="view2.no_tips_in_this_place"/></c:if>
					<jsp:include page="../inc/cmptipvolist_inc.jsp"></jsp:include>
					<%request.setAttribute("url_rewrite", true);%>
					<c:set var="page_url" scope="request">/user/${userId}/todo</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
				<div class="clr"></div>
			</div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<jsp:include page="../inc/rcon_usertip_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>