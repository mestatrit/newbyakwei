<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="相关足迹[${name }]- 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">相关足迹[${name }]</div>
	<c:forEach var="c" items="${list}" varStatus="idx">
	<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/e/cmp.do?companyId=${c.companyId }">${c.name}</hk:a> 
		</div>
	</c:forEach>
	<div class="hang"><hk:simplepage2 href="/e/cmp_s?name=${enc_name }&forpage=1"/></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>