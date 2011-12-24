<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">专业管理 
		<c:if test="${parent!=null }">${parent.name }
		<a class="more" href="<%=path %>/epp/web/op/webadmin/studykind.do?companyId=${companyId}&parentId=${parent.parentId}">返回</a>
		</c:if>
		</div>
		<div class="mod_content">
			<c:if test="${parent.klevel<3}">
				<div class="divrow">
				<input type="button" value="添加专业" class="btn" onclick="tourl('<%=path %>/epp/web/op/webadmin/studykind_create.do?companyId=${companyId}&parentId=${parentId}')"/>
				</div>
			</c:if>
			<div class="divrow">
				<form method="get" action="<%=path %>/epp/web/op/webadmin/studykind.do">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="parentId" value="${parentId}"/>
					名称：<hk:text name="name" value="${name}" clazz="text"/>
					<hk:submit clazz="btn" value="查询"/>
				</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<ul class="datalist">
					<c:forEach var="kind" items="${list}">
						<li>
							<div class="f_l" style="width:260px">
								<a href="<%=path %>/epp/web/op/webadmin/studykind.do?companyId=${companyId}&parentId=${kind.kindId}">${kind.name }</a>
							</div>
							<div class="f_l" style="width:200px">
								<a href="<%=path %>/epp/web/op/webadmin/studykind_update.do?companyId=${companyId}&kindId=${kind.kindId}">修改</a>
								<c:if test="${!kind.hasChild}">
								/ <a href="javascript:delkind(${kind.kindId })">删除</a>
								</c:if>
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何数据</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/studykind.do?companyId=${companyId}&parentId=${kind.kindId}&name=${enc_name}</c:set>
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
function delkind(kindId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/studykind_del.do?companyId=${companyId}&kindId="+kindId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>