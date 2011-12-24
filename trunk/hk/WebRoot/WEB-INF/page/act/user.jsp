<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${act.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">${act.name } <hk:data key="view.act.userlist"/></div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="au" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${au.userId}">${au.user.nickName}</hk:a> 
				<c:if test="${adminact && au.userId!=loginUser.userId}">
					<span class="s">
						<c:if test="${au.passCheck}">
							<hk:a href="/act/op/act_checkunpassuser.do?actId=${actId}&userId=${au.userId }" page="true"><hk:data key="view.act.user.unpasscheck"/></hk:a>
						</c:if>
						<c:if test="${act.actNeedCheck && !au.passCheck}">
							<hk:a href="/act/op/act_checkpassuser.do?actId=${actId}&userId=${au.userId }" page="true"><hk:data key="view.act.user.passcheck"/></hk:a>
						</c:if>
						<hk:a href="/act/op/act_deluser.do?actId=${actId}&userId=${au.userId }"><hk:data key="view.okdel"/></hk:a>
					</span>
				</c:if>
			</div>
		</c:forEach>
		<hk:simplepage href="/act/act_user.do?actId=${actId}"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/act/act.do?actId=${actId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>