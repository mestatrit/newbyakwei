<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title=" - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:data key="e.admin.cfmdelbizcle.tip"/></div>
	<div class="hang">
		<hk:form action="/e/admin/admin_cfmdelbizcle.do">
			<hk:hide name="circleId" value="${circleId}"/>
			<hk:hide name="cityId" value="${cityId}"/>
			<hk:hide name="provinceId" value="${provinceId}"/>
			<hk:submit name="ok" value="e.admin.cfmdelbizcle.ok" res="true"/> 
			<hk:submit name="cancel" value="e.admin.cfmdelbizcle.cancel" res="true"/>
		</hk:form>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>