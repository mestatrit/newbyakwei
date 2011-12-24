<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">服务管理
</div>
<div class="mod_content">
	<div class="divrow">
		<input value="创建服务" type="button" class="btn split-r" onclick="tourl('<%=path %>/h4/op/venue/svr_create.do?companyId=${companyId}')"/>
	</div>
	<div class="divrow">
	<form method="get" action="<%=path %>/h4/op/venue/svr.do">
		<hk:hide name="companyId" value="${companyId}"/>
		名称：<hk:text name="name" value="${name}" clazz="text"/>
		<hk:submit value="搜索" clazz="btn"/>
	</form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<ul class="rowlist">
			<li>
				<div class="f_l b" style="width:150px">
					名称
				</div>
				<div class="f_l b" style="width:90px">
					价格
				</div>
				<div class="f_l b" style="width:80px">
					服务时长
				</div>
				<div class="clr"></div>
			</li>
			<c:forEach var="n" items="${list}">
				<li>
					<div class="f_l" style="width:150px">
						<a href="<%=path %>/h4/op/venue/svr_update.do?companyId=${companyId}&svrId=${n.svrId}">${n.name }</a>
					</div>
					<div class="f_l" style="width:90px">
						￥${n.price }
					</div>
					<div class="f_l" style="width:80px">
						${n.svrmin }分钟
					</div>
					<div class="f_l" style="width:260px;padding-left: 20px">
						<a href="javascript:selset(${n.svrId })">选择图集</a> / 
						<a href="<%=path %>/h4/op/venue/svr_update.do?companyId=${companyId}&svrId=${n.svrId}">修改</a> / 
						<a href="javascript:del(${n.svrId })">删除</a>
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
		<c:set var="page_url" scope="request"><%=path %>/h4/op/venue/svr.do?companyId=${companyId}&name=${enc_name}</c:set>
		<jsp:include page="../../../inc/pagesupport_inc.jsp"></jsp:include>
	</div>
</div>
</div>
<script type="text/javascript">
function selset(svrId){
	tourl("<%=path %>/h4/op/venue/photo_selphotosetforsvr.do?companyId=${companyId}&svrId="+svrId);
}
function del(svrId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/venue/svr_del.do?companyId=${companyId}&svrId="+svrId,
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
</c:set><jsp:include page="../mgr.jsp"></jsp:include>