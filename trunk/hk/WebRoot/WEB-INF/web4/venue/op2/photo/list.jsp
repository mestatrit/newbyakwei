<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">图片列表</div>
	<div class="mod_content">
		<div class="divrow">
			<input type="button" class="btn split-r" value="上传图片" onclick="tourl('<%=path %>/h4/op/venue/photo_upload.do?companyId=${companyId }')"/>
		</div>
		<c:if test="${fn:length(list)>0}">
			<ul class="rowlist">
				<c:forEach var="n" items="${list}">
					<li>
						<div class="f_l" style="width:80px">
							<a href="<%=path %>/h4/op/venue/photo_view.do?companyId=${companyId}&photoId=${n.photoId}"><img src="${n.pic60 }"/></a>
						</div>
						<div class="f_l" style="width:270px;padding-left: 20px;">
							<c:if test="${company.headPath != n.path}">
								<a href="javascript:sethead(${n.photoId})">设为头图</a> / 
							</c:if>
							<a href="javascript:selset(${n.photoId })">选择图集</a> / 
							<a href="javascript:delcmpphoto(${n.photoId })">删除</a>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
				
			</ul>
		</c:if>
		<c:if test="${fn:length(list)==0}">
			<div class="nodata">还没有任何图片</div>
		</c:if>
		<div>
			<c:set var="page_url" scope="request"><%=path %>/h4/op/venue/photo.do?companyId=${companyId}</c:set>
			<jsp:include page="../../../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
function view(photoId){
	tourl("<%=path%>/h4/op/venue/photo_view.do?companyId=${companyId}&photoId=${photoId}&return_url="+encodeLocalURL());
}
function selset(photoId){
	tourl('<%=path %>/h4/op/venue/photo_selphotoset.do?companyId=${companyId}&photoId='+photoId+'&return_url='+encodeLocalURL());
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
			url:"<%=path%>/h4/op/venue/photo_delpic.do?companyId=${companyId}&photoId="+photoId,
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
		url:"<%=path%>/h4/op/venue/photo_sethead.do?companyId=${companyId}&photoId="+photoId,
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
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>