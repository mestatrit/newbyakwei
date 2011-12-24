<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${cmpUnion.name}</c:set>
<%CmpUnionSite cmpUnionSite=(CmpUnionSite)request.getAttribute("cmpUnionSite"); %>
<c:set var="index_1" scope="request">
	<c:if test="${loadmod_1}">
		<%JspDataUtil.loadCmpUnionBoard(request); %>
		<c:if test="${fn:length(cmpunionboardlist)>0}">
			<div class="nav2"><a name="${mod_1.module }" href="#${mod_1.nextModule }"><img src="<%=path %>/unionst/wap/img/down.gif"/></a>新闻</div>
			<div class="row">
				<c:forEach var="b" items="${cmpunionboardlist}">
					<p><img class="img-icon" src="<%=path %>/unionst/wap/img/arrow_grey.gif"/><hk:a href="/union/board.do?uid=${uid }&boardId=${b.boardId}">${b.title}</hk:a></p>
				</c:forEach>
			</div>
		</c:if>
	</c:if>
</c:set>
<c:set var="index_2" scope="request">
	<c:if test="${loadmod_2}">
		<%JspDataUtil.loadCmpUnionCmpAct(request); %>
		<c:if test="${fn:length(cmpactlist)>0}">
			<div class="nav2"><a name="${mod_2.module }" href="#${mod_2.nextModule }"><img src="<%=path %>/unionst/wap/img/down.gif"/></a>活动</div>
			<div class="row">
				<c:forEach var="act" items="${cmpactlist}">
					<p><img class="img-icon" src="<%=path %>/unionst/wap/img/arrow_grey.gif"/><hk:a href="/union/cmpact.do?uid=${uid }&actId=${act.actId}">${act.name}</hk:a></p>
				</c:forEach>
			</div>
		</c:if>
	</c:if>
</c:set>
<c:set var="index_3" scope="request">
	<c:if test="${loadmod_3}">
		<%JspDataUtil.loadCmpUnionBox(request); %>
		<c:if test="${fn:length(cmpunionboxlist)>0}">
			<div class="nav2"><a name="${mod_3.module }" href="#${mod_3.nextModule }"><img src="<%=path %>/unionst/wap/img/down.gif"/></a>宝箱</div>
			<div class="row">
				<c:forEach var="box" items="${cmpunionboxlist}">
					<p><img class="img-icon" src="<%=path %>/unionst/wap/img/arrow_grey.gif"/><hk:a href="/union/box.do?uid=${uid }&boxId=${box.boxId}">${box.name}</hk:a></p>
				</c:forEach>
			</div>
		</c:if>
	</c:if>
</c:set>
<c:set var="index_4" scope="request">
	<c:if test="${loadmod_4}">
		<%JspDataUtil.loadCmpUnionCoupon(request); %>
		<c:if test="${fn:length(cmpunioncouponlist)>0}">
		<div class="nav2"><a name="${mod_4.module }" href="#${mod_4.nextModule }"><img src="<%=path %>/unionst/wap/img/down.gif"/></a>优惠券</div>
		<div class="row">
			<c:forEach var="coupon" items="${cmpunioncouponlist}">
				<p><img class="img-icon" src="<%=path %>/unionst/wap/img/arrow_grey.gif"/><hk:a href="/union/coupon.do?uid=${uid}&couponId=${coupon.couponId }">${coupon.name}</hk:a></p>
			</c:forEach>
		</div>
		</c:if>
	</c:if>
</c:set>
<c:set var="index_5" scope="request">
	<c:if test="${loadmod_5}">
		<div class="nav2"><a name="${mod_5.module }" href="#${mod_5.nextModule }"><img src="<%=path %>/unionst/wap/img/down.gif"/></a>分类推荐|<hk:a href="/union/kind_list.do?uid=${uid}">全部分类</hk:a></div>
		<div class="row">
			<%JspDataUtil.loadCmpUnionCmdKind(request); %>
			<c:if test="${fn:length(cmdkindlist)>0}"><c:forEach var="k" items="${cmdkindlist}"><hk:a clazz="split-r" href="/union/kind.do?uid=${uid}&kindId=${k.kindId }&p=1">${k.name}</hk:a></c:forEach></c:if>
		</div>
	</c:if>
</c:set>
<c:set var="index_6" scope="request">
	<c:if test="${loadmod_6}">
		<%JspDataUtil.loadCmpUnionLink(request); %>
		<c:if test="${fn:length(cmpunionlinklist)>0}">
		<div class="nav2"><a name="${mod_6.module }" href="#${mod_6.nextModule }"><img src="<%=path %>/unionst/wap/img/down.gif"/></a>友情链接</div>
		<div class="row">
		<a class="split-r" href="http://www.huoku.com">火酷</a><c:forEach var="l" items="${cmpunionlinklist}"><a href="${l.url }" class="split-r">${l.title }</a></c:forEach>
		</div>
		</c:if>
	</c:if>
</c:set>
<%CmpUnionModuleUtil.buildSite(request); %>