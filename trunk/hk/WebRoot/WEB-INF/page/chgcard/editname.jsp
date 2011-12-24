<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:data key="view.chgcard.editingusername" arg0="${user.nickName}"/></div>
	<div class="hang even">
		<hk:form action="/chgcard/act_editactusername.do">
			<hk:hide name="actId" value="${actId}"/>
			<hk:hide name="userId" value="${userId}"/>
			<hk:data key="userotherinfo.name"/>:<br/>
			<hk:text name="name" value="${o.name}" maxlength="10"/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/chgcard/act_view.do?actId=${actId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>