<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">选择服务 ${cmpActor.name }
<a class="more" href="<%=path %>/h4/op/venue/actor_updateactor.do?companyId=${companyId}&actorId=${actorId}">返回</a>
</div>
<div class="mod_content">
	<c:if test="${fn:length(list)>0}">
		<ul class="rowlist">
			<c:forEach var="svr" items="${list}">
				<li>
					<div class="f_l" style="width:150px">
						<a href="<%=path %>/epp/web/op/webadmin/svr_update.do?companyId=${companyId}&svrId=${svr.svrId}">${svr.name }</a>
					</div>
					<div class="f_l" style="width:90px">￥${svr.price }</div>
					<div class="f_l" style="width:260px;padding-left: 20px">
						<a href="javascript:addsvrforactor(${svr.svrId })">选择</a>
					</div>
					<div class="clr"></div>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${fn:length(list)==0}">
		<div class="nodata">没有可以添加的服务</div>
	</c:if>
	<div>
		<c:set var="page_url" scope="request"><%=path %>/h4/op/venue/svr_addsvrforactor.do?companyId=${companyId}&actorId=${actorId}&name=${enc_name}</c:set>
		<jsp:include page="../../../inc/pagesupport_inc.jsp"></jsp:include>
	</div>
</div>
</div>
<c:if test="${fn:length(reflist)>0}">
<div class="mod">
<div class="mod_title">已添加的服务</div>
<div class="mod_content">
	<div class="divrow">
		<c:forEach var="ref" items="${reflist}">
		<div class="divrow">
			<div class="f_l" style="width:150px">${ref.cmpSvr.name }</div>
			<div class="f_l" style="width:90px">￥${ref.cmpSvr.price }</div>
			<div class="f_l"><a href="javascript:rmsvrfromactor(${ref.oid })">删除</a></div>
			<div class="clr"></div>
		</div>
		</c:forEach>
	</div>
</div>
</div>
</c:if>
<script type="text/javascript">
function rmsvrfromactor(oid){
	if(window.confirm('确实要从此人的服务项目中移除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/h4/op/venue/actor_rmsvrfromactor.do?companyId=${companyId}&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function addsvrforactor(svrId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/op/venue/svr_addsvrforactor.do?companyId=${companyId}&ch=1&actorId=${actorId}&svrId="+svrId,
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
</c:set><jsp:include page="../mgr.jsp"></jsp:include>