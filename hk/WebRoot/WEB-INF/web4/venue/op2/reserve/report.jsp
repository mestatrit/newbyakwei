<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.HkWebUtil"%>
<%@page import="com.hk.bean.CmpReserve"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCompany(request);
%>
<c:set var="js_value" scope="request">
<link type="text/css" href="<%=path%>/webst4/css/smoothness/jquery-ui-1.7.custom.css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/webst4/js/jquery-ui-1.7.custom.min.js"></script>
<script type="text/javascript" src="<%=path%>/webst4/js/ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(function(){
		$('.datepicker').datepicker({
			numberOfMonths: 1,
			showButtonPanel: false,
			dateFormat: 'yy-mm-dd'
		});
	});
});
</script>
</c:set>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
<div class="mod_title">工作统计</div>
<div class="mod_content">
	<div class="divrow">
	<form method="get" action="<%=path %>/h4/op/venue/reserve_report.do">
		<hk:hide name="companyId" value="${companyId}"/>
		开始日期：<hk:text name="begin" value="${begin}" clazz="text_yzm datepicker"/>
		结束日期：<hk:text name="end" value="${end}" clazz="text_yzm datepicker"/>
		<hk:submit value="统计" clazz="btn"/>
	</form>
	</div>
		<c:if test="${fn:length(list)>0}">
			<ul class="rowlist">
				<li>
					<div class="f_l" style="width: 150px">姓名</div>
					<div class="f_l" style="width: 100px">预约完成量</div>
					<div class="clr"></div>
				</li>
				<c:forEach var="report" items="${list}">
					<li>
						<div class="f_l" style="width: 150px">${report.cmpActor.name }</div>
						<div class="f_l" style="width: 100px">${report.workCount }</div>
						<div class="clr"></div>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	<c:if test="${fn:length(list)==0}">
		<div class="nodata">暂时没有统计数据</div>
	</c:if>
</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>