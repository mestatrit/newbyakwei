<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">设置网站默认背景图片</div>
		<div class="mod_content">
			<div>
				<br/>
				<form id="uploadlogofrm" target="hideframe" onsubmit="return subuploadfrm(this.id)" method="post" enctype="multipart/form-data" action="<%=path %>/epp/web/op/webadmin/info_updatebgimage.do">
					<hk:hide name="ch" value="1"/>
					<hk:hide name="companyId" value="${companyId}"/>
					图片文件：<input type="file" name="f" size="30"/><hk:submit value="上传" clazz="btn"/>
					<div class="infowarn" id="warnmsg"></div>
					<c:if test="${not empty cmpInfo.bgPicPath}">
						<div>
							<a href="javascript:delbgpic()">删除背景图片</a>
							<c:if test="${not empty o.logopath}">
								<div style="width: 600px;"><img src="${cmpInfo.bgPicUrl }?v=<%=Math.random() %>"/></div>
							</c:if>
						</div>
					</c:if>
				</form>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delbgpic(){
	if(window.confirm('确实要删除背景图片？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/info_delbgimage.do?companyId=${companyId}",
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function subuploadfrm(frmid){
	setHtml('warnmsg','');
	showGlass(frmid);
	return true;
}
function updateerror(error,msg,v){
	setHtml('warnmsg',msg);hideGlass();
}
function updateok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>