<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<div class="pl" style="overflow: hidden;">
<c:if test="${parent_cmpNav!=null}">
	<div class="leftnav">
		<div class="tit">${parent_cmpNav.name }</div>
		<div class="con">
			<c:forEach var="child_nav" items="${children}">
				<div class="inner">
					<ul class="nav_1">
						<li>
							<a href="/column/${companyId }/${child_nav.oid}/"<c:if test="${navId==child_nav.oid}"> class="sel"</c:if>>${child_nav.name }</a>
							<c:if test="${child_nav.oid==navId && fn:length(before_cmparticle_list)>0 && fn:length(after_cmparticle_list)>0}">
								<ul class="nav_2">
									<c:forEach var="ar" items="${before_cmparticle_list}">
										<li style="margin-bottom: 10px"><a style="font-size: 12px;line-height: 12px;" class="normal" href="/article/${companyId }/${navId}/${ar.oid}.html">${ar.title }</a></li>
									</c:forEach>
									<c:forEach var="ar" items="${after_cmparticle_list}">
										<li style="margin-bottom: 10px"><a style="font-size: 12px;line-height: 12px;" class="normal" href="/article/${companyId }/${navId}/${ar.oid}.html">${ar.title }</a></li>
									</c:forEach>
								</ul>
							</c:if>
						</li>
					</ul>
				</div>
			</c:forEach>
		</div>
	</div>
</c:if>
<c:if test="${parent_cmpNav==null}">
	<%EppViewUtil.loadP2CmpAdList(request,5); %>
	<c:forEach var="p2cmpadref" items="${p2cmpadreflist}">
		<div class="mod">
			<c:if test="${p2cmpadref.cmpAd.imageAd}">
				<a target="_blank" href="http://${p2cmpadref.cmpAd.url }"><img src="${p2cmpadref.cmpAd.picUrl }"/></a>
			</c:if>
			<c:if test="${p2cmpadref.cmpAd.textAd}">
				<a target="_blank" href="http://${p2cmpadref.cmpAd.url }">${p2cmpadref.cmpAd.name }</a>
			</c:if>
			<c:if test="${p2cmpadref.cmpAd.htmlAd}">
				${p2cmpadref.cmpAd.html }
			</c:if>
		</div>
	</c:forEach>
</c:if>
</div>