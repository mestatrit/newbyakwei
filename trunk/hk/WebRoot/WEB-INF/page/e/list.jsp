<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${sys_zone_pcity.city.city} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${cityId>0}"><c:set var="city_css">nn</c:set></c:if>
		<c:if test="${all>0}"><c:set var="all_css">nn</c:set></c:if>
		<hk:a clazz="${city_css}" href="/e/cmp_list.do?cityId=${sys_zone_pcityId}">${sys_zone_pcity.city.city }</hk:a> |
		<hk:a clazz="${all_css}" href="/e/cmp_list.do?all=1">全部</hk:a> | 
		<hk:a href="/index_changecity2.do">切换地区</hk:a>
	</div>
	<div class="hang even">
	<hk:form method="get" action="/e/cmp_list.do">
		<hk:hide name="cityId" value="${cityId}"/>
		<hk:text name="name" value="${name}"/>
		<hk:submit value="搜索"/>
	</hk:form>
	</div>
	<jsp:include page="../inc/companylist_inc.jsp"></jsp:include>
	<c:if test="${fn:length(companylist)>0}">
		<hk:simplepage2 href="/e/cmp_list.do?cityId=${cityId}&all=${all }&name=${enc_name }"/>
	</c:if>
	<c:if test="${fn:length(companylist)==0}">
		<hk:data key="view2.nocmpdata"/>
		<div class="hang">
		<hk:a href="/e/op/op_toadd.do?name=${enc_name }">创建足迹${name}</hk:a>
		</div>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>