<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.op.employee.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:a href="/e/op/op_toaddemployee.do?cid=${cid}"><hk:data key="e.op.employee.addemployee"/></hk:a></div>
	<c:if test="${fn:length(evolist)>0}">
		<c:set var="addstr" value="cid=${cid}"></c:set>
		<c:forEach var="vo" items="${evolist}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${vo.employee.userId}">${vo.employee.user.nickName }</hk:a> 
				${vo.levelData }
				<c:if test="${loginUser.userId!=vo.employee.userId}">
					<hk:a href="/e/op/op_delemployee.do?userId=${vo.employee.userId}&${addstr }"><hk:data key="view.delete"/></hk:a> 
				</c:if>
			</div>
		</c:forEach>
		<hk:simplepage href="/e/op/op_empoloyee.do?${addstr}"/>
	</c:if>
	<c:if test="${fn:length(evolist)==0}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/e/op/op_view.do?${addstr}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>