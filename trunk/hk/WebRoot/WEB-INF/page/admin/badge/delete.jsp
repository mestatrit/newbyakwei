<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="确认删除徽章 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">确认删除徽章</div>
	<div class="hang even">
		<hk:form action="/admin/badge_delete.do">
			<hk:hide name="badgeId" value="${badgeId}"/>
			<hk:hide name="cfm" value="1"/>
			<hk:submit name="ok" value="确定"/>
			<hk:submit name="cancel" value="取消"/>
		</hk:form>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>