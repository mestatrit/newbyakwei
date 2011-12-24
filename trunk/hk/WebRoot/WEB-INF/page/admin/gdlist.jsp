<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="地皮管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">地皮管理</div>
	<div class="hang even">
		<hk:form method="get" action="/admin/admin_gdlist.do">
			昵称:<br/>
			<hk:text name="nickName" value="${nickName}"/><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="ut" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
			<hk:a href="/admin/admin_toaddgd.do?userId=${ut.userId}">${ut.user.nickName}</hk:a> 
			${ut.groundCount }块
			</div>
		</c:forEach>
		<hk:simplepage href="/admin/admin_gdlist.do"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>