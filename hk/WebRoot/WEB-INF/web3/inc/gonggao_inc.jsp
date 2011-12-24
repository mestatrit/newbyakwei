<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${fn:length(bizCircleList)>0}">
<div class="mod">
	<div class="mod-4 r_mod3">
		<%=Hkcss2Util.rd_bg %>
		<div class="tit"><hk:data key="view.gonggao"/></div>
		<div class="cont">
			
		</div>
		<%=Hkcss2Util.rd_bg_bottom %>
	</div>
	<div class="clr">
	</div>
</div>
</c:if>