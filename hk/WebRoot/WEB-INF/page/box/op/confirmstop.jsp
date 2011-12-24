<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="你确实要作废${box.name}宝箱吗？ - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">你确实要作废${box.name}宝箱吗？</div>
	<div class="hang">
		<hk:form action="/box/op/op_confirmstop.do">
			<hk:hide name="fromadmin" value="${fromadmin}"/>
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="t" value="${t}"/>
			<hk:hide name="repage" value="${repage}"/>
			<div class="hang">
			<hk:submit name="ok" value="确定" clazz="sub"/>
			<hk:submit name="cancel" value="取消"/>
			</div>
		</hk:form>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>