<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="${hkAd.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">${hkAd.name}</div>
	<div class="hang">发布地区：
		<c:if test="${city==null}">全球</c:if>
		<c:if test="${city!=null}">${city.city }</c:if>
	</div>
	<c:if test="${hkAd.viewCount>0}">
		<div class="hang">
		已展示${hkAd.viewCount }次
		</div>
	</c:if>
	<div class="hang odd">
		<c:if test="${hkAd.imageShow}">
			<a href="${hkAd.href }" target="_blank"><img src="${hkAd.imgUrl }"></a>
		</c:if>
		<c:if test="${!hkAd.imageShow}">
			<a href="${hkAd.href }" target="_blank">${hkAd.adData }</a>
		</c:if>
	</div>
	
	<div class="hang even">
		<hk:a href="/op/gg_update.do?oid=${hkAd.oid}">修改</hk:a><br/>
		<c:if test="${hkAd.pause}">
			<hk:a href="/op/gg_dorun.do?oid=${hkAd.oid}">运行</hk:a><br/>
		</c:if>
		<c:if test="${!hkAd.pause}">
			<hk:a href="/op/gg_pause.do?oid=${hkAd.oid}">暂停</hk:a><br/>
		</c:if>
		<hk:a href="/op/gg_del.do?oid=${hkAd.oid}">删除</hk:a><br/>
		<hk:a href="/op/gg_list.do">返回广告管理</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>