<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">所有分类</div>
<c:if test="${fn:length(cmdkindlist)==0}"><hk:data key="nodataview"/></c:if>
<c:if test="${fn:length(cmdkindlist)>0}">
	<c:forEach var="k" items="${cmdkindlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="row ${clazz_var }"><hk:a href="/union/kind.do?uid=${uid }&kindId=${k.kindId}">${k.name}</hk:a></div>
	</c:forEach>
	<div class="row"><hk:simplepage2 href="/union/kind_cmdlist.do?uid=${uid}"/></div>
</c:if>
<div class="row">
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>