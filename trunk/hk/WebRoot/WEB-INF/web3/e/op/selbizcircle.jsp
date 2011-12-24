<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">商圈管理</c:set>
<c:set var="mgr_content" scope="request">
<div>
	<c:if test="${fn:length(bclist)>0}">
		<h3>可以添加的商圈</h3>
		<div class="bdbtm"></div>
		<table class="infotable" cellpadding="0" cellspacing="0">
			<c:forEach var="bc" items="${bclist}">
				<tr>
					<td width="100px">${bc.name }</td>
					<td><a href="<%=path %>/e/op/op_selbizcircleweb.do?companyId=${companyId }&circleId=${bc.circleId }">添加</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${fn:length(bclist)==0}"><strong class="text_14">没有可以添加的商圈</strong></c:if>
</div>
<div>
	<c:if test="${fn:length(bclist2)>0}">
		<h3>已添加的商圈</h3>
		<div class="bdbtm"></div>
		<table class="infotable" cellpadding="0" cellspacing="0">
			<c:forEach var="bc" items="${bclist2}">
				<tr>
					<td width="100px">${bc.name }</td>
					<td><a href="<%=path %>/e/op/op_delbizcircleweb.do?companyId=${companyId }&circleId=${bc.circleId }">删除</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${fn:length(bclist2)==0}"><strong class="text_14">还没有添加任何商圈</strong></c:if>
</div>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>