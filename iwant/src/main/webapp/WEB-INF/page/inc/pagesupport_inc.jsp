<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.dev3g.cactus.web.util.SimplePage"%>
<%@page import="com.dev3g.cactus.util.HkUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
SimplePage simplePage=(SimplePage)request.getAttribute(HkUtil.SIMPLEPAGE_ATTRIBUTE);
request.setAttribute("simplePage",simplePage);%>
<c:if test="${simplePage!=null}">
<div class="page3">
	<c:if test="${url_rewrite}">
		<c:if test="${simplePage.page>1}">
			<a class="p_l" href="${page_url }/${simplePage.page-1 }">上一页</a>
		</c:if>
		<c:if test="${simplePage.hasNext}">
			<a class="p_r" href="${page_url }/${simplePage.page+1 }">下一页</a>
		</c:if>
	</c:if>
	<c:if test="${!url_rewrite}">
		<c:if test="${simplePage.page>1}">
			<a class="p_l" href="${page_url }&amp;page=${simplePage.page-1 }">上一页</a>
		</c:if>
		<c:if test="${simplePage.hasNext}">
			<a class="p_r" href="${page_url }&amp;page=${simplePage.page+1 }">下一页</a>
		</c:if>
	</c:if>
	<div class="clr"></div>
</div>
</c:if>