<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">工作人员管理</div>
<div class="mod_content">
	<div class="divrow">
		<input value="添加工作人员" type="button" class="btn split-r" onclick="tourl('<%=path %>/h4/op/venue/actor_createactor.do?companyId=${companyId}')"/>
	</div>
	<div class="divrow">
	<form method="get" action="<%=path %>/h4/op/venue/actor.do">
		<hk:hide name="companyId" value="${companyId}"/>
		姓名：<hk:text name="name" value="${name}" clazz="text"/>
		<hk:submit value="搜索" clazz="btn"/>
	</form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<ul class="rowlist">
			<c:forEach var="n" items="${list}">
				<li>
					<div class="f_l" style="width:150px">
						<a href="<%=path %>/h4/op/venue/actor_updateactor.do?companyId=${companyId}&actorId=${n.actorId}">${n.name }</a>
					</div>
					<div class="f_l" style="width:100px">
						<hk:data key="epp.cmpactor.actorStatus${n.actorStatus}"/>
					</div>
					<div class="f_l" style="padding-left: 20px">
						
						<c:if test="${n.actorReserve}">
							<a href="javascript:setsvr(${n.actorId })">管理服务</a> / 
							<a href="javascript:setworkday(${n.actorId })">设置工作日</a> / 
							<a href="javascript:setsptime(${n.actorId })">时间管理</a> / 
						<a href="javascript:updatestatus(${n.actorId })">更改状态</a> / 
						</c:if>
						<a href="<%=path %>/h4/op/venue/actor_updateactor.do?companyId=${companyId}&actorId=${n.actorId}">修改</a> / 
						<a href="javascript:delactor(${n.actorId })">删除</a>
					</div>
					<div class="clr"></div>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${fn:length(list)==0}">
		<div class="nodata">还没有数据</div>
	</c:if>
	<div>
		<c:set var="page_url" scope="request"><%=path %>/h4/op/venue/actor.do?companyId=${companyId}&roleId=${roleId}&name=${enc_name}</c:set>
		<jsp:include page="../../../inc/pagesupport_inc.jsp"></jsp:include>
	</div>
</div>
</div>
<script type="text/javascript">
function setsvr(actorId){
	tourl("<%=path%>/h4/op/venue/svr_addsvrforactor.do?companyId=${companyId}&actorId="+actorId);
}
function delactor(actorId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/h4/op/venue/actor_delactor.do?companyId=${companyId}&actorId="+actorId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function setworkday(actorId){
	tourl("<%=path %>/h4/op/venue/actor_setworkday.do?companyId=${companyId}&actorId="+actorId+"&return_url="+encodeLocalURL());
}
function setsptime(actorId){
	tourl("<%=path %>/h4/op/venue/actor_sptimelist.do?companyId=${companyId}&actorId="+actorId+"&return_url="+encodeLocalURL());
}
function updatestatus(actorId){
	tourl("<%=path %>/h4/op/venue/actor_updatestatus.do?companyId=${companyId}&actorId="+actorId+"&return_url="+encodeLocalURL());
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