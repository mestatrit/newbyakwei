<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">设置网站配色</div>
			<div class="mod_content">
				<div class="hcenter" style="width: 400px;">
				<div class="divrow">
					<span class="split-r">网站默认配色</span>
					<c:if test="${!cmpInfo.enableUserStyle}">
						<span class="b">启用中</span>
					</c:if>
					<c:if test="${cmpInfo.enableUserStyle}">
						<hk:button onclick="settyleinuse(0)" clazz="btn" value="启用"/>
					</c:if>
				</div>
				<c:if test="${not empty cmpInfo.styleData}">
				<div class="divrow">
					<span class="split-r">自定义配色 </span>
					<c:if test="${cmpInfo.enableUserStyle}">
						<span class="b">启用中</span>
					</c:if>
					<c:if test="${!cmpInfo.enableUserStyle}">
						<hk:button onclick="settyleinuse(1)" clazz="btn" value="启用"/>
					</c:if>
				</div>
				</c:if>
				<div class="divrow">
				<hk:button clazz="btn" value="自定义网站配色" onclick="toupdate()"/>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function toupdate(){
	tourl('<%=path %>/epp/web/op/webadmin/info_updatestyle.do?companyId=${companyId}');
}
function settyleinuse(flg){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/info_setstyleinuse.do?companyId=${companyId}&userstyle="+flg,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>