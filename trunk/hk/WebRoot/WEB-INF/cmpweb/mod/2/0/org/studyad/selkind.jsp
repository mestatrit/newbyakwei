<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">${cmpOrgNav.name } 选择专业
	<a class="more" href="${denc_return_url }">返回</a>
	</div>
	<div class="content">
		<c:if test="${parent!=null}">
		<span style="font-weight: bold;font-size: 18px;">${parent.name }</span>
		<a class="more" href="<%=path %>/epp/web/org/studyad_selkind.do?companyId=${companyId}&orgId=${orgId}&parentId=${parent.parentId }&orgnavId=${orgnavId }&adid=${adid }&return_url=${return_url}">上一级</a>
		</c:if>
		<c:if test="${fn:length(list)==0}"><hk:data key="epp.cmpdata.nodatalist"/></c:if>
		<c:if test="${fn:length(list)>0}">
			<ul class="datalist">
				<c:forEach var="kind" items="${list}">
					<li>
						<span class="fl" style="width:400px">
							<c:if test="${kind.hasChild}">
								<a href="<%=path %>/epp/web/org/studyad_selkind.do?companyId=${companyId}&orgId=${orgId}&parentId=${kind.kindId }&orgnavId=${orgnavId }&adid=${adid }&return_url=${return_url}">${kind.name }</a>
							</c:if>
							<c:if test="${!kind.hasChild}">
								${kind.name }
							</c:if>
						</span>
						<c:if test="${!kind.hasChild}">
							<span class="fl" style="width:100px">
								<a href="<%=path %>/epp/web/org/studyad_selkind.do?companyId=${companyId}&orgId=${orgId}&kindId=${kind.kindId }&orgnavId=${orgnavId }&adid=${adid }&ch=1&return_url=${return_url}">选择</a>
							</span>
						</c:if>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="fr">
			<c:set var="page_url" scope="request"><%=path%>/epp/web/org/studyad_slekind.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}&parentId=${parentId}&name=${enc_name}&adid=${adid}</c:set>
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
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>