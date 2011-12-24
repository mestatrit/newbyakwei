<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${fn:length(list)==0}"><div class="b">暂时没有数据</div></c:if>
<c:if test="${fn:length(list)>0}">
	<ul class="itemimglist">
	<c:forEach var="ref" items="${list}">
		<li><a href="${ctx_path }/tb/item?itemid=${ref.itemid}"><img src="${ref.tbItem.pic_url }_sum.jpg" title="${ref.tbItem.title }"/></a></li>
	</c:forEach>
	</ul>
	<div class="clr"></div>
	<c:if test="${more_wantitem}">
	<a class="more2" href="${ctx }/tb/user_wantitem?userid=${userid}">更多</a>
	</c:if>
</c:if>