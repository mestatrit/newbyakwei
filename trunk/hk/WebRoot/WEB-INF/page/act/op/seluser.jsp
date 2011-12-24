<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.act_selectuserforsendactsms"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:a href="/act/op/act_tosendsms.do?actId=${actId}"><hk:data key="view.act_sendactsmstoall"/></hk:a></div>
	<c:forEach var="vo" items="${volist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<c:if test="${vo.actUser.userId==loginUser.userId}">${vo.actUser.user.nickName}</c:if>
			<c:if test="${vo.actUser.userId!=loginUser.userId}">
				<c:if test="${vo.selected}">${vo.actUser.user.nickName} <span class="s"><hk:a href="/act/op/act_seluser.do?actId=${actId}&userId=${vo.actUser.userId }&del=1"><hk:data key="view.cancel"/></hk:a></span></c:if>
				<c:if test="${!vo.selected}"><hk:a href="/act/op/act_seluser.do?actId=${actId}&userId=${vo.actUser.userId }" page="true">${vo.actUser.user.nickName}</hk:a></c:if>
			</c:if>
			
		</div>
	</c:forEach>
	<hk:simplepage href="/act/op/act_seluser.do?actid=${actId}"/>
	<div class="hang even">
		<hk:form action="/act/op/act_seluser.do">
			<hk:hide name="actId" value="${actId}"/>
			<hk:hide name="selok" value="1"/>
			<hk:textarea name="nickNames" value="${userdata}" clazz="ipt" rows="5"/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/act/act.do?actId=${actId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>