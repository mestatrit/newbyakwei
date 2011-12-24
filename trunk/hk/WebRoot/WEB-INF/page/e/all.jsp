<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:rmBlankLines rm="true">
			<hk:a href="/e/cmp_list.do?w=all" clazz="nn"><hk:data key="view.all"/></hk:a>|
			<hk:a href="/e/cmp_list.do?w=city"><hk:data key="view.mycity"/></hk:a>|
			<hk:a href="/e/cmp_tosearchcity.do"><hk:data key="view.selectzone"/></hk:a>
		</hk:rmBlankLines>
	</div>
	<jsp:include page="../inc/companyvo.jsp"></jsp:include>
	<c:if test="${fn:length(companyvolist)>0}">
		<hk:simplepage2 href="/e/cmp_citycmp.do?cityId=${cityId}"/>
	</c:if>
	<c:if test="${fn:length(companyvolist)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>