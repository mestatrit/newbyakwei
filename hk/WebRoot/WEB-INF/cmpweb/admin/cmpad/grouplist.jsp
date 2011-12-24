<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">企业广告</div>
		<div class="mod_content">
			<div class="divrow">
			</div>
			<div>
				<input value="创建组" type="button" class="btn split-r" onclick="tourl('<%=path %>/epp/web/op/webadmin/cmpad_creategroup.do?companyId=${companyId}&adid=${adid}')"/>
				<a href="<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}">广告管理</a>
			</div>
			<c:if test="${fn:length(list)>0}">
				<table class="nt" cellpadding="0" cellspacing="0">
					<c:forEach var="n" items="${list}">
						<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2'" onmouseout="this.className='divrow bdtm'">
							<div class="f_l" style="width:300px">
								<a href="<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId }&groupId=${n.groupId}">${n.name }</a>
							</div>
							<div class="f_l" style="width:260px;padding-left: 20px">
								<c:if test="${cmpAd!=null}">
								<a href="javascript:updatecmpadgroupid(${n.groupId })">选定</a> / 
								</c:if>
								<c:if test="${o.cmpEdu}">
								<a href="javascript:selblockforgroup(${n.groupId })">推荐到区块</a> / 
								</c:if>
								<a href="<%=path %>/epp/web/op/webadmin/cmpad_updategroup.do?groupId=${n.groupId }&companyId=${companyId}">修改</a> / 
								<a href="javascript:delgroup(${n.groupId })">删除</a>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</table>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有添加任何组</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/cmpad_grouplist.do?companyId=${companyId}&adid=${adid}&name=${enc_name}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function selblockforgroup(groupId){
	tourl('<%=path %>/epp/web/op/webadmin/cmpad_selblockforgroup.do?groupId='+groupId+'&companyId=${companyId}&return_url='+encodeLocalURL());
}
function updatecmpadgroupid(groupId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/cmpad_updatecmpadgroupid.do?companyId=${companyId}&adid=${adid}&groupId="+groupId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function delgroup(groupId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/epp/web/op/webadmin/cmpad_delgroup.do?companyId=${companyId}&groupId="+groupId,
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