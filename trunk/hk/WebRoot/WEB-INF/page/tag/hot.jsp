<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="频道 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang odd">
	<hk:rmBlankLines rm="true">
		<c:if test="${hot==1}">
			<hk:a href="/tag/utlist_hot.do?hot=1" clazz="nn">热门</hk:a>|
			<hk:a href="/tag/utlist_hot.do">数量</hk:a>
		</c:if>
		<c:if test="${hot==0}">
			<hk:a href="/tag/utlist_hot.do?hot=1">热门</hk:a>|
			<hk:a href="/tag/utlist_hot.do" clazz="nn">数量</hk:a>
		</c:if>
	</hk:rmBlankLines>
	</div>
	<div class="hang odd">
		<c:if test="${fn:length(list)>0}">
			<c:forEach var="tag" items="${list}">
			<hk:a href="/laba/taglabalist.do?tagId=${tag.tagId}">#${tag.name}</hk:a> 
			</c:forEach>
		</c:if>
	</div>
	<c:if test="${fn:length(list)>0}"><div class="hang even"><hk:simplepage href="/tag/utlist_hot.do?hot=${hot }"/></div></c:if>
	<c:if test="${fn:length(list)==0}">没有数据显示</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>