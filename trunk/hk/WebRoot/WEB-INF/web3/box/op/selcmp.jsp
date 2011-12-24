<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.box.create" /></c:set>
<c:set var="mgr_content" scope="request">
	<h2 class="line">选择发布方式</h2>
	<div class="text_16">
		<div><a href="<%=path %>/box/op/op_toaddweb.do">个人发布</a></div>
		<c:forEach var="c" items="${companylist}">
			<div><hk:a href="/box/op/op_toaddweb.do?companyId=${c.companyId}">${c.name}</hk:a></div>
		</c:forEach>
	</div>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>