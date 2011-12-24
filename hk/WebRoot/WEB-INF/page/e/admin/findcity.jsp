<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.admin.findcity.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
	<hk:form method="get" action="/e/admin/admin_findcity.do">
		<hk:hide name="f" value="${f}"/>
		<hk:data key="e.admin.findcity.cityname"/><br/>
		<hk:text name="city" value="${city}"/><br/>
		<hk:submit value="e.admin.findcity.sbumit" res="true"/>
	</hk:form>
	</div>
	<c:if test="${fn:length(clist)>0}">
		<c:set var="addstr" value="f=${f }&city=${enc_city}"/>
		<div class="hang odd">
		<c:forEach var="c" items="${clist}">
			<hk:a href="/e/admin/admin_opcity.do?cityId=${c.cityId}&${addstr }">${c.city }</hk:a> 
		</c:forEach>
		</div>
	</c:if>
	<c:if test="${fn:length(plist)>0}">
		<c:set var="addstr" value="f=${f }&city=${enc_city}"/>
		<div class="hang odd">
		<c:forEach var="c" items="${plist}">
			<hk:a href="/e/admin/admin_opcity.do?provinceId=${c.provinceId}&${addstr }">${c.province }</hk:a> 
		</c:forEach>
		</div>
	</c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>