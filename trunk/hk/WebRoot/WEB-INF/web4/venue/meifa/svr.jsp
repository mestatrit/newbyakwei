<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CompanyUserStatus"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${cmpSvr.name } - ${company.name}</c:set>
<c:set var="js_value" scope="request">
	<meta name="keywords" content="${cmpSvr.name }|${company.name }|<hk:data key="view2.website.title"/>" />
	<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/jcarousel/skins/tango/skin.css"/>
	<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/jquery.jcarousel.min.js"></script>
	<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/reserve.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="mod">
		<h1>${cmpSvr.name }</h1>
		<div class="mod_content">
			<div class="f_l" style="width: 600px; text-align: center;">
				<div id="bigimgarea" class="mod">
					<c:forEach var="ref" items="${photoreflist}" end="0">
					<img id="bigimg" src="${ref.companyPhoto.pic640 }" />
					</c:forEach>
				</div>
				<div>
					<ul id="mycarousel" class="jcarousel-skin-tango">
						<c:forEach var="ref" items="${photoreflist}">
						<li><a href="javascript:showbig('${ref.companyPhoto.pic640 }')"><img src="${ref.companyPhoto.pic60 }" /></a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div class="f_r" style="width: 240px">
				<div class="mod">
					<a href="<%=path %>/h4/venue.do?companyId=${companyId}&mf=1">回到店铺首页</a>
				</div>
				<div class="mod">
					<input type="button" class="btn" value="预约" style="margin-right: 30px;" onclick="reserve_svr(${companyId},${svrId })"/>
					<input type="button" class="btn" value="返回" onclick="tourl('/cmp/${companyId}/service/list')"/>
				</div>
				<div class="mod">
					<div class="mod_title">服务介绍</div>
					<div class="mod_content">
						${cmpSvr.intro }
					</div>
				</div>
				<c:if test="${fn:length(actorsvrreflist)>0}">
					<div class="mod">
						<div class="mod_title">提供服务人
						</div>
						<div class="mod_content">
							<ul class="datalist actor">
								<c:forEach var="ref" items="${actorsvrreflist}">
									<li idx="${ref.oid }">
										<div class="svrname">
											<a href="/cmp/${companyId }/actor/${ref.actorId}">${ref.cmpActor.name } ${ref.cmpActor.cmpActorRole.name }</a>
										</div>
										<div id="actor_reserve_${ref.oid }" class="svr_reserve">
											<input type="button" class="btn_reserve" value="预约" onclick="reserve_actor(${companyId},${ref.actorId})"/>
										</div>
										<div class="clr"></div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<c:if test="${fn:length(svrlist)>0}">
					<div class="mod">
						<div class="mod_title">
							其他产品服务
						</div>
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
		</div>
	</div>
<script type="text/javascript">
	$(document).ready(function(){
		$('ul.datalist li').bind('mouseenter', function(){
			var idx = $(this).attr('idx');
			$('#svr_' + idx).css('display', 'block');
		}).bind('mouseleave', function(){
			var idx = $(this).attr('idx');
			$('#svr_' + idx).css('display', 'none');
		});
		$('ul.svrlist li').bind('mouseenter', function(){
			$(this).css('background-color','#ffffcc');
			var idx = $(this).attr('idx');
			$('#svr_reserve_' + idx).css('display', 'block');
		}).bind('mouseleave', function(){
			$(this).css('background-color','#ffffff');
			var idx = $(this).attr('idx');
			$('#svr_reserve_' + idx).css('display', 'none');
		});
		$('ul.actor li').bind('mouseenter', function(){
			$(this).css('background-color','#ffffcc');
			var idx = $(this).attr('idx');
			$('#actor_reserve_' + idx).css('display', 'block');
		}).bind('mouseleave', function(){
			$(this).css('background-color','#ffffff');
			var idx = $(this).attr('idx');
			$('#actor_reserve_' + idx).css('display', 'none');
		});
		$('#mycarousel').jcarousel({
			vertical: false,
			scroll: 1,
			visible: 6,
			buttonNextHTML: '<a id="next" class="divbtn">上一个</a>',
			buttonPrevHTML: '<a id="prev" class="divbtn">下一个</a>'
		});
	});
	function showbig(url){
		getObj('bigimg').src=url;
	}
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>