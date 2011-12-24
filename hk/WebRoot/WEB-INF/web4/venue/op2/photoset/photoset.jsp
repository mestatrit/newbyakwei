<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">图片列表
	${cmpPhotoSet.name }
	<a class="more" href="<%=path %>/h4/op/venue/photo_photosetlist.do?companyId=${companyId}">返回</a>
	</div>
	<div class="mod_content">
		<c:if test="${fn:length(list)>0}">
			<ul class="rowlist">
				<c:forEach var="n" items="${list}">
					<li>
						<div class="f_l" style="width:80px">
							<img src="${n.companyPhoto.pic60 }"/>
						</div>
						<div class="f_l" style="width:270px;padding-left: 20px;">
							<c:if test="${cmpPhotoSet.picPath != n.companyPhoto.path}">
								<a href="javascript:sethead(${n.photoId})">设为图集头图</a> / 
							</c:if>
							<a href="javascript:rmphotofromset(${n.oid })">从图集中移除</a>
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
			<c:set var="page_url" scope="request"><%=path %>/h4/op/venue/photo_photoset.do?companyId=${companyId}&setId=${setId}</c:set>
			<jsp:include page="../../../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
function rmphotofromset(oid){
	if(window.confirm('确实要从图集中移除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/h4/op/venue/photo_rmphotofromset.do?companyId=${companyId}&setId=${setId}&oid="+oid,
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
		url:"<%=path%>/h4/op/venue/photo_makesethead.do?companyId=${companyId}&setId=${setId}&photoId="+photoId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
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