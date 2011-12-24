<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="确认推荐这个喇叭？ - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">确认推荐这个喇叭？</div>
	<div class="hang">
		<hk:form action="/laba/op/op_confirmpinklaba.do?${queryString}">
			<hk:submit name="ok" value="确定" clazz="sub"/>
			<hk:submit name="cancel" value="取消"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>