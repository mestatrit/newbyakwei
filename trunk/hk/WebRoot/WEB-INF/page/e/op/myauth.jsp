<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="选择你要管理的足迹" rm="false" bodyId="thepage">
<c:set var="addstr" value="cityId=${cityId}&companyId=${companyId }&provinceId=${provinceId }"></c:set>
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">选择你要管理的足迹</div>
	<c:if test="${fn:length(companyvolist)>0}">
		<c:forEach var="vo" items="${companyvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/e/op/op_opauth.do?companyId=${vo.company.companyId}&f=${f}&pcityId=${pcityId }&kind=${kind }&cityId=${cityId }&provinceId=${provinceId }&move_oid=${move_oid }">${vo.company.name}</hk:a> 
			</div>
		</c:forEach>
		<hk:simplepage href="/e/op/op_myauth.do?f=${f}"/>
	</c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>