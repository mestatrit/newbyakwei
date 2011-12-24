<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">选择图集</div>
	<div class="mod_content">
		<img src="${companyPhoto.pic60 }"/><br/>
		<c:if test="${fn:length(list)>0}">
			<ul class="rowlist">
				<c:forEach var="n" items="${list}">
					<li>
						<div class="f_l" style="width:80px">
							<c:if test="${not empty n.picPath}">
							<img src="${n.pic60 }"/><br/>
							</c:if>
							${n.name }
						</div>
						<div class="f_l" style="width:150px;padding-left: 20px;">
							<a href="javascript:selset(${n.setId })">选择</a>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${fn:length(list)==0}">
			<div class="nodata">还没有任何图集</div>
		</c:if>
		<div>
			<a class="more2" href="<%=path %>/h4/op/venue/svr.do?companyId=${companyId}">返回</a>
		</div>
	</div>
</div>
<div class="mod">
<div class="mod_title">已选择的图集</div>
<div class="mod_content">
<c:if test="${not empty selCmpPhotoSet.picPath}">
<img src="${selCmpPhotoSet.pic60 }"/><br/>
</c:if>
${selCmpPhotoSet.name } <a href="javascript:clearsvrphotoset(${svrId })">删除</a>
</div>
</div>
<script type="text/javascript">
function clearsvrphotoset(svrId){
	if(window.confirm('确实要取消该图集')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/venue/svr_clearsvrphotoset.do?companyId=${companyId}&svrId="+svrId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				tourl('${denc_return_url }');
			}
		});
	}
}
function selset(setId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/op/venue/photo_selphotosetforsvr.do?companyId=${companyId}&ch=1&svrId=${svrId}&setId="+setId,
		cache:false,
    	dataType:"html",
		success:function(data){
			tourl('${denc_return_url }');
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
</c:set><jsp:include page="../mgr.jsp"></jsp:include>