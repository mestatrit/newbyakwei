<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);%>
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
			<div class="nodata">没有可选图集</div>
		</c:if>
		<div>
			<a class="more2" href="${denc_return_url }">返回</a>
		</div>
	</div>
</div>
<c:if test="${fn:length(reflist)>0}">
<div class="mod">
	<div class="mod_title">已选择图集</div>
	<div class="mod_content">
		<c:forEach var="ref" items="${reflist}">
			<div class="divrow">
				<div class="f_l" style="width: 150px;">
				<c:if test="${not empty ref.cmpPhotoSet.picPath}"><img src="${ref.cmpPhotoSet.pic60 }"/><br/></c:if>
				${ref.cmpPhotoSet.name }
				</div>
				<div class="f_l">
				<a href="javascript:delcmpphotosetref(${ref.oid })">取消选择</a>
				</div>
				<div class="clr"></div>
			</div>
		</c:forEach>
	</div>
</div>
</c:if>
<script type="text/javascript">
function delcmpphotosetref(oid){
	var url="<%=path%>/h4/op/venue/photo_delcmpphotosetref.do?companyId=${companyId}&oid="+oid;
	doAjax(url,function(data){
		refreshurl();
	});
}
function selset(setId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/op/venue/photo_selphotoset.do?companyId=${companyId}&ch=1&photoId=${photoId}&setId="+setId,
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