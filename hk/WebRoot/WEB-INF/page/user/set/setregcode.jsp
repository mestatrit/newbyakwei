<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.setregcode"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${hasone}">
			<hk:data key="view.user_regcode"/>:<br/>
			${o.name }
		</c:if>
		<c:if test="${!hasone}">
			<hk:form action="/user/set/set_setregcode.do">
				<hk:data key="regcode.name"/>:<br/>
				<hk:text name="name" value="hk" maxlength="10"/><br/>
				<span class="ruo s"><hk:data key="regcode.name.tip"/></span><br/>
				<hk:submit value="view.submit" res="true"/>
			</hk:form>
			<br/>
			<c:if test="${fn:length(tuilist)>0}">
				<hk:data key="view.commendregcode"/>:<br/>
				<c:forEach var="r" items="${tuilist}">
				<hk:a href="/user/set/set_setregcode.do?name=${r.enc_name}">${r.name}</hk:a><br/>
				</c:forEach>
			</c:if>
		</c:if>
	</div>
	<div class="hang"><hk:a href="/user/set/set.do"><hk:data key="view.returntoset"/></hk:a></div>
	<div class="hang"><hk:a href="/invite/invite_toinvite.do"><hk:data key="view.returntoinvite"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>