<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">
		角色管理
		</div>
		<div class="mod_content">
			<div class="divrow">
				<input value="添加角色" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/actor_createrole.do?companyId=${companyId}')"/>
			</div>
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:300px">
								${n.name }
							</div>
							<div class="f_l" style="width:260px;padding-left: 20px">
								<a href="<%=path %>/epp/web/op/webadmin/actor_updaterole.do?companyId=${companyId}&roleId=${n.roleId}">修改</a> / 
								<a href="javascript:delrole(${n.roleId })">删除</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有数据</div>
			</c:if>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function delrole(roleId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/actor_delrole.do?companyId=${companyId}&roleId="+roleId,
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