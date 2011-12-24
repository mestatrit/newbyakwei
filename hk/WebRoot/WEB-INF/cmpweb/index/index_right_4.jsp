<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
EppViewUtil.loadBoxList(request);%>
<c:if test="${fn:length(boxlist)>0}">
	<!-- 宝箱 -->
	<div class="innermod">
		<h3 class="home_mod_title">${right_nav.name }</h3>
		<ul class="article">
			<c:forEach var="box" items="${boxlist}">
				<li><a href="<%=path %>/epp/web/box_view.do?companyId=${companyId }&navId=${right_nav.oid}&boxId=${box.boxId}">${box.name }</a></li>
			</c:forEach>
		</ul>
	</div>
</c:if>