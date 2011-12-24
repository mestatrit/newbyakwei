<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.OrderForm"%>
<%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.order" /></c:set>
<c:set var="body_hk_content" scope="request">
<style type="text/css">
ul.tablelist li{
font-size:14px;
position:relative;
color:#ffffff;
float: left;
width: 80px;
height: 70px;
border: 3px solid #e5e5e5;
margin: 5px;
text-align: center;
padding: 5px 0px;
overflow: hidden;
}
ul.tablelist li a{
color:#ffffff;
height: 25px;
display: block;
font-weight: bold;
height:70px;
font-size: 16px;
}
ul.tablelist li.free{
background: #006600;
}
</style>
<div class="mod_left">
	<div class="mod-1">
		<%=Hkcss2Util.rd_bg%>
		<div class="tit">座位分类</div>
		<div class="cont">
		<ul class="userset">
			<li><a class="n1" href="<%=path %>/op/orderform_tablelist.do?companyId=${companyId }&oid=${oid}"><hk:data key="view.company.allcmptable"/></a></li>
		</ul>
		<c:forEach var="cmpTableSort" items="${sortlist}">
			<div class="subtit">${cmpTableSort.name }</div>
			<ul class="userset">
				<c:forEach var="cpt" items="${cmpTableSort.cmpPersonTableList}">
					<c:set var="css_class"><c:if test="${sortId==cpt.sortId && cpt.personNum==num}">active</c:if></c:set>
					<li>
						<a id="num_${cpt.sortId }_${cpt.personNum}" class="n1 ${css_class }" href="<%=path %>/op/orderform_tablelist.do?companyId=${companyId }&sortId=${cpt.sortId }&num=${cpt.personNum}&oid=${oid}"><hk:data key="view.company.cmptable.personnum" arg0="${cpt.personNum}"/> <span>(${cpt.freeCount }/${cpt.totalCount })</span></a>
					</li>
					<c:set var="css_class"></c:set>
				</c:forEach>
			</ul>
		</c:forEach>
		</div>
		<%=Hkcss2Util.rd_bg_bottom%>
	</div>
</div>
<div class="mod_primary">
	<h3 class="line">${company.name }</h3>
	<div>
		<ul class="tablelist">
			<c:forEach var="t" items="${list}">
			<li class="free">
				<a href="<%=path %>/op/orderform_table.do?oid=${oid}&tableId=${t.tableId}&companyId=${companyId}&return_url=${return_url}">${t.tableNum }</a>
			</li>
			</c:forEach>
		</ul>
		<div class="clr"></div>
	</div>
</div>
<div class="clr"></div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>