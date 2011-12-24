<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<%CmpUnionSite cmpUnionSite=(CmpUnionSite)request.getAttribute("cmpUnionSite"); %>
<jsp:include page="index_inc.jsp"></jsp:include>
<c:set var="html_main_content" scope="request">
	<div class="row">
		<table>
			<tr>
				<td width="80px">
				<c:if test="${not empty cmpUnion.logo}"><img src="${cmpUnion.logo80Pic }"/></c:if>
				</td>
				<td style="padding-left: 20px;"></td>
			</tr>
		</table>
	</div>
	<div class="row">
		<hk:form action="/union/s.do" method="get">
		<hk:hide name="uid" value="${uid}"/>
		<hk:hide name="s" value="1"/>
		<hk:text name="w"/>
		<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<div class="row">
	<hk:rmBlankLines rm="true">
		<hk:rmstr value="|">
		<c:forEach var="k" items="${kindlist}">
		<hk:a href="/union/kind.do?uid=${uid}&kindId=${k.kindId }">${k.name}</hk:a>|
		</c:forEach>
		</hk:rmstr>
	</hk:rmBlankLines>
	</div>
	<c:forEach var="indexmodvalue" items="${indexmodvaluelist}">
	${indexmodvalue }
	</c:forEach>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>