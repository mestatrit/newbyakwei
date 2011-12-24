<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">图集列表</div>
			<div class="mod_content">
				<div class="divrow">
					<input type="button" class="btn split-r" value="创建图集" onclick="tourl('<%=path %>/epp/web/op/webadmin/info_createphotoset.do?companyId=${companyId }')"/>
					<a href="<%=path %>/epp/web/op/webadmin/info_piclist.do?companyId=${companyId}">图片管理</a>
				</div>
				<c:if test="${fn:length(list)>0}">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:80px">
								<c:if test="${not empty n.picPath}">
								<img src="${n.pic60 }"/><br/>
								</c:if>
								<a href="<%=path %>/epp/web/op/webadmin/info_photoset.do?companyId=${companyId}&setId=${n.setId}">${n.name }</a>
							</div>
							<div class="f_l" style="width:150px;padding-left: 20px;">
								<a href="<%=path %>/epp/web/op/webadmin/info_photoset.do?companyId=${companyId}&setId=${n.setId}">查看图片</a> / 
								<a href="<%=path %>/epp/web/op/webadmin/info_updatephotoset.do?companyId=${companyId}&setId=${n.setId}">修改</a> / 
								<a href="javascript:delset(${n.setId })">删除</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有任何图片</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function subuppicfrm(frmid){
	setHtml('uploadmsg','');
	showGlass(frmid);
	return true;
}
function delset(setId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/info_delphotoset.do?companyId=${companyId}&setId="+setId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function sethead(photoId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/info_sethead.do?companyId=${companyId}&photoId="+photoId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function uploaderror(error,msg,v){
	setHtml('uploadmsg',msg);
	hideGlass();
}
function uploadok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>