<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">服务管理
		</div>
		<div class="mod_content">
			<div class="divrow">
				<input value="创建服务" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/svr_create.do?companyId=${companyId}')"/>
			</div>
			<div class="divrow">
			<form method="get" action="<%=path %>/epp/web/op/webadmin/svr.do">
				<hk:hide name="companyId" value="${companyId}"/>
				名称：<hk:text name="name" value="${name}" clazz="text"/>
				<hk:submit value="搜索" clazz="btn"/>
			</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:300px">
								<a href="<%=path %>/epp/web/op/webadmin/svr_update.do?companyId=${companyId}&svrId=${n.svrId}">${n.name }</a>
							</div>
							<div class="f_l" style="width:260px;padding-left: 20px">
								<a href="<%=path %>/epp/web/op/webadmin/svr_update.do?companyId=${companyId}&svrId=${n.svrId}">修改</a> / 
								<a href="javascript:del(${n.svrId })">删除</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有数据</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/svr.do?companyId=${companyId}&name=${enc_name}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function del(svrId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/svr_del.do?companyId=${companyId}&svrId="+svrId,
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