<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.nearby.companyfeedinfo"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:data key="view.nearby.companyfeedinfo"/></div>
	<c:if test="${fn:length(companyfeedvolist)>0}">
		<c:forEach var="f" items="${companyfeedvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
			${f.content } 
			<fmt:formatDate value="${f.companyFeed.createTime}" pattern="yy-MM-dd HH:mm"/>
			</div>
		</c:forEach>
		<hk:simplepage href="/e/cmp_feedlist.do"/>
	</c:if>
	<c:if test="${fn:length(companyfeedvolist)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>