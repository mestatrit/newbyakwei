<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">选择服务 ${cmpActor.name }
		<a class="more" href="<%=path %>/epp/web/op/webadmin/actor_updateactor.do?companyId=${companyId}&actorId=${actorId}">返回</a>
		</div>
		<div class="mod_content">
			<div class="divrow">
				<form method="get" action="<%=path %>/epp/web/op/webadmin/actor_addsvrforactor.do">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="actorId" value="${actorId}"/>
					名称：<hk:text name="name" value="${name}" clazz="text"/>
					<hk:submit value="搜索" clazz="btn"/>
				</form>
			</div>
			<c:if test="${fn:length(volist)>0}">
				<c:forEach var="vo" items="${volist}">
					<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
						<div class="f_l" style="width:300px">
							<a href="<%=path %>/epp/web/op/webadmin/svr_update.do?companyId=${companyId}&svrId=${vo.cmpSvr.svrId}">${vo.cmpSvr.name }</a>
						</div>
						<div class="f_l" style="width:260px;padding-left: 20px">
							<c:if test="${vo.selected}">
							已选择 <a href="javascript:rmsvrfromactor(${vo.cmpActorSvrRef.oid })">取消</a>
							</c:if>
							<c:if test="${!vo.selected}">
							<a href="javascript:addsvrforactor(${vo.cmpSvr.svrId })">选择</a>
							</c:if>
						</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${fn:length(volist)==0}">
				<div class="nodata">还没有数据</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/actor_addsvrforactor.do?companyId=${companyId}&actorId=${actorId}&name=${enc_name}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function addsvrforactor(svrId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/actor_addsvrforactor.do?companyId=${companyId}&ch=1&actorId=${actorId}&svrId="+svrId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function rmsvrfromactor(oid){
	if(window.confirm('确实要从此人的服务项目中移除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/actor_rmsvrfromactor.do?companyId=${companyId}&oid="+oid,
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