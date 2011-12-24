<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.ZoneService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="com.hk.svr.pub.ZoneUtil"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Country"%><%@page import="com.hk.bean.Province"%><%@page import="com.hk.bean.City"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${fn:length(cmptipvolist)==0}"><hk:data key="view2.no_tips_in_this_place"/></c:if>
<jsp:include page="cmptipvolist_inc.jsp"></jsp:include>
<c:if test="${more_todo}">
<div>
	<a href="/user/${userId }/todo" class="more2"><hk:data key="view2.more"/></a>
</div>
</c:if>
<c:if test="${more_done}">
<div>
	<a href="/user/${userId }/done" class="more2"><hk:data key="view2.more"/></a>
</div>
</c:if>