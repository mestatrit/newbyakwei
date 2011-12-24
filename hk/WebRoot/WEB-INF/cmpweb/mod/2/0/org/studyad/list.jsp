<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">${cmpOrgNav.name }</div>
	<div class="content">
		<c:if test="${adminorg}">
			<div class="divrow">
				<a class="split-r" href="javascript:tocreate()">发布招生简章</a>
			</div>
		</c:if>
		<c:if test="${fn:length(list)==0}"><hk:data key="epp.cmpdata.nodatalist"/></c:if>
		<c:if test="${fn:length(list)>0}">
			<ul class="datalist">
				<c:forEach var="ad" items="${list}">
					<li>
						<span class="title"><a href="/edu/${companyId }/${orgId }/zhaosheng/${orgnavId }/${ad.adid }.html">${ad.title }</a></span>
						<span class="time"><fmt:formatDate value="${ad.createTime}" pattern="yyyy-MM-dd"/></span>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="fr">
			<c:set var="page_url" scope="request">/edu/${companyId }/${orgId }/zhaosheng/${orgnavId }</c:set>
			<c:set var="url_rewrite" scope="request" value="true"/>
			<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
		</div>
		<div class="clr"></div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#e5e6e8');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});
function tocreate(){
	var return_url='<%=path %>/epp/web/org/studyad_create.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}';
	tourl('<%=path %>/epp/web/org/studyad_selkind.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId }&return_url='+encodeURL(return_url));
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>