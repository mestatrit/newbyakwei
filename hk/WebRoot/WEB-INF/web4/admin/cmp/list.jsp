<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AuthCompany"%>
<%@page import="com.hk.bean.Company"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">足迹管理</div>
	<div class="mod_content">
		<div class="divrow">
			<form method="get" action="<%=path %>/h4/admin/cmp.do">
				名称：<hk:text name="name" clazz="text" value="${name}"/>
				审核状态：
				<hk:select name="status" checkedvalue="${status}">
					<hk:option value="-100" data="所有"/>
					<hk:option value="<%=Company.COMPANYSTATUS_UNCHECK %>" data="未审核"/>
					<hk:option value="<%=Company.COMPANYSTATUS_CHECKED %>" data="审核通过"/>
					<hk:option value="<%=Company.COMPANYSTATUS_CHECKFAIL %>" data="审核不通过"/>
				</hk:select><br/>
				地区：<jsp:include page="../../inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">initselected(${pcityId});</script>
				<hk:submit clazz="btn" value="查询"/>
			</form>
		</div>
		<ul class="rowlist">
			<c:forEach var="cmp" items="${list}">
				<li>
					<div class="f_l" style="width: 300px;">
						<span class="split-r"><a target="_blank" href="/venue/${cmp.companyId }">${cmp.name }</a></span>
						<span class="split-r">${cmp.pcity.name }</span>
					</div>
					<div class="f_l" style="width: 80px;">
						<hk:data key="view.companystatus_${cmp.companyStatus}"/>
					</div>
					<div class="f_l" style="width: 200px;">
						<c:if test="${!cmp.checkSuccess}">
							<a href="javascript:checkok(${cmp.companyId })">设为通过</a> / 
						</c:if>
						<c:if test="${!cmp.checkFail}">
							<a href="javascript:checkfail(${cmp.companyId })">设为不通过</a> / 
						</c:if>
						<a href="<%=path %>/h4/admin/cmp_view.do?companyId=${cmp.companyId}">设置</a>
					</div>
					<div class="clr"></div>
				</li>
			</c:forEach>
		</ul>
		<div>
			<c:set var="page_url" scope="request"><%=path%>/h4/admin/cmp.do?status=${status}&name=${enc_name}&pcityId=${pcityId}</c:set>
			<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
function checkfail(sysId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_checkfail.do?companyId="+sysId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function checkok(sysId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/cmp_checkok.do?companyId="+sysId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>