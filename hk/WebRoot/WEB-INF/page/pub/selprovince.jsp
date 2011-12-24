<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="选择省/直辖市 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">选择省/直辖市</div>
	<div class="hang odd">
		<c:forEach var="p" items="${list}">
			<hk:a clazz="split-r" href="/index_selcityfromprovince.do?provinceId=${p.provinceId}&forsel=${forsel }" needreturnurl="true">${p.province}</hk:a>
		</c:forEach>
	</div>
	<div class="hang"><hk:a href="/index_changecity2.do?forsel=${forsel }" needreturnurl="true">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>