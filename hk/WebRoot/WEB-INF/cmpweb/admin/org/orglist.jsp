<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">机构管理</div>
		<div class="mod_content">
			<div class="divrow">
				<c:if test="${cmpOtherWebInfo.orgNeedCheck}">
					<span class="split-r">机构需要审核</span>
					<a href="javascript:setorgnocheck(${companyId })">设为机构注册不需要审核</a>
				</c:if>
				<c:if test="${!cmpOtherWebInfo.orgNeedCheck}">
					<span class="split-r">机构不需要审核</span>
					<a href="javascript:setorgneedcheck(${companyId })">设为机构注册需要审核</a>
				</c:if>
			</div>
			<div class="divrow">
				<form method="get" action="<%=path %>/epp/web/op/webadmin/org.do">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="navoid" value="${navoid}"/>
					名称：<hk:text name="name" value="${name}" clazz="text"/>
					<hk:submit clazz="btn" value="查询"/>
				</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<ul class="datalist">
					<c:forEach var="org" items="${list}">
						<li>
							<div class="f_l" style="width:260px">
								<a target="_blank" href="/edu/${companyId }/${org.orgId}">${org.name }</a>
							</div>
							<div class="f_l" style="width:70px">
								<c:if test="${org.available}">开启中	</c:if>
								<c:if test="${!org.available}">已暂停</c:if>
							</div>
							<div class="f_l" style="width:200px">
								<c:if test="${org.available}">
									<a href="javascript:setunuse(${org.orgId })">暂停</a>
								</c:if>
								<c:if test="${!org.available}">
									<a href="javascript:setuse(${org.orgId })">开启</a>
								</c:if>
								<c:if test="${!org.openUserInfo}">
									/ <a href="javascript:openuserinfo(${org.orgId })">开启报名信息</a>
								</c:if>
								<c:if test="${org.openUserInfo}">
									/ <a href="javascript:closeuserinfo(${org.orgId })">关闭报名信息</a>
								</c:if>
								<c:if test="${!org.openStyle}">
									/ <a href="javascript:openstyle(${org.orgId })">开启配色</a>
								</c:if>
								<c:if test="${org.openStyle}">
									/ <a href="javascript:closestyle(${org.orgId })">关闭配色</a>
								</c:if>
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何机构数据</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/org.do?companyId=${companyId}&navoid=${navoid }&name=${enc_name}</c:set>
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
function setorgneedcheck(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_setorgneedcheck.do?companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setorgnocheck(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_setorgnocheck.do?companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setuse(orgId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_setorgflgok.do?companyId=${companyId}&orgId="+orgId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setunuse(orgId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_setorgflgno.do?companyId=${companyId}&orgId="+orgId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function openuserinfo(orgId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_openuserinfo.do?companyId=${companyId}&orgId="+orgId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function closeuserinfo(orgId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_closeuserinfo.do?companyId=${companyId}&orgId="+orgId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function openstyle(orgId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_openstyle.do?companyId=${companyId}&orgId="+orgId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function closestyle(orgId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_closestyle.do?companyId=${companyId}&orgId="+orgId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>