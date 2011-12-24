<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${tag.name}</c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div>
	<h1 class="title3"><hk:data key="view2.tagvenuelist_incity" arg0="${city.city}" arg1="${tag.name}"/></h1>
	</div>
	<c:forEach var="c" items="${list}">
		<div class="list-1" onmouseover="this.className='list-1 bg2';" onmouseout="this.className='list-1';">
			<a href="/venue/${c.companyId }/" class="b">${c.company.name }</a><br/>
			${c.company.pcity.name } ${c.company.addr }
		</div>
	</c:forEach>
	<c:set var="url_rewrite" value="true" scope="request"/>
	<c:set var="page_url" scope="request">/venue/tag/${tagId}</c:set>
	<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>