<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib
	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%
	String path = request.getContextPath();
%>
<!-- 地图 -->
<div class="innermod">
	<iframe scrolling="no" width="210" height="210" style="border: none;border: 0;border-bottom-style: none;padding: 0;margin: 0;" frameborder="0" src="http://www.huoku.com/smallmap.jsp?companyId=${companyId }">
		
	</iframe>
</div>