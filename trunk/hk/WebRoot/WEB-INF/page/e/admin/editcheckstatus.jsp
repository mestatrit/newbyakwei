<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		${user.nickName } (${company.name })<br/>
		<hk:form action="/e/admin/admin_editcheckstatus.do">
			<hk:hide name="sysId" value="${sysId}"/>
			<hk:hide name="checkStatus" value="${checkStatus}"/>
			<br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/admin/admin_authcompany.do?sysId=${sysId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>