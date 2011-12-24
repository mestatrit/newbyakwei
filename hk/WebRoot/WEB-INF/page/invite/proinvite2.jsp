<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="红地毯 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">为已经在火酷的用户创建红地毯</div>
	<div class="hang">
		<hk:form action="/invite/invite_finduser.do">
			输入昵称进行查找:<br/><a name="#finduser"></a>
			<hk:text name="nickName" value="${nickName}" maxlength="20"/><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<div class="hang">
		<c:if test="${user2==null}">此用户不存在</c:if>
		<c:if test="${user2!=null}"><hk:a href="/invite/invite_addproinvite2.do?userId=${user2.userId}">${user2.nickName}</hk:a></c:if>
	</div>
	<div class="hang"><hk:a href="/invite/invite_toproinvite.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>