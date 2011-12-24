<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">图集列表</div>
	<div class="mod_content">
		<div class="divrow">
			<input type="button" class="btn split-r" value="创建图集" onclick="tourl('<%=path %>/h4/op/venue/photo_createphotoset.do?companyId=${companyId }')"/>
		</div>
		<c:if test="${fn:length(list)>0}">
			<ul class="rowlist">
				<c:forEach var="n" items="${list}">
					<li>
						<div class="f_l" style="width:80px">
							<c:if test="${not empty n.picPath}">
							<img src="${n.pic60 }"/><br/>
							</c:if>
							<a href="<%=path %>/h4/op/venue/photo_photoset.do?companyId=${companyId}&setId=${n.setId}">${n.name }</a>
						</div>
						<div class="f_l" style="width:150px;padding-left: 20px;">
							<a href="<%=path %>/h4/op/venue/photo_photoset.do?companyId=${companyId}&setId=${n.setId}">查看图片</a> / 
							<a href="<%=path %>/h4/op/venue/photo_updatephotoset.do?companyId=${companyId}&setId=${n.setId}">修改</a> / 
							<a href="javascript:delset(${n.setId })">删除</a>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${fn:length(list)==0}">
			<div class="nodata">还没有任何图片</div>
		</c:if>
	</div>
</div>
<script type="text/javascript">
function delset(setId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/venue/photo_delphotoset.do?companyId=${companyId}&setId="+setId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
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