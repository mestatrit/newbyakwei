<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.user.laba" arg0="${user.nickName}"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<c:if test="${userLogin && loginUser.userId==userId}">
				<div class="laba_input">
					<jsp:include page="../inc/labainput.jsp"></jsp:include>
				</div>
			</c:if>
			<div class="f_l" style="width: 540px;">
				<div class="active_tips_tab"><hk:data key="view2.user.laba" arg0="${user.nickName}"/></div>
				<div class="f_r" style="position: relative;"><a class="return2" href="/user/${userId }/"><hk:data key="view2.return"/></a></div>
				<div class="clr"></div>
				<div id="laba_content">
					<div class="listbox">
						<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
					</div>
					<%request.setAttribute("url_rewrite", true);%>
					<c:set var="page_url" scope="request">/user/${userId}/laba</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
				<div class="clr"></div>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<jsp:include page="../inc/rcon_usertip_inc.jsp"></jsp:include>
		</div>
	</div>
	<div class="clr"></div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>