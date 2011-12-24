<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<jsp:include page="../inc/pub_inc.jsp"></jsp:include>
<c:set var="html_title" scope="request">${cmpChildKind.name} - 火酷网</c:set>
<c:set var="body_hk_content" scope="request">
	<div class="mod_left">
		<jsp:include page="../inc/left_search.jsp"></jsp:include>
		<jsp:include page="../inc/companykind_inc.jsp"></jsp:include>
		<jsp:include page="../inc/bizcircle_inc.jsp"></jsp:include>
	</div>
	<div class="mod_primary">
		<c:set var="nav_2_path_content" scope="request">
			<ul>
				<li>
					<a class="home" href="http://<%=HkWebConfig.getWebDomain()%>"></a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp_pklist.do?parentId=${parentId }&${url_add }">${parentKind.name }</a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp_klist.do?kindId=${kindId }&${url_add }">${companyKind.name }</a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp_cklist.do?oid=${oid }&kindId=${kindId }&${url_add }">${cmpChildKind.name }</a>
				</li>
			</ul>
		</c:set>
		<jsp:include page="../inc/nav-2.jsp"></jsp:include>
		<div class="mod_primary_l">
			<c:if test="${fn:length(childkindlist)>0}"><div class="line"><div class="atag"><c:forEach var="cp" items="${childkindlist}"><a href="<%=path %>/cmp_cklist.do?oid=${cp.oid }&kindId=${kindId }&${url_add }">${cp.name }</a></c:forEach></div></div></c:if>
			<c:set var="page_url" scope="request">/cmp_cklist.do?oid=${oid }&pcityId=${pcityId}</c:set>
			<jsp:include page="../inc/companylist_inc.jsp"></jsp:include>
		</div>
		<div class="mod_primary_r">
			<jsp:include page="../inc/hottaglist_inc.jsp"></jsp:include>
		</div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>