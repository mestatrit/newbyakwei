<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">标签列表
			</div>
			<div class="mod_content">
				<div class="divrow b">
				显示所有文章中添加的标签
				</div>
				<div class="divrow b">
					<form action="<%=path %>/epp/web/op/webadmin/cmparticle_taglist.do">
						<hk:hide name="companyId" value="${companyId}"/>
						标签：<hk:text name="name" clazz="text" value="${name}"/>
						<hk:submit clazz="btn" value="搜索"/>
					</form>
				</div>
				<c:if test="${fn:length(list)>0}">
					<ul class="datalist">
						<c:forEach var="tag" items="${list}">
							<li>
								<span class="f_l" style="width:200px">${tag.name }</span>
								<span class="f_l">
								<c:if test="${tag.pink}">
									<a href="javascript:delpink(${tag.tagId })">取消推荐</a> /
								</c:if>
								<c:if test="${!tag.pink}">
									<a href="javascript:pink(${tag.tagId })">推荐</a> /
								</c:if>
									<a href="javascript:deltag(${tag.tagId })">删除</a>
								</span>
								<div class="clr"></div>
							</li>
						</c:forEach>
					</ul>
				</c:if>
				<c:if test="${fn:length(list)==0}">
					<div class="nodata">本页没有标签</div>
				</c:if>
				<div>
					<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmparticle_taglist.do?companyId=${companyId}&name=${enc_name}</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#ffffcc');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});
function deltag(tagId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmparticle_deltag.do?companyId=${companyId}&tagId="+tagId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function pink(tagId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_setpink.do?companyId=${companyId}&tagId="+tagId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function delpink(tagId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmparticle_delpink.do?companyId=${companyId}&tagId="+tagId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>