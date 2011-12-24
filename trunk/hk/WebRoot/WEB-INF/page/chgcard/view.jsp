<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${o.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">${o.name}
	<c:if test="${loginUser.userId==o.userId}">
		<hk:a href="/chgcard/act_toedit.do?actId=${actId}"><hk:data key="view.edit"/></hk:a>
	</c:if>
	</div>
	<div class="hang even">
		<hk:form method="get" action="/chgcard/act_view.do">
			<hk:hide name="actId" value="${actId}"/>
			<hk:text name="key" maxlength="20" value="${key}"/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:forEach var="au" items="${ulist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/card/card.do?userId=${au.userId}&actId=${actId }">${au.userCard.simpleName}</hk:a>
			<c:if test="${o.userId==loginUser.userId && au.userId!=o.userId}">
			<hk:a href="/chgcard/act_deluser.do?actId=${actId}&userId=${au.userId }"><hk:data key="view.delete"/></hk:a> 
			<hk:a href="/chgcard/act_toeditactusername.do?actId=${actId}&userId=${au.userId }"><hk:data key="view.chgcard.editusername"/></hk:a>
			</c:if>
		</div>
	</c:forEach>
	<hk:simplepage href="/chgcard/act_view.do?actId=${actId}&key=${enc_key }"/>
	<div class="hang"><hk:a href="/chgcard/act.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>