<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AuthCompany"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">足迹认证审核</div>
	<div class="mod_content">
		<div class="divrow">
			<form method="get" action="<%=path %>/h4/admin/authcmp.do">
				审核状态：
				<hk:select name="mainStatus" checkedvalue="${mainStatus}">
					<hk:option value="<%=AuthCompany.MAINSTATUS_UNCHECK %>" data="未审核"/>
					<hk:option value="<%=AuthCompany.MAINSTATUS_CHECKED %>" data="审核成功"/>
					<hk:option value="<%=AuthCompany.MAINSTATUS_CHECKFAIL %>" data="审核不通过"/>
				</hk:select>
				<hk:submit clazz="btn" value="查询"/>
			</form>
		</div>
		<ul class="rowlist">
			<c:forEach var="vo" items="${volist}">
				<li>
					<div class="f_l" style="width: 570px;">
						<span class="split-r">姓名：${vo.authCompany.username }</span>
						<span class="split-r">电话：${vo.authCompany.tel }</span>
						<span class="split-r">申请时间：<fmt:formatDate value="${vo.authCompany.createTime }" pattern="yyyy-MM-dd"/></span>
						<br/>
						<span class="split-r">公司名称：${vo.authCompany.name }</span><br/>
						${vo.authCompany.content }
					</div>
					<div class="f_l" style="width: 100px;text-align: center;">
						<p class="b">(<hk:data key="view.authcompany.mainstatus_${vo.authCompany.mainStatus}"/>)</p>
						<c:if test="${!vo.authCompany.checkOk}">
						<a href="javascript:checkok(${vo.authCompany.sysId })">设为通过</a><br/>
						</c:if>
						<c:if test="${vo.authCompany.checkOk}">
						<a href="javascript:checkfail(${vo.authCompany.sysId })">设为不通过</a><br/>
						</c:if>
						<a href="javascript:del(${vo.authCompany.sysId })">删除</a><br/>
					</div>
					<div class="clr"></div>
				</li>
			</c:forEach>
		</ul>
		<div>
			<c:set var="page_url" scope="request"><%=path%>/h4/admin/authcmp.do?mainStatus=${mainStatus}</c:set>
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
function del(sysId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/admin/authcmp_del.do?sysId="+sysId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function checkfail(sysId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/admin/authcmp_checkfail.do?sysId="+sysId,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl
		}
	});
}
function checkok(sysId){
	tourl("<%=path%>/h4/admin/authcmp_tocheckok.do?sysId="+sysId);
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>