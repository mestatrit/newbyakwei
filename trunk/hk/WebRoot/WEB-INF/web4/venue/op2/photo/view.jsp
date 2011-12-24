<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">图片
	<a class="more" href="<%=path %>/h4/op/venue/photo.do?companyId=${companyId}">返回</a>
	</div>
	<div class="mod_content">
		<div class="divrow b" style="font-size: 18px">
			图片尺寸：
			<a class="split-r" href="<%=path %>/h4/op/venue/photo_view.do?companyId=${companyId}&photoId=${photoId}&size=240">240</a>		
			<a class="split-r" href="<%=path %>/h4/op/venue/photo_view.do?companyId=${companyId}&photoId=${photoId}&size=320">320</a>		
			<a class="split-r" href="<%=path %>/h4/op/venue/photo_view.do?companyId=${companyId}&photoId=${photoId}&size=600">600</a>		
		</div>
		<div class="divrow">
		<c:if test="${size==240}"><img src="${companyPhoto.pic240 }"/></c:if>
		<c:if test="${size==320}"><img src="${companyPhoto.pic320 }"/></c:if>
		<c:if test="${size==600}"><img src="${companyPhoto.pic600 }"/></c:if>
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