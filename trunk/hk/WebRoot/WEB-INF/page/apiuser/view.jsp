<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="api - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		userKey:<br/>
		${o.userKey }<br/><br/>
		应用名称:<br/>
		${o.name }<br/><br/>
		url:<br/>
		${o.url }<br/>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>