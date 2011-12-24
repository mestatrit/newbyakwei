<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">栏目管理</div>
	<div class="content">
		<c:if test="${fn:length(cmporgnavlist)==0}"><hk:data key="epp.cmpdata.nodatalist"/></c:if>
		<c:if test="${fn:length(cmporgnavlist)>0}">
			<ul class="datalist">
				<c:forEach var="nav" items="${cmporgnavlist}" varStatus="idx">
					<li>
						<span class="fl" style="width: 200px">${nav.name }</span>
						<span class="fl" style="width: 100px">
							<c:if test="${idx.index>0}"><a href="javascript:moveup(${nav.navId })">位置上移</a></c:if>&nbsp;
						</span>
						<span class="fl" style="width: 200px">
						<a href="<%=path %>/epp/web/org/org_updatenav.do?companyId=${companyId}&orgId=${orgId}&oid=${nav.navId}">修改</a>
						</span>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
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
function moveup(oid){
	$.ajax({
		type:"POST",
		url:"<%=path %>/epp/web/org/org_moveupnav.do?companyId=${companyId}&orgId=${orgId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>