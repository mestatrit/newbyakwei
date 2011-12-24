<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CompanyUserStatus"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">美发师 - ${company.name}</c:set>
<c:set var="js_value" scope="request">
	<meta name="keywords" content="美发师|${company.name }|<hk:data key="view2.website.title"/>" />
	<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/reserve.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="pl">
		<div class="mod">
			<div class="mod_title">美发师</div>
			<div class="mod_content">
				<ul class="actorlist2">
					<c:forEach var="actor" items="${actorlist}">
						<li idx="${actor.actorId }">
							<input id="actor_reserve_${actor.actorId }" type="button" class="btn_reserve abs" value="预约" onclick="reserve_actor(${companyId},${actor.actorId})"/>
							<div class="head">
								<c:if test="${not empty actor.picPath}">
									<a href="/cmp/${companyId }/actor/${actor.actorId}"><img src="${actor.pic150Url }" /></a>
								</c:if>
							</div>
							<div class="bdy">
								<a href="/cmp/${companyId }/actor/${actor.actorId}">${actor.name }</a> <hk:data key="view.cmpactor.gender${actor.gender}"/> ${actor.cmpActorRole.name }<br />
								${actor.intro }
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
				<div>
					<c:set var="url_rewrite" value="true" scope="request"></c:set>
					<c:set var="page_url" scope="request">/cmp/${companyId}/actor/list</c:set>
					<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="pr">
		<div class="mod">
			<a href="<%=path %>/h4/venue.do?companyId=${companyId}&mf=1">回到店铺首页</a>
		</div>
		<c:if test="${fn:length(svrlist)>0}">
			<div class="mod">
				<div class="mod_title">产品服务</div>
				<div class="mod_content">
					<ul class="datalist svrlist">
						<c:forEach var="svr" items="${svrlist}">
							<li idx="${svr.svrId }">
								<div class="svrname2">
									<a href="/cmp/${companyId }/service/${svr.svrId}">${svr.name }</a>
								</div>
								<div class="svrprice2">￥${svr.price }</div>
								<div class="clr"></div>
								<div id="svr_reserve_${svr.svrId }" class="svr_reserve2">
									<input type="button" class="btn_reserve" value="预约" onclick="reserve_svr(${companyId},${svr.svrId })"/>
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
	$('ul.svrlist li').bind('mouseenter', function(){
		$(this).css('background-color', '#ffffcc');
		var idx = $(this).attr('idx');
		$('#svr_reserve_' + idx).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).css('background-color', '#ffffff');
		var idx = $(this).attr('idx');
		$('#svr_reserve_' + idx).css('display', 'none');
	});
	$('ul.actorlist2 li').bind('mouseenter', function(){
		$(this).css('background-color', '#ffffcc');
		var idx = $(this).attr('idx');
		$('#actor_reserve_' + idx).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).css('background-color', '#ffffff');
		var idx = $(this).attr('idx');
		$('#actor_reserve_' + idx).css('display', 'none');
	});
});
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>