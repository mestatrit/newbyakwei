<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${box.name}</c:set>
<c:set var="html_body_content" scope="request">
<div>
	<jsp:include page="../inc/boxfeed_inc.jsp"></jsp:include>
	<div class="rcon2">
		<div class="inner">
			<c:if test="${equipment!=null}">
				<div class="mod">
					<div class="mod_title">${box.name }开箱副产品</div>
					<div class="mod_content">
							<div class="row">
								<h1>${equipment.name }</h1>
								${equipment.intro }
							</div>
					</div>
				</div>
			</c:if>
			<a href="/box/${boxId }" class="more2"><hk:data key="view2.return"/></a>
		</div>
	</div>
	<div class="clr"></div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>