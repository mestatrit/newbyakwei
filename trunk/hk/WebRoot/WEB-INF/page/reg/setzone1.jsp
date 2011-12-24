<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">选择所在地</div>
	<div class="hang odd">
		<c:forEach var="c" items="${clist}">
			<hk:a href="/next/op/op_setzone.do?cityId=${c.cityId}">${c.city}</hk:a> 
		</c:forEach>
	</div>
	<div class="hang"><hk:a href="/next/op/op_tosetzone0.do?countryId=${countryId}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>