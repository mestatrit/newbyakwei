<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<jsp:include page="../inc/pub_inc.jsp"></jsp:include>
<c:set var="html_title" scope="request">${bizCircle.name} - 火酷网</c:set>
<c:set var="meta_value" scope="request">
<meta name="keywords" content="${bizCircle.name },火酷网,足迹"/>
<meta name="description" content="${bizCircle.name },火酷网,足迹"/>
</c:set>
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
				<c:if test="${kindId>0}">
				<li>
					<a class="nav-a" href="<%=path %>/cmp_klist.do?kindId=${kindId }&${url_add }">${companyKind.name }</a>
				</li>
				</c:if>
				<li>
					<a class="nav-a" href="<%=path %>/cmp_bzcmplist.do?circleId=${circleId }&parentId=${parentId }&kindId=${kindId }&${url_add }">${bizCircle.name}</a>
				</li>
			</ul>
		</c:set>
		<jsp:include page="../inc/nav-2.jsp"></jsp:include>
		<div class="mod_primary_l">
			<c:if test="${parentId>0 || kindId>0}">
				<div class="text_14">
					<a href="<%=path %>/cmp_bzcmplist.do?circleId=${circleId }&${url_add }"><hk:data key="view.company.allkindcmplist"/></a>
				</div>
			</c:if>
			<c:set var="page_url" scope="request">/cmp_bzcmplist.do?parentId=${parentId }&kindId=${kindId}&pcityId=${pcityId}&circleId=${circleId}</c:set>
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