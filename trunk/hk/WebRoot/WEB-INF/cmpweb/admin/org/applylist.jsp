<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpOrgApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">机构申请审核</div>
		<div class="mod_content">
			<div class="divrow">
				<form method="get" action="<%=path %>/epp/web/op/webadmin/org_applylist.do">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="navoid" value="${navoid}"/>
					姓名：<hk:text name="userName" value="${userName}" clazz="text"/><br/>
					机构名称：<hk:text name="orgName" value="${orgName}" clazz="text"/>
					<hk:select name="checkflg" checkedvalue="${checkflg}" forcecheckedvalue="-1">
						<hk:option value="-1" data="所有"/>
						<hk:option value="<%=CmpOrgApply.CHECKFLG_UNCHECKED %>" data="未审核"/>
						<hk:option value="<%=CmpOrgApply.CHECKFLG_YES %>" data="审核通过"/>
						<hk:option value="<%=CmpOrgApply.CHECKFLG_NO %>" data="审核不通过"/>
					</hk:select>
					<hk:submit clazz="btn" value="查询"/>
				</form>
			</div>
			<c:if test="${fn:length(list)>0}">
				<ul class="datalist">
					<c:forEach var="orgapply" items="${list}">
						<li>
							<div class="f_l" style="width:400px">
								<span class="split-r">${orgapply.userName }</span>
								<span class="split-r">${orgapply.tel }</span>
								<span class="split-r">${orgapply.email }</span><br/>
								${orgapply.orgName }
							</div>
							<div class="f_r" style="width:100px">
								<c:if test="${orgapply.checkOk}">
								<p><a href="javascript:checkno(${orgapply.oid })">不通过</a></p>
								</c:if>
								<c:if test="${!orgapply.checkOk}">
								<p><a href="javascript:checkok(${orgapply.oid })">通过</a></p>
								</c:if>
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata">还没有任何机构申请数据</div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/op/webadmin/org_applylist.do?companyId=${companyId}&navoid=${navoid }&checkflg=${checkflg}&userName=${enc_userName}&orgName=${enc_orgName }</c:set>
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
function checkok(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_applycheckok.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function checkno(oid){
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/op/webadmin/org_applycheckno.do?companyId=${companyId}&oid="+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>