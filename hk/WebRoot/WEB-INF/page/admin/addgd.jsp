<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="地皮管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">地皮管理</div>
		<div class="hang ${clazz_var }">
		${userTool.user.nickName} ${userTool.groundCount }块<br/>
	</div>
	<div class="hang">
		<hk:form action="/admin/admin_addgd.do">
			<hk:hide name="userId" value="${userTool.userId}"/>
			请输入增加或减少的地皮<br/>
			<hk:text name="count" maxlength="5"/><br/>
			<hk:submit name="add" value="增加"/> 
			<hk:submit name="lessen" value="减少"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>