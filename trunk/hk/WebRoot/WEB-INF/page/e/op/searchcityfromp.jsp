<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.selbizcircle"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<c:set var="addstr" value="companyId=${companyId}&provinceId=${provinceId }"></c:set>
	<div class="hang odd">
		<c:forEach var="c" items="${list}">
			<hk:a href="/e/op/op_bizcirclelist.do?cityId=${c.cityId}&${addstr }">${c.city }</hk:a> 
		</c:forEach>
	</div>
	<div class="hang"><hk:a href="/e/op/op_searchcity.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>