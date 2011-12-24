<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.optable"/></c:set>
<c:set var="css_value" scope="request"><link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/table.css" /><link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/webst3/js/date/datePicker.css"></c:set>
<c:set var="body_hk_content" scope="request">
<script type="text/javascript" src="<%=path %>/webst3/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/date/jquery.datePicker.min-2.1.2.js"></script>
<jsp:include page="datetable_inc.jsp"></jsp:include>
<div class="mod_primary">
	<div class="datetable">
		<div class="text_14">
			<hk:form method="get" action="/e/op/auth/table_countinfo.do">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="sortId" value="${sortId}"/>
				<hk:hide name="dateflg" value="sel"/>
				查询时间：从<hk:text name="begin_date" clazz="text_short_1 date-pick4 split-r" value="${begin_date}"/> 
				到<hk:text name="end_date" clazz="text_short_1 date-pick5 split-r" value="${end_date}"/>
				<hk:submit value="查询" clazz="btn"/>
			</hk:form>
		</div>
		<div class="date_bar text_14">
			<div class="date_title">${cmpTableSort.name } </div>
			<div class="date_sel">
				<c:if test="${dateflg=='today'}"><c:set var="today_active" >active</c:set></c:if>
				<c:if test="${dateflg=='week'}"><c:set var="week_active" >active</c:set></c:if>
				<c:if test="${dateflg=='days7'}"><c:set var="days7_active" >active</c:set></c:if>
				<c:if test="${dateflg=='month'}"><c:set var="month_active" >active</c:set></c:if>
				<c:set var="baseurl"><%=path %>/e/op/auth/table_countinfo.do?companyId=${companyId}&sortId=${sortId}</c:set>
				<a class="${today_active }" href="${baseurl }&dateflg=today">当天</a>
				<a class="${week_active }" href="${baseurl }&dateflg=week">本周</a>
				<a class="${days7_active }" href="${baseurl }&dateflg=days7">7天内</a>
				<a class="${month_active }" href="${baseurl }&dateflg=month">月</a>
			</div>
			<div class="clr"></div>
		</div>
		<div class="text_16 heavy">
		<c:if test="${rate==0}">暂时没有统计数据</c:if>
		<c:if test="${rate>0}">
		
			${cmpTableSort.name }有${count }张餐桌， 共接待客人${sum }次，翻台率为<fmt:formatNumber value="${rate }" pattern="##.##"></fmt:formatNumber>
		</c:if>
		</div>
	</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
$('.date-pick4').datePicker({clickInput:true,createButton:false,startDate:'2010-01-01'});
$('.date-pick5').datePicker({clickInput:true,createButton:false,startDate:'2010-01-01'});
</script>
</c:set>
<jsp:include page="../../../inc/cmpmgrframe.jsp"></jsp:include>