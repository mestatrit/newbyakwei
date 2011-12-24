<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CompanyUserStatus"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">提供${cmpSvr.name}的人 - ${company.name}</c:set>
<c:set var="js_value" scope="request">
	<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/reserve.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="pl">
		<div class="mod">
			<div class="mod_title">提供${cmpSvr.name}的人</div>
			<div class="mod_content">
				<ul class="actorlist2">
					<c:forEach var="ref" items="${reflist}">
						<li idx="${ref.cmpActor.actorId }">
							<input id="actor_reserve_${ref.cmpActor.actorId }" type="button" class="btn_reserve abs" value="预约" onclick="reserve_actor(${companyId},${ref.actorId})"/>
							<div class="head">
								<c:if test="${not empty ref.cmpActor.picPath}">
									<a href="/cmp/${companyId }/actor/${ref.cmpActor.actorId}"><img src="${ref.cmpActor.pic150Url }" /></a>
								</c:if>
							</div>
							<div class="bdy">
								<a href="/cmp/${companyId }/actor/${ref.cmpActor.actorId}">${ref.cmpActor.name }</a> <hk:data key="view.cmpactor.gender${ref.cmpActor.gender}"/> ${ref.cmpActor.cmpActorRole.name }<br />
								${ref.cmpActor.intro }
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
				<div>
					<c:set var="page_url" scope="request"><%=path%>/h4/venue/actor_listforsvr.do?companyId=${companyId}&svrId=${svrId}</c:set>
					<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="pr">
		<div class="mod" align="center">
			<a href="<%=path %>/h4/venue.do?companyId=${companyId }&mf=1">回到店铺首页</a>
		</div>
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