<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.optable"/></c:set>
<c:set var="mgr_content" scope="request">
<style type="text/css">
ul.tablelist li{
font-size:14px;
position:relative;
color:#ffffff;
float: left;
width: 100px;
height: 90px;
border: 3px solid #e5e5e5;
margin: 7px;
text-align: center;
padding: 5px 0px;
overflow: hidden;
}
ul.tablelist li a{
color:#ffffff;
height: 25px;
display: block;
font-weight: bold;
text-decoration: underline;
}
ul.tablelist li a:hover{
text-decoration: none;
}
ul.tablelist li.free{
background: #006600;
}
ul.tablelist li.inuse{
background: #CC0000;
}
ul.tablelist li.booked{
background: #FFCC00;
}
ul li div.num{
font-size: 16px;
font-weight: bold;
}
</style>
<c:if test="${booked_not_meal_count>0}">
<div class="warn2">
<a href="<%=path %>/e/op/auth/table_bookednotmeal.do?companyId=${companyId}"><hk:data key="view.company.cmpordertable.bookednotmealcount" arg0="${booked_not_meal_count}"/></a>
</div>
</c:if>
<div>
<ul class="tablelist">
	<c:forEach var="t" items="${list}">
	<c:set var="clazz">
		<c:if test="${t.booked}">booked</c:if>
		<c:if test="${!t.booked}">
			<c:if test="${t.free}">free</c:if>
			<c:if test="${!t.free}">inuse</c:if>
		</c:if>
	</c:set>
	<li class="${clazz }">
		<div class="num">${t.tableNum }</div>
		<a href="javascript:setbook(${t.tableId })">预约</a>
		<c:if test="${t.free}">
			<a href="javascript:setinuse(${t.tableId })">安排客人</a>
		</c:if>
		<c:if test="${!t.free}">
			<a href="javascript:setfree(${t.tableId })">送客</a>
		</c:if>
	</li>
	</c:forEach>
</ul>
<div class="clr"></div>
</div>
<script type="text/javascript">
function setfree(id){
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/table_setfree.do?companyId=${companyId}&tableId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setinuse(id){
	tourl('<%=path %>/e/op/auth/table_datetable.do?companyId=${companyId}&tableId='+id+"&dateflg=today");
}
function setbook(id){
	tourl('<%=path %>/e/op/auth/table_datetable.do?companyId=${companyId}&tableId='+id+"&dateflg=today");
}
</script>
</c:set>
<c:set var="cmptable_show" value="true" scope="request"/>
<c:set var="mgr_bar_not_show" value="true" scope="request"/>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>