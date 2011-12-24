<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="宝库 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
	<hk:a clazz="nn" href="/op/award_prize.do">开箱物品</hk:a> |
	<hk:a href="/op/award_equ.do">道具</hk:a> |
	<hk:a href="/op/award_equused.do">已使用的道具</hk:a>
	</div>
	<c:forEach var="up" items="${list}" varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if>
		<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			${up.boxPrize.name }<br/>
			<span class="ruo s split-r"><fmt:formatDate value="${up.createTime}" pattern="yy-MM-dd"/>获得</span>
			<c:if test="${up.boxPrize.useSignal && up.drawed}">
				<span class="ruo s">序列号：${up.prizeNum }  暗号：${up.prizePwd }</span>
				<span class="ruo s"><fmt:formatDate value="${up.drawTime}" pattern="yy-MM-dd"/>兑换</span>
			</c:if>
		</div>
	</c:forEach>
	<div class="hang">
	<hk:simplepage2 href="/op/award_prize.do?v=1"/>
	</div>
	<div class="hang">
	<hk:a href="/home.do?userId=${userId}"><hk:data key="view2.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>