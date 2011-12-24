<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">图片列表
			</div>
			<div class="mod_content">
				<div class="divrow">
					<input type="button" class="btn split-r" value="上传企业图片" onclick="tourl('#upload')"/>
					<a href="<%=path %>/epp/web/op/webadmin/info_photosetlist.do?companyId=${companyId}">图集管理</a>
				</div>
				<c:if test="${fn:length(list)>0}">
					<table class="nt" cellpadding="0" cellspacing="0">
						<c:forEach var="n" items="${list}">
							<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
								<div class="f_l" style="width:80px">
									<a href="<%=path %>/epp/web/op/webadmin/info_photo.do?companyId=${companyId}&photoId=${n.photoId}&return_url=${return_url}"><img src="${n.pic60 }"/></a>
								</div>
								<div class="f_l" style="width:270px;padding-left: 20px;">
									<c:if test="${o.headPath != n.path}">
										<a href="javascript:sethead(${n.photoId})">设为企业头图</a> / 
									</c:if>
									<a href="javascript:selset(${n.photoId })">选择图集</a> / 
									<a href="javascript:delcmpphoto(${n.photoId })">删除</a>
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有任何图片</div>
				</c:if>
				<div>
					<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/info_piclist.do?companyId=${companyId}</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
				<div style="width: 400px">
					<br/><a name="upload"></a>
					<h2>上传图片</h2>
					<form id="uppicfrm" onsubmit="return subuppicfrm(this.id)" method="post" enctype="multipart/form-data" action="<%=path %>/epp/web/op/webadmin/info_uploadcmppic.do" target="hideframe">
						<hk:hide name="companyId" value="${companyId}"/> 
						<c:forEach var="b" begin="0" end="4">
							<div class="divrow">
								<input type="file" name="f${b }" size="50"/>
							</div>
						</c:forEach>
						<div class="inforwarn" id="uploadmsg"></div>
						<div align="right">
						<hk:submit value="上传" clazz="btn"/>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
function selset(photoId){
	tourl('<%=path %>/epp/web/op/webadmin/info_selphotoset.do?companyId=${companyId}&photoId='+photoId+'&return_url='+encodeLocalURL());
}
function subuppicfrm(frmid){
	setHtml('uploadmsg','');
	showGlass(frmid);
	return true;
}
function delcmpphoto(photoId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/info_delpic.do?companyId=${companyId}&photoId="+photoId,
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