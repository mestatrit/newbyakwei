<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="iteratorbox">
<c:forEach var="box" items="${boxlist}">
	<c:set var="box_url"><%=path %>/box.do?boxId=${box.boxId }</c:set>
	<li>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="head"><a href="${box_url }"><img src="<%=path %>/webst3/img/bx60.gif"/></a></td>
				<td class="box-body">
					<div class="boxname"><a href="${box_url }">${box.name }</a></div>
					<div class="intro">${box.intro }</div>
				</td>
			</tr>
		</table>
	</li>
</c:forEach>
</c:set>
<ul class="boxlist" id="boxlist"><c:if test="${boxlist!=null}">${iteratorbox}</c:if></ul>
<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
