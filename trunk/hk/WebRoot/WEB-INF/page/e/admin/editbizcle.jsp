<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.admin.editbizcle.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<hk:form action="/e/admin/admin_editbizcle.do">
		<hk:hide name="circleId" value="${circleId}"/>
		<hk:hide name="cityId" value="${cityId}"/>
		<hk:hide name="provinceId" value="${provinceId}"/>
		<div class="hang even"><hk:data key="bizcircle.name"/></div>
		<div class="hang odd"><hk:text name="name" value="${o.name}"/></div>
		<hk:submit value="e.admin.editbizcle.sbumit" res="true"/>
	</hk:form>
	<div class="hang"><hk:a href="/e/admin/admin_bizclelist.do?cityId=${cityId }&provinceId=${provinceId }"><hk:data key="e.admin.editbizcle.tobizclelist"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>