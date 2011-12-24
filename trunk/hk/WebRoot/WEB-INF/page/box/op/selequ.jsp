<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.BoxPrize"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="选择道具 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">选择道具</div>
	<c:forEach var="e" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			${e.name } <hk:a href="/box/op/op_selequ.do?ch=1&eid=${e.eid}" needreturnurl="true">选择</hk:a>
		</div>
	</c:forEach>
	<hk:a href="${denc_return_url}">返回</hk:a>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>