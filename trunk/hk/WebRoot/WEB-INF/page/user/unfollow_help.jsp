<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="会员搜索 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		${content }
	</div>
	<div class="hang">
		<hk:a href="/home.do?userId=${userId}"><hk:data key="view.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>