<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="频道 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:if test="${show=='hot'}"><c:set var="hotnn" value="nn"></c:set></c:if>
	<c:if test="${show=='num'}"><c:set var="numnn" value="nn"></c:set></c:if>
	<c:if test="${empty show}"><c:set var="newnn" value="nn"></c:set></c:if>
	<div class="hang">
		<hk:rmBlankLines rm="true">
			<hk:a href="/tag/utlist.do?userId=${userId}&show=hot" clazz="${hotnn}">热门</hk:a>|
			<hk:a href="/tag/utlist.do?userId=${userId}&show=num" clazz="${numnn}">数量</hk:a>|
			<hk:a href="/tag/utlist.do?userId=${userId}" clazz="${newnn}">最新</hk:a>
		</hk:rmBlankLines>
	</div>
	<c:if test="${fn:length(list)>0}">
		<div class="hang odd">
			<c:forEach var="tag" items="${list}">
			<hk:a href="/laba/taglabalist.do?tagId=${tag.tagId}">#${tag.name}</hk:a> 
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${fn:length(list)>0}"><hk:simplepage href="/tag/utlist.do?userId=${userId}&show=${show }"/></c:if>
	<c:if test="${fn:length(list)==0}">没有数据显示</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>