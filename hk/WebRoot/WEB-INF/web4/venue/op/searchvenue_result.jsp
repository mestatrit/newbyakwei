<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<html>
<body>
<div id="res">
	<div>
	<strong>${loginUser.pcity.name }</strong>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="company" items="${list}">
			<div class="row" style="border-bottom: 1px solid #e5e5e5">
				<a class="b" href="/createtip?doneflg=<%=CmpTip.DONEFLG_DONE %>&companyId=${company.companyId }">${company.name }</a><br/>
				<c:if test="${not empty company.addr}">${company.addr}<br/></c:if>
				${company.pcity.name }
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(list)==0}">
		<hk:data key="view2.company_not_found"/> 
		<hk:data key="view2.you_like_add_it"/>
		<a class="b" href="/venue/create?name=${enc_name }"><hk:data key="view2.click_here"/></a>
	</c:if>
	<div>
	<a href="/venue/search?t=all"><hk:data key="view2.searchvenue_on_other_city"/></a>
	</div>
</div>
<script type="text/javascript">
	var html=document.getElementById("res").innerHTML;
	parent.showResult(html);
</script>
</body>
</html>