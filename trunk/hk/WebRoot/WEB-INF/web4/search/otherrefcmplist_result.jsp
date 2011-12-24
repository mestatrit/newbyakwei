<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.website.title"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<c:if test="${fn:length(cmplist)>0}">
		<div><h1 class="title3">${city.city}</h1></div>
		<c:forEach var="c" items="${cmplist}">
			<div class="list-1" onmouseover="this.className='list-1 bg2';" onmouseout="this.className='list-1';">
				<a href="/venue/${c.companyId }/" class="b">${c.name }</a><br/>
				${c.addr }
			</div>
		</c:forEach>
		<c:set var="page_url" scope="request">/s_otherrefcmpname.do?key=${enc_key}</c:set>
		<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
	</c:if>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>