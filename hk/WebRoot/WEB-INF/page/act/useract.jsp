<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.myjoinact"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">${view_title}</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="a" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/act/act.do?actId=${a.actId}">${a.name}</hk:a>
			</div>
		</c:forEach>
		<hk:simplepage href="/act/act_useract.do"/>
	</c:if>
	<div class="hang even"><c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>