<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">图片列表
			${cmpPhotoSet.name }
			<a class="more" href="<%=path %>/epp/web/op/webadmin/info_photosetlist.do?companyId=${companyId}">返回</a>
			</div>
			<div class="mod_content">
				<c:if test="${fn:length(list)>0}">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:80px">
								<a href="<%=path %>/epp/web/op/webadmin/info_photo.do?companyId=${companyId}&photoId=${n.photoId}&return_url=${return_url}"><img src="${n.companyPhoto.pic60 }"/></a>
							</div>
							<div class="f_l" style="width:270px;padding-left: 20px;">
								<c:if test="${cmpPhotoSet.picPath != n.companyPhoto.path}">
									<a href="javascript:sethead(${n.photoId})">设为图集头图</a> / 
								</c:if>
								<a href="javascript:rmphotofromset(${n.oid })">从图集中移除</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">还没有任何图片</div>
				</c:if>
				<div>
					<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/info_photoset.do?companyId=${companyId}&setId=${setId}</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function rmphotofromset(oid){
	if(window.confirm('确实要从图集中移除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/info_rmphotofromset.do?companyId=${companyId}&setId=${setId}&oid="+oid,
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
		url:"<%=path%>/epp/web/op/webadmin/info_makesethead.do?companyId=${companyId}&setId=${setId}&photoId="+photoId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>