<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="VIP用户 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">VIP用	</div>
	<div class="hang">
	<hk:form action="/admin/adminvipuser_create.do">
		昵称：<hk:text name="nickName" value="${nickName}"/>
		<hk:submit value="创建"/>
	</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="vip" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
			<hk:a href="/home.do?userId=${vip.userId}">${vip.user.nickName}</hk:a> 
			${vip.user.domain } 
			<hk:a href="/admin/adminvipuser_del.do?oid=${vip.oid}">取消VIP</hk:a>
			</div>
		</c:forEach>
		<hk:simplepage2 href="/admin/adminvipuser.do"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>