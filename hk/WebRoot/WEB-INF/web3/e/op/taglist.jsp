<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">标签管理</c:set>
<c:set var="mgr_content" scope="request">
<c:if test="${fn:length(alllist)==0 && fn:length(list)==0}">
<strong class="text_14">目前没有标签可以添加</strong>
</c:if>
<c:if test="${fn:length(alllist)>0 || fn:length(list)>0}">
<div>
	<c:if test="${fn:length(alllist)>0}">
		<h3>可以添加的标签</h3>
		<div class="bdbtm"></div>
		<table class="infotable" cellpadding="0" cellspacing="0">
			<c:forEach var="t" items="${alllist}">
				<tr>
					<td width="100px">${t.name }</td>
					<td><a href="<%=path %>/e/op/op_addtag.do?companyId=${companyId }&tagId=${t.tagId }">添加</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${fn:length(alllist)==0}"><strong class="text_14">没有可以添加的标签</strong></c:if>
</div>
<div>
	<c:if test="${fn:length(list)>0}">
		<h3>已添加的标签</h3>
		<div class="bdbtm"></div>
		<table class="infotable" cellpadding="0" cellspacing="0">
			<c:forEach var="t" items="${list}">
				<tr>
					<td width="100px">${t.name }</td>
					<td><a href="<%=path %>/e/op/op_deltag.do?companyId=${companyId }&tagId=${t.tagId }">删除</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${fn:length(list)==0}"><strong class="text_14">还没有添加任何标签</strong></c:if>
</div>
</c:if>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>