<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="频道 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
		<div class="hang even">
			<hk:form action="/tag/op/op_add.do">
				<hk:text name="name" maxlength="15"/><br/>
				<hk:submit value="创建频道"/>
			</hk:form>
		</div>
	<c:if test="${fn:length(list)>0}">
		<div class="hang even">我的频道</div>
		<div class="hang odd">
			<c:forEach var="tag" items="${list}">
			<hk:a href="/laba/taglabalist.do?tagId=${tag.tagId}">#${tag.name}</hk:a> 
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${more}"><div class="hang even"><hk:a href="/tag/utlist.do?userId=${loginUser.userId}&hot=1"><hk:data key="view.more"/></hk:a></div></c:if>
	<c:if test="${fn:length(hotlist)>0}">
		<div class="hang even">热门频道</div>
		<div class="hang odd">
			<c:forEach var="tag" items="${hotlist}">
			<hk:a href="/laba/taglabalist.do?tagId=${tag.tagId}">#${tag.name}</hk:a> 
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${hotmore}"><div class="hang even"><hk:a href="/tag/utlist_hot.do?hot=1"><hk:data key="view.more"/></hk:a></div></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>