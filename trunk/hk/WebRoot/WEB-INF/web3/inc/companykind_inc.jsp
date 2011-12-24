<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:if test="${fn:length(companyKindList)>0}">
<div class="mod">
	<div class="mod-5 simple_nav">
		<%=Hkcss2Util.rd_bg %>
		<div class="cont">
			<div class="pad">
				<ul class="userset">
					<c:forEach var="k" items="${companyKindList}">
					<li>
						<a class="n1" href="<%=path %>/cmp_klist.do?kindId=${k.kindId }&${url_add }">${k.name }<span>(${k.cmpCount })</span> </a>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<%=Hkcss2Util.rd_bg_bottom %>
	</div>
	<div class="clr"></div>
</div>
</c:if>