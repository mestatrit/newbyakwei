<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<h1 class="s1">${cmpOrgNav.name }</h1>
	<div class="content">
		<c:if test="${adminorg}">
		<a href="<%=path %>/epp/web/org/msg_list.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}">留言管理</a>
		</c:if>
		<c:if test="${fn:length(list)==0}"><hk:data key="epp.cmparticle.nodatalist"/></c:if>
		<c:if test="${fn:length(list)>0}">
			<ul class="datalist">
				<c:forEach var="msg" items="${list}">
					<li>
						<div class="fl" style="width:600px">
							<span class="split-r">${msg.name }</span>
							<c:if test="${not empty msg.tel}">
								<span class="split-r">电话：${msg.tel }</span>
							</c:if>
							<c:if test="${not empty msg.im}">
								<span class="split-r">QQ/MSN：${msg.im }</span>
							</c:if>
							<c:if test="${not empty msg.email}">
								<span class="split-r">E-mail：${msg.email }</span>
							</c:if><br/>
							<span class="split-r"><fmt:formatDate value="${msg.createTime}" pattern="yyyy-MM-dd"/></span><br/>
							${msg.content }
						</div>
						<div class="fr" style="width:100px">
							<a href="javascript:delmsg(${msg.oid })">删除</a>
						</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#e5e6e8');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});

function delmsg(oid){
	if(window.confirm('确实要删除留言？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/org/msg_del.do?companyId=${companyId}&orgId=${orgId}&oid=${oid}",
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