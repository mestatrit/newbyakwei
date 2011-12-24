<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${act.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		${act.name }<br/>
		<c:if test="${act.userId==loginUser.userId}">
			<hk:a href="/act/op/act_seluser.do?actId=${actId}"><hk:data key="view.sendgroupactsms"/></hk:a> 
			<hk:a href="/act/op/act_toedit.do?actId=${actId}"><hk:data key="view.edit"/></hk:a>
		</c:if><br/>
		<fmt:formatDate value="${act.beginTime}" pattern="yyyy-MM-dd HH:mm"/><hk:data key="view.begin"/><br/>
		<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/><hk:data key="view.end"/><br/><br/>
		<c:if test="${not empty act.addr}">
			<hk:data key="act.addr"/>:${act.addr }<br/>
		</c:if>
		<c:if test="${not empty act.intro}"><hk:data key="act.intro"/>:${act.intro }<br/><br/></c:if>
		<hk:data key="act.port"/>:<br/>${allport }<br/><br/>
		<hk:data key="view.act.joinactmethod"/>:<br/>
		<hk:data key="view.act.joinact_content" arg0="${actSysNum.sysnum }"/><br/><br/>
		<hk:data key="view.act.joinact_example"/>:<br/>
		<hk:data key="view.act.joinact_example_content"/><br/>
	</div>
	<div class="hang even"><hk:data key="view.act.userlist"/></div>
	<c:if test="${fn:length(userlist)>0}">
		<c:forEach var="au" items="${userlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${au.userId}">${au.user.nickName}</hk:a> 
				<c:if test="${adminact && au.userId!=loginUser.userId}">
					<span class="s">
						<c:if test="${au.passCheck}">
							<hk:a href="/act/op/act_checkunpassuser.do?actId=${actId}&userId=${au.userId }"><hk:data key="view.act.user.passcheck"/></hk:a>
						</c:if>
						<c:if test="${act.actNeedCheck && !au.passCheck}">
							<hk:a href="/act/op/act_checkpassuser.do?actId=${actId}&userId=${au.userId }"><hk:data key="view.act.user.unpasscheck"/></hk:a>
						</c:if>
						<hk:a href="/act/op/act_deluser.do?actId=${actId}&userId=${au.userId }"><hk:data key="view.okdel"/></hk:a>
					</span>
				</c:if>
			</div>
		</c:forEach>
		<c:if test="${showmore}"><div class="hang even"><hk:a href="/act/act_user.do?actId=${actId}"><hk:data key="view.more"/></hk:a></div></c:if>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>