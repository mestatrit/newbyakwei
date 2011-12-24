<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">搜索产品</div>
<div class="row">
<hk:form action="/union/s.do" method="get">
<hk:hide name="uid" value="${uid}"/>
<hk:hide name="s" value="1"/>
<hk:text name="w" value="${denc_w}"/>
<hk:submit value="搜索"/>
</hk:form>
</div>
<c:if test="${fn:length(list)==0 && s==1}"><hk:data key="nodataview"/></c:if>
<c:if test="${fn:length(list)>0}">
	<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="row ${clazz_var }"><hk:a href="/union/product.do?uid=${uid }&pid=${p.productId}">${p.name}</hk:a></div>
	</c:forEach>
	<div class="row"><hk:simplepage2 href="/union/s.do?uid=${uid}&s=1&w=${enc_w }"/></div>
</c:if>
<div class="row">
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>