<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
	<hk:form method="get" action="/admin/admin_adddeffollowuser.do">
		昵称:<br/>
		<hk:text name="nickName"/><br/>
		<hk:submit value="添加"/>
	</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="def" items="${list}">
			<div class="hang ${clazz_var }">
			${def.user.nickName } 
			<hk:a href="/admin/admin_deldeffollowuser.do?userId=${def.userId}"><hk:data key="view.delete"/> </hk:a>
			</div>
		</c:forEach>
		<hk:simplepage href="/admin/admin_deffollowuser.do"/>
	</c:if>
	<div class="hang">
		<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>