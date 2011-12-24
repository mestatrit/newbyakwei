<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">
角色管理
</div>
<div class="mod_content">
	<div class="divrow">
		<input value="添加角色" type="button" class="btn split-r" onclick="tourl('<%=path %>/h4/op/venue/actor_createrole.do?companyId=${companyId}')"/>
	</div>
	<c:if test="${fn:length(list)>0}">
		<ul class="rowlist">
			<c:forEach var="n" items="${list}">
				<li>
					<div class="f_l" style="width:300px">
						${n.name }
					</div>
					<div class="f_l" style="width:260px;padding-left: 20px">
						<a href="<%=path %>/h4/op/venue/actor_updaterole.do?companyId=${companyId}&roleId=${n.roleId}">修改</a> / 
						<a href="javascript:delrole(${n.roleId })">删除</a>
					</div>
					<div class="clr"></div>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${fn:length(list)==0}">
		<div class="nodata">还没有数据</div>
	</c:if>
</div>
</div>
<script type="text/javascript">
function delrole(roleId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/h4/op/venue/actor_delrole.do?companyId=${companyId}&roleId="+roleId,
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