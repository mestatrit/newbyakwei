<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.CmpTip"%><%@page import="com.hk.frame.util.DataUtil"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="js_value" scope="request">
<meta name="keywords" content="<hk:data key="view2.website.title"/>" />
<meta name="description" content="通过火酷，您可以预约您周围的生活服务，避免等待，还能享受折扣。|<hk:data key="view2.website.title"/>"/>
<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/reserve.js"></script>
</c:set>
<c:set var="html_title" scope="request">
	<hk:data key="view2.website.title" />
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="pl">
		<div class="mod kuactor">
			<div class="kuimg">
				<c:forEach var="pinkactor" items="${pinkactorlist}">
					<div id="bigimg_${pinkactor.oid }" style="display: none;">
						<a href="/cmp/${pinkactor.cmpActor.companyId }/actor/${pinkactor.actorId}"><img src="${pinkactor.cmpActor.pic500Url }" width="320" height="320" /></a>
					</div>
				</c:forEach>
			</div>
			<ul class="kulist">
				<c:forEach var="pinkactor" items="${pinkactorlist}">
					<li idx="${pinkactor.oid }">
						<div id="arrow_${pinkactor.oid }" class="arrow"></div>
						<div class="inpad">
							<div class="head">
								<a href="/cmp/${pinkactor.cmpActor.companyId }/actor/${pinkactor.actorId}"><img src="${pinkactor.cmpActor.pic60Url }" /></a>
							</div>
							<div class="info">
								<div id="ku_reserve_${pinkactor.oid }" class="svr_reserve">
									<input type="button" class="btn_reserve" value="预约" onclick="reserve_actor(${pinkactor.cmpActor.companyId },${pinkactor.actorId })"/>
								</div>
								<a href="/cmp/${pinkactor.cmpActor.companyId }/actor/${pinkactor.actorId}">${pinkactor.cmpActor.name }</a>
								<br />
								${pinkactor.cmpActor.cmpActorRole.name }
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
			<div class="clr"></div>
		</div>
		<div class="mod m2">
			<ul class="actorlist">
				<c:forEach var="actor" items="${kuactorlist}">
					<li>
						<div class="p" idx="${actor.actorId }">
							<div id="actor_${actor.actorId }" class="reserve">
								<a href="javascript:reserve_actor(${companyId },${actor.actorId })">预约</a>
							</div>
							<a href="/cmp/${actor.companyId }/actor/${actor.actorId }"><img src="${actor.pic150Url }" width="120" height="150"/></a>
						</div>
						<p><a href="/cmp/${actor.companyId }/actor/${actor.actorId }">${actor.name }</a></p>
						<p>${actor.cmpActorRole.name }</p>
					</li>
				</c:forEach>
			</ul>
			<div class="clr"></div>
		</div>
		<div class="mod">
			<div class="mod_title">点评</div>
			<div class="mod_content">
				<ul class="reviewlist">
					<li>
						<div class="head">
							<a href="#"><img src="img/h48.jpg" /> </a><a href="#">昵称</a>
						</div>
						<div class="review">
							点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容 点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容 点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容
						</div>
						<div class="clr"></div>
					</li>
					<li>
						<div class="head">
							<a href="#"><img src="img/h48.jpg" /> </a><a href="#">昵称</a>
						</div>
						<div class="review">
							点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容 点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容 点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容
						</div>
						<div class="clr"></div>
					</li>
					<li>
						<div class="head">
							<a href="#"><img src="img/h48.jpg" /> </a><a href="#">昵称</a>
						</div>
						<div class="review">
							点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容 点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容 点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容
						</div>
						<div class="clr"></div>
					</li>
					<li>
						<div class="head">
							<a href="#"><img src="img/h48.jpg" /> </a><a href="#">昵称</a>
						</div>
						<div class="review">
							点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容 点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容 点评内容点评内容点评内容点评内容点评内容点评内容点评内容点评内容
						</div>
						<div class="clr"></div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="pr">
		<div class="mod">
			<div style="font-size: 16px; margin-bottom: 30px;">
				通过火酷，您可以预约您周围的生活服务，避免等待，还能享受折扣。
			</div>
			<div>
				<input type="button" class="btn f_l" value="添加商户" onclick="toauthcmp()"/>
				<c:if test="${!userLogin}">
				<input type="button" class="btn f_r" value="注册|登录" onclick="tourl('/login')"/>
				</c:if>
				<div class="clr"></div>
			</div>
		</div>
		<!-- 
			<div class="mod">
				<div class="mod_title">
					公告
				</div>
				<div class="mod_content">
					公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容公告内容
				</div>
			</div>
		 -->
		<c:if test="${fn:length(couponlist)>0}">
			<div class="mod">
				<div class="mod_title">优惠券</div>
				<div class="mod_content">
					<ul class="rowlist2">
						<c:forEach var="coupon" items="${couponlist}">
							<li><a href="#">优惠券1</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:if>
		<c:if test="${fn:length(kucmplist)>0}">
			<div class="mod">
				<div class="mod_title">最火商户</div>
				<div class="mod_content">
					<ul class="rowlist2">
						<c:forEach var="cmp" items="${kucmplist}">
							<li><a href="<%=path %>/h4/venue.do?companyId=${cmp.companyId}&mf=1">${cmp.name }</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:if>
		<c:if test="${fn:length(newcmplist)>0}">
			<div class="mod">
				<div class="mod_title">最新商户</div>
				<div class="mod_content">
					<ul class="rowlist2">
						<c:forEach var="cmp" items="${newcmplist}">
							<li><a href="<%=path %>/h4/venue.do?companyId=${cmp.companyId}&mf=1">${cmp.name }</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:if>
	</div>
	<div class="clr"></div>
<script type="text/javascript">
function toauthcmp(){
	tourl('<%=path %>/h4/op/authcmp.do?return_url='+encodeLocalURL());
}
var lastbigimg = -1;
var lastbigimg_li = null;
function showbigimg(li){
	var idx = $(li).attr('idx');
	$(li).css('background-color', '#e4e4e4');
	$('#arrow_' + lastbigimg).css('display', 'none');
	$('#bigimg_' + lastbigimg).css('display', 'none');
	$('#ku_reserve_' + lastbigimg).css('display', 'none');
	$(lastbigimg_li).css('background-color', '#ffffff');
	$('#ku_reserve_' + idx).css('display', 'block');
	$('#arrow_' + idx).css('display', 'block');
	$('#bigimg_' + idx).css('display', 'block');
	lastbigimg = idx;
	lastbigimg_li = li;
}
$(document).ready(function() {
	$('ul.kulist li').bind('mouseenter', function() {
		showbigimg(this);
	}).bind('mouseleave', function() {
		var idx = $(this).attr('idx');
		$('#ku_reserve_' + idx).css('display', 'none');
	});
	$('ul.actorlist li .p').bind('mouseenter', function() {
		var idx = $(this).attr('idx');
		$('#actor_' + idx).css('display', 'block');
	}).bind('mouseleave', function() {
		var idx = $(this).attr('idx');
		$('#actor_' + idx).css('display', 'none');
	});
	$('ul.reviewlist li,ul.rowlist2 li').bind('mouseenter', function() {
		$(this).css('background-color', '#ffffcc');
	}).bind('mouseleave', function() {
		$(this).css('background-color', '#ffffff');
	});
	showbigimg($("ul.kulist li:first-child"));
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>