<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.inputcityname"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form method="get" action="/e/cmp_searchcity.do">
			<hk:hide name="countryId" value="${countryId}"/>
			<hk:data key="view.inputcityname"/>:<br/>
			<hk:text name="name" value="${name}"/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<c:if test="${noresult}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/e/cmp_nearby.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>