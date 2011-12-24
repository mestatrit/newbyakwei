<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.selbizcircle"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
<c:set var="addstr" value="cityId=${cityId}&companyId=${companyId }&provinceId=${provinceId }"></c:set>
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">${view_title}</div>
	<c:if test="${fn:length(list)>0}">
		<div class="hang odd">
				<c:forEach var="c" items="${list}" varStatus="idx">
					<hk:a href="/e/op/op_selbizcircle.do?circleId=${c.circleId}&${addstr}">${c.name}</hk:a> 
				</c:forEach>
		</div>
		<hk:simplepage href="/e/op/op_bizbizclelist.do?${addstr}"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/e/cmp.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>