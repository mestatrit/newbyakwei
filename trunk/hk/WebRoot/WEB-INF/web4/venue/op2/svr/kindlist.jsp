<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">服务分类管理
</div>
<div class="mod_content">
	<div class="divrow">
		<input value="创建分类" type="button" class="btn split-r" onclick="tourl('<%=path %>/h4/op/venue/svr_createkind.do?companyId=${companyId}')"/>
	</div>
	<c:if test="${fn:length(list)>0}">
		<table class="nt" cellpadding="0" cellspacing="0">
			<c:forEach var="n" items="${list}">
				<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
					<div class="f_l" style="width:300px">
						<a href="<%=path %>/h4/op/venue/svr_updatekind.do?companyId=${companyId}&kindId=${n.kindId}">${n.name }</a>
					</div>
					<div class="f_l" style="width:260px;padding-left: 20px">
						<a href="<%=path %>/h4/op/venue/svr_updatekind.do?companyId=${companyId}&kindId=${n.kindId}">修改</a> / 
						<a href="javascript:delkind(${n.kindId })">删除</a>
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
<script type="text/javascript">
function delkind(kindId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/venue/svr_delkind.do?companyId=${companyId}&kindId="+kindId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set><jsp:include page="../mgr.jsp"></jsp:include>