<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${!listall}">
	<c:set var="css_city" value="nn"/>
	<c:if test="${city!=null}"><c:set var="view_title" value="${city.city}"/></c:if>
	<c:if test="${province!=null}"><c:set var="view_title" value="${province.province}"/></c:if>
</c:if>
<c:if test="${listall}"><c:set var="css_all" value="nn"/><c:set var="view_title"><hk:data key="view.zone_all"/></c:set></c:if>
<c:if test="${city!=null}"><c:set var="zoneinfo" value="${city.city}"/></c:if>
<c:if test="${province!=null}"><c:set var="zoneinfo" value="${province.province}"/></c:if>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:rmBlankLines rm="true">
			<hk:a href="/e/cmp_list.do?kindId=${kindId }" clazz="${css_all}"><hk:data key="view.zone_all"/></hk:a>|
			<c:if test="${city!=null || province!=null}">
				<hk:a href="/e/cmp_list.do?cityId=${cityId}&provinceId=${provinceId }&kindId=${kindId }" clazz="${css_city}">${zoneinfo}</hk:a>|
				</c:if>
			<hk:a href="/index_tosearchcity.do?kindId=${kindId }&fn=1"><hk:data key="view.selectcityzone"/></hk:a>
		</hk:rmBlankLines>
	</div>
	<div class="hang"><c:if test="${kindId>0}">${kind.name }</c:if></div>
	<jsp:include page="../inc/companyvo.jsp"></jsp:include>
	<c:if test="${fn:length(companyvolist)>0}">
		<hk:simplepage href="/e/cmp_list2.do?cityId=${cityId}&provinceId=${provinceId }&kindId=${kindId }"/>
	</c:if>
	<c:if test="${fn:length(companyvolist)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>