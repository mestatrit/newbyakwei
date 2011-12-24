<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:if test="${fn:length(cmplist)>0}">
	<div id="usercmplist" class="cmplist usercmplist">
		<br class="linefix" /><a name="list_top"></a>
		<c:forEach var="cmp" items="${cmplist}">
		<c:set var="cmp_url"><%=path %>/cmp.do?companyId=${cmp.companyId }</c:set>
			<div class="cmp"  onmouseout="this.className='cmp';" onmouseover="this.className='cmp bg3';">
				<div class="image">
					<a href="${cmp_url }"><img src="${cmp.head240 }" /></a>
				</div>
				<div class="content">
					<a class="text_16" href="${cmp_url }">${cmp.name }</a>
					<br />
					<c:if test="${cmp.starsLevel>0}"><img class="imgd" src="<%=path %>/webst3/img/stars/star${cmp.starsLevel }.gif" /></c:if>
					<c:if test="${cmp.reviewCount>0}"><span class="small"><hk:data key="view.company.reviewcount" arg0="${cmp.reviewCount}"/></span></c:if>
					<br />
					${cmp.intro }
				</div>
				<div class="clr"></div>
			</div>
		</c:forEach>
	</div>
	<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
</c:if>