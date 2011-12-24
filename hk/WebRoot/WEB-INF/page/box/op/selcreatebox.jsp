<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.box.createbox"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:data key="view.box.createbox"/></div>
	<div class="hang even">
		<div class="hang"><hk:a href="/box/op/op_toadd.do"><hk:data key="view.box.personalcreatebox"/></hk:a></div>
	<c:forEach var="c" items="${companylist}">
		<div class="hang"><hk:a href="/box/op/op_toadd.do?companyId=${c.companyId}">${c.name}</hk:a></div>
	</c:forEach>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>