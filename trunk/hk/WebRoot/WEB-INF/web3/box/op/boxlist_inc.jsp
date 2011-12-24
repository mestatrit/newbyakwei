<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="iteraotrbox">
	<c:forEach var="b" items="${boxlist}">
		<tr>
			<td width="150px"><a href="<%=path %>/box/op/op_boxweb.do?boxId=${b.boxId }">${b.name }</a></td>
			<td width="150px" align="center">${b.openCount}/${b.totalCount }</td>
			<td width="100px" align="center"><hk:data key="box.boxstatus${b.boxStatus}"/></td>
			<td width="100px" align="center"><hk:data key="box.checkflg${b.checkflg}"/></td>
			<td width="100px" align="center">
			<c:if test="${!b.stop && !b.expired}"><a href="javascript:toedit(${b.boxId })">修改</a></c:if>
			</td>
		</tr>
	</c:forEach>
</c:set>
<table class="infotable" cellpadding="0" cellspacing="0">
<tr>
	<th>名称</th>
	<th>打开数量/总数	</th>
	<th>运行状态</th>
	<th>审核状态</th>
	<th></th>
</tr>
<c:if test="${iteraotrbox!=null}">${iteraotrbox }</c:if>
</table>
<jsp:include page="../../inc/pagesupport_inc2.jsp"></jsp:include>
