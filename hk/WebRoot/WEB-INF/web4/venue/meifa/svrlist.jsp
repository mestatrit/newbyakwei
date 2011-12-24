<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CompanyUserStatus"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">产品和服务 - ${company.name}</c:set>
<c:set var="js_value" scope="request">
	<meta name="keywords" content="产品和服务|${company.name }|<hk:data key="view2.website.title"/>" />
	<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/reserve.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="pl">
		<div class="mod">
			<div class="mod_title">产品和服务</div>
			<div class="mod_content">
				<ul class="svrlist2">
					<c:forEach var="svr" items="${svrlist}">
						<li idx="${svr.svrId }">
							<div>
								<input id="svr_reserve_${svr.svrId }" type="button" class="btn_reserve abs" value="预约"  onclick="reserve_svr(${companyId},${svr.svrId })"/>
								<a class="split-r" href="/cmp/${companyId }/service/${svr.svrId}">${svr.name }</a> 
								<c:if test="${not empty svr.price}">￥${svr.price}</c:if>
							</div>
							${svr.intro }
						</li>
					</c:forEach>
				</ul>
				<div>
					<c:set var="url_rewrite" value="true" scope="request"></c:set>
					<c:set var="page_url" scope="request">/cmp/${companyId}/service/list</c:set>
					<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="pr">
		<div class="mod">
			<a href="<%=path %>/h4/venue.do?companyId=${companyId}&mf=1">回到店铺首页</a>
		</div>
		<c:if test="${fn:length(actorlist)>0}">
			<div class="mod">
				<div class="mod_title">美发师</div>
				<div class="mod_content">
					<ul class="datalist svrlist">
						<c:forEach var="actor" items="${actorlist}">
							<li idx="${actor.actorId }">
								<div class="svrname">
									<a href="/cmp/${companyId }/actor/${actor.actorId}">${actor.name } ${actor.cmpActorRole.name }</a>
								</div>
								<div id="actor_reserve_${actor.actorId }" class="svr_reserve">
									<input type="button" class="btn_reserve" value="预约" onclick="reserve_actor(${companyId},${actor.actorId})"/>
								</div>
								<div class="clr"></div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:if>
	</div>
	<div class="clr"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.svrlist2 li').bind('mouseenter', function(){
		$(this).addClass('bg2');
		$(this).css('background-color','#ffffcc');
		var idx = $(this).attr('idx');
		$('#svr_reserve_' + idx).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
		var idx = $(this).attr('idx');
		$('#svr_reserve_' + idx).css('display', 'none');
	});
	$('ul.svrlist li').bind('mouseenter', function(){
		$(this).addClass('bg2');
		var idx = $(this).attr('idx');
		$('#actor_reserve_' + idx).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).removeClass('bg2');
		var idx = $(this).attr('idx');
		$('#actor_reserve_' + idx).css('display', 'none');
	});
});
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>