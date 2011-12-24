<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">上传网站logo</div>
		<div class="mod_content">
			<div>
				<br/>
				<form id="uploadlogofrm" target="hideframe" onsubmit="return subuploadfrm(this.id)" method="post" enctype="multipart/form-data" action="<%=path %>/epp/web/op/webadmin/info_uploadlogo.do">
					<hk:hide name="ch" value="1"/>
					<hk:hide name="companyId" value="${companyId}"/>
					<c:if test="${not empty o.logopath}">
						<div class="divrow"><img src="${o.logoPic }?v=<%=Math.random() %>"/></div>
					</c:if>
					<div class="divrow">
						LOGO文件：<input type="file" name="f" size="50"/>
					</div>
					<c:if test="${not empty o.logo2path}">
						<div class="divrow"><img src="${o.logo2Pic }?v=<%=Math.random() %>"/></div>
					</c:if>
					<div class="divrow">
						<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==1}">
							版权位置LOGO：<input type="file" name="f2" size="50"/><br/>
							<div>版权位置LOGO 建议为透明png或者透明gif，最宽边不能超过100px(没有可以不上传)</div>
						</c:if>
					</div>
					<div class="divrow" align="center">
					<div class="infowarn" id="warnmsg"></div>
					<hk:submit value="上传" clazz="btn"/>
					</div>
				</form>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function subuploadfrm(frmid){
	setHtml('warnmsg','');
	showGlass(frmid);
	return true;
}
function uploaderror(error,msg,v){
	setHtml('warnmsg',msg);hideGlass();
}
function uploadok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>