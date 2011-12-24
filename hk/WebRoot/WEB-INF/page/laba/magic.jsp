<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="魔法 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">魔法</div>
	<c:if test="${canbomb}">
	<div class="hang"><hk:a href="/laba/op/op_bomblaba.do?${queryString }">炸掉这个喇叭</hk:a></div>
	<div class="hang"><hk:a href="/laba/op/op_pinklaba.do?${queryString }">推为精华</hk:a></div>
	</c:if>
	<div class="hang"><hk:a href="/laba/laba.do?${queryString }">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>