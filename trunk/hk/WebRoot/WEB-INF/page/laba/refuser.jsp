<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="转载喇叭的火友 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">转载喇叭的火友</div>
	<c:if test="${fn:length(reflabalist)>0}">
		<c:forEach var="reflaba" items="${reflabalist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }"><hk:a href="/home.do?userId=${reflaba.refUserId}">${reflaba.refUser.nickName}</hk:a> <fmt:formatDate value="${reflaba.createTime}" pattern="yyyy-MM-dd HH:mm"/>进行了转发  
		<c:if test="${loginUser.userId==reflaba.refUserId}"><hk:a href="/laba/op/op_delref.do?labaId=${labaId}">删除转发</hk:a></c:if>
		</div>
		</c:forEach>
		<hk:simplepage href="/laba/laba_refuser.do?labaId=${labaId}"/>
	</c:if>
	<c:if test="${fn:length(reflabalist)==0}">没有数据显示</c:if>
	<div class="hang"><hk:a href="/laba/laba.do?labaId=${labaId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>