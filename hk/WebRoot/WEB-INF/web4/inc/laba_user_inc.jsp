<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.ZoneService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="com.hk.svr.pub.ZoneUtil"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Country"%><%@page import="com.hk.bean.Province"%><%@page import="com.hk.bean.City"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${fn:length(labavolist)==0}"><hk:data key="view2.user_nolaba"/></c:if>
<c:if test="${fn:length(labavolist)>0}">
	<c:forEach var="vo" items="${labavolist}" varStatus="idx">
		<div class="block <c:if test="${idx.index%2!=0}">bg1</c:if>">
			<div class="content">
				${vo.content }
				 <span class="s">(<hk:time value="${vo.laba.createTime}"/>)</span>
			</div>
			<div class="clr"></div>
		</div>
	</c:forEach>
</c:if>
<c:if test="${more_laba}">
<div>
	<a href="/user/${userId }/laba/2" class="more2"><hk:data key="view2.more"/></a>
</div>
</c:if>