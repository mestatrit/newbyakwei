<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">火酷网</c:set>
<c:set var="html_body_content" scope="request">
<style>
.adcharstyle{
font-size: 20px;
font-weight: bold;
}
</style>
<div>
	<div class="lcon2">
		<div class="inner">
			<c:if test="${fn:length(checkinlist)>0}">
				<div class="mod">
					<div class="mod_title">谁来过这里</div>
					<div class="mod_content">
						<c:forEach var="in" items="${checkinlist}">
							<div class="divrow">
								<div class="f_l">
									<a href="/user/${in.userId }/"><img src="${in.user.head48Pic }" alt="${in.user.nickName }" title="${in.user.nickName }"/></a>
								</div>
								<div class="f_l" style="padding-left: 5px;">
									<a href="/user/${in.userId }/">${in.user.nickName }</a><br/>
									<fmt:formatDate value="${in.uptime}" pattern="yy-MM-dd HH:mm"/>
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<c:if test="${fn:length(wantlist)>0}">
				<div class="mod">
					<div class="mod_title">谁想来这里</div>
					<div class="mod_content">
						<c:forEach var="in" items="${wantlist}">
							<div class="divrow">
								<div class="f_l">
									<a href="/user/${in.userId }/"><img src="${in.user.head48Pic }" alt="${in.user.nickName }" title="${in.user.nickName }"/></a>
								</div>
								<div class="f_l" style="padding-left: 5px;">
									<a href="/user/${in.userId }/">${in.user.nickName }</a>
								</div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
		</div>
	</div>
	<div class="rcon2">
		<div class="inner">
			<div class="mod">
				<div class="mod_title">你可能有兴趣的优惠券或广告</div>
				<div class="mod_content">
					<c:if test="${fn:length(couponlist)>0}">
						<c:forEach var="c" items="${couponlist}">
							<div class="divrow">
								<a href="/coupon/${c.couponId }" class="adcharstyle">${c.name }</a>
								<c:if test="${not empty c.picpath}">
									<div><a href="/coupon/${c.couponId }"><img src="${c.h_2Pic }"/></a></div>
								</c:if>
								${c.content }
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(hkadlist)>0}">
						<c:forEach var="ad" items="${hkadlist}">
							<div class="divrow">
								<c:if test="${!ad.imageShow}">
									<div><a href="${ad.href }" class="adcharstyle" target="_blank">${ad.name }</a></div>
									<a href="${ad.href }">${ad.adData }</a>
								</c:if>
								<c:if test="${ad.imageShow}">
									<a href="${ad.href }" target="_blank"><img src="${ad.imgUrl }" width="550"/></a>
								</c:if>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${showGoogleAd}">
						<script type="text/javascript"><!--
						google_ad_client = "pub-7451990910159208";
						/* 468x60, 创建于 10-4-27 */
						google_ad_slot = "3268756756";
						google_ad_width = 468;
						google_ad_height = 60;
						//-->
						</script>
						<script type="text/javascript"
						src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
						</script>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="clr"></div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>