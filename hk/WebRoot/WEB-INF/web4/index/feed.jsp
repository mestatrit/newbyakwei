<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.CmpTip"%><%@page import="com.hk.frame.util.DataUtil"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><c:set var="js_value" scope="request"><script type="text/javascript">
<c:if test="${fn:length(feedvolist)>10}">
var delay = 5000;
function shift() {
	$('.item:last-child').remove().css('display', 'none').prependTo('#items');
	$('.item:first-child').slideDown(1000);
	$('.item:last-child').css('display', 'none');
	setTimeout('shift()', delay);
}
$(document).ready(function() {
	setTimeout('shift()', delay);
});
</c:if>
</script>
<style type="text/css">
div#items{height: 850px;overflow: hidden;}
div.item {clear: both;font-size: 14px;padding: 10px 0 3px 0;border-bottom: 1px black dotted;}
div.item a{font-weight: bold;}
div.i_img {float: left;width: 51px;margin-right: 10px;}
div.i_img img {padding: 2px;border: 1px #999999 solid;}
div.i_con {float: left;width: 391px;}
div.i_icn {float: left;	width: 57px;margin-left: 10px;}
</style>
</c:set><c:set var="html_title" scope="request"><hk:data key="view2.website.title" /></c:set><c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div style="font-size: 20px" class="bdtm b">
			<c:if test="${cityflg==0}">
				<hk:data key="view2.all_feed"/> |
				<a href="/feed/city"><hk:data key="view2.current_city"/></a>
			</c:if>
			<c:if test="${cityflg==1}">
				<a href="/feed"><hk:data key="view2.feed.recent"/></a> |
				<hk:data key="view2.current_city"/>
			</c:if>
			</div>
			<div id="items">
				<c:if test="${fn:length(feedvolist)==0}">
				<div style="font-size: 16px;font-weight: bold;"><hk:data key="view2.nofeeddata_in_city"/></div>
				</c:if>
				<c:if test="${fn:length(feedvolist)<=10}">
					<c:forEach var="vo" items="${feedvolist}" varStatus="idx">
						<div class="item">
							<div class="i_img"><a href="/user/${vo.first.userId }/"><img height="48" width="48" src="${vo.first.user.head48Pic }" alt="${vo.first.user.nickName }" title="${vo.first.user.nickName }"/></a></div>
							<div class="i_con">
								<div>
									<a href="/user/${vo.first.userId }/">${vo.first.user.nickName }</a>：
								</div>
								<div>
									${vo.content }
								</div>
							</div>
							<div class="i_icn">
								<c:if test="${vo.first.createVenue}">
									<a href="/venue/${vo.first.companyId}"><img src="<%=path %>/webst4/img/icon_tip.png" title="<hk:data key="view2.lookonclick"/>" alt="<hk:data key="view2.lookonclick"/>"/></a>
								</c:if>
								<c:if test="${vo.first.writeTips}">
									<a href="/item/${vo.first.tipId }"><img src="<%=path %>/webst4/img/icon_tip.png" title="<hk:data key="view2.lookonclick"/>" alt="<hk:data key="view2.lookonclick"/>"/></a>
								</c:if>
								<c:if test="${vo.first.getBadge}">
								<c:if test="${vo.first.userBadge.oid==0}">
									<a href="<%=path %>/h4/user_findbadge.do?userId=${vo.first.userId }&badgeId=${vo.first.userBadge.badgeId }"><img src="${vo.first.userBadge.pic57 }" title="${vo.first.userBadge.name }：${vo.first.userBadge.intro}" alt="${vo.first.userBadge.name }：${vo.first.userBadge.intro}"/></a>
								</c:if>
								<c:if test="${vo.first.userBadge.oid>0}">
									<a href="/userbadge/${vo.first.userBadge.oid }"><img src="${vo.first.userBadge.pic57 }" title="${vo.first.userBadge.name }：${vo.first.userBadge.intro}" alt="${vo.first.userBadge.name }：${vo.first.userBadge.intro}"/></a>
								</c:if>
								</c:if>
								<c:if test="${vo.first.becomeMayor}">
									<a href="/venue/${vo.first.companyId }/"><img src="<%=path %>/webst4/img/icon_mayor.png" title="<hk:data key="view2.lookonclick"/>" alt="<hk:data key="view2.lookonclick"/>"/></a>
								</c:if>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(feedvolist)>10}">
					<c:forEach var="vo" items="${feedvolist}" varStatus="idx">
						<div style="<c:if test="${idx.index>=9}">display:none</c:if>" id="recent${idx.index }" class="item">
							<div class="i_img"><a href="/user/${vo.first.userId }/"><img height="48" width="48" src="${vo.first.user.head48Pic }" alt="${vo.first.user.nickName }" title="${vo.first.user.nickName }"/></a></div>
							<div class="i_con">
								<div>
									<a href="/user/${vo.first.userId }/">${vo.first.user.nickName }</a>:
								</div>
								<div>
									${vo.content }
								</div>
							</div>
							<div class="i_icn">
								<c:if test="${vo.first.createVenue}">
									<a href="/venue/${vo.first.companyId}"><img src="<%=path %>/webst4/img/icon_tip.png" title="<hk:data key="view2.lookonclick"/>" alt="<hk:data key="view2.lookonclick"/>"/></a>
								</c:if>
								<c:if test="${vo.first.writeTips}">
									<a href="/item/${vo.first.tipId }"><img src="<%=path %>/webst4/img/icon_tip.png" title="<hk:data key="view2.lookonclick"/>" alt="<hk:data key="view2.lookonclick"/>"/></a>
								</c:if>
								<c:if test="${vo.first.getBadge}">
									<a href="/userbadge/${vo.first.userBadge.oid }"><img src="${vo.first.userBadge.pic57 }" title="${vo.first.userBadge.name }：${vo.first.userBadge.intro}" alt="${vo.first.userBadge.name }：${vo.first.userBadge.intro}"/></a>
								</c:if>
								<c:if test="${vo.first.becomeMayor}">
									<a href="/venue/${vo.first.companyId }/"><img src="<%=path %>/webst4/img/icon_mayor.png" title="<hk:data key="view2.lookonclick"/>" alt="<hk:data key="view2.lookonclick"/>"/></a>
								</c:if>
							</div>
							<div class="clr"></div>
						</div>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<div class="mod">
				<input type="button" value="<hk:data key="view2.addvenueandtip"/>" onclick="tourl('/venue/search')" class="btn"/>
			</div>
			<c:if test="${loginUser==null}">
				<div class="mod">
					<div class="rd" style="width:250px;background-color: #999999;text-align: center;">
					<div style="padding:15px 10px;color:#fff">
					<span style="color: #fff;font-size:14px;font-weight: bold;">
						<hk:data key="view2.toreg1"/>，<br/>
						<a style="color: #fff;text-decoration: underline; " href="/signup"><hk:data key="view2.toreg2"/></a>
					</span>
					</div>
					</div>
				</div>
			</c:if>
			<div class="mod">
			<div class="mod_title"><hk:data key="view2.people.onwhere"/></div>
			<div class="mod_content">
				<c:forEach var="log" items="${cmpCheckInUserLogList}">
					<div class="divrow bdtm">
						<a class="b" href="/user/${log.userId }/">${log.user.nickName }</a>
						<hk:data key="view2.user.on"/>
						<a class="b" href="/venue/${log.companyId }/">${log.company.name }</a> 
						<c:set var="createtime" scope="request" value="${log.createTime}"/>
						<span class="ruo"><%=JspDataUtil.outLabaTime(request,"createtime")%></span>
					</div>
				</c:forEach>
			</div>
			</div>
		</div>
	</div>
	<div class="clr"></div>
</div></c:set><jsp:include page="../inc/frame.jsp"></jsp:include>