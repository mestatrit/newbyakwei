<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<div>
				<c:if test="${fn:length(nextlist)>0}">
					<div class="f_r" style="font-weight: bold;font-size: 30px;">
						<a href="<%=path %>/epp/web/video_next.do?companyId=${companyId }&navId=${navId}&oid=${oid }">&gt;&gt;</a>
					</div>
				</c:if>
				<div style="margin-right: 50px;text-align: center;"><h1 style="display: inline;">${cmpVideo.name }</h1></div>
				<div class="clr"></div>
			</div>
			<div class="divrow">
				<c:if test="${not empty cmpVideo.html}">
					<div class="hcenter" style="width:500px">
					${cmpVideo.html }
					</div>
				</c:if>
			</div>
			<c:if test="${not empty cmpVideo.intro}">
				<div class="divrow">${cmpVideo.intro }</div>
			</c:if>
			<a class="more2" href="<%=path %>/epp/web/video.do?companyId=${companyId}&navId=${navId}"><hk:data key="epp.return"/></a>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>