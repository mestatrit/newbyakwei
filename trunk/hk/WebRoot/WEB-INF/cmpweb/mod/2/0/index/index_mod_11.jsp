<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==11}">
		<div class="mod">
			<div class="mod_tit">
				${block.name }
			</div>
			<c:forEach var="frlink" items="${cmpfrlinklist}">
			<a target="_blank" title="${frlink.name }" href="http://${frlink.url }" class="split-r">${frlink.name }</a>
			</c:forEach>
		</div>
	</c:if>
</c:forEach>