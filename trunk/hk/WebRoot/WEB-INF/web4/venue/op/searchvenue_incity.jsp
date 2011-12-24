<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.website.title"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px;">
<table class="nt reg" cellpadding="0" cellspacing="0">
<tr>
	<td><h1 style="display:inline"><hk:data key="view2.add_tip"/> / <hk:data key="view2.add_todo"/></h1>  ... <hk:data key="view2.or"/> <a href="/venue/create"><hk:data key="view2.create_new_venue"/></a></td>
</tr>
<tr>
	<td style="font-size: 16px;">
		
	</td>
</tr>
</table>
<br/>
<div>
	<form id="sfrm" method="get" action="/venue/search">
		<hk:hide name="ch" value="1"/>
		<hk:text name="name" clazz="text" maxlength="30" value="${name}"/>
		<hk:submit clazz="btn2" value="view2.search" res="true"/>
	</form>
</div>
<div id="result">
	<div id="res">
		<div><h1>${loginUser.pcity.name }</h1></div>
		<c:if test="${fn:length(list)==0}"><hk:data key="view2.data_not_found"/></c:if>
		<c:if test="${fn:length(list)>0}">
			<c:forEach var="company" items="${list}">
				<div class="row" style="border-bottom: 1px solid #e5e5e5">
					<a class="b" href="/createtip?doneflg=<%=CmpTip.DONEFLG_DONE %>&companyId=${company.companyId }">${company.name }</a><br/>
					<c:if test="${not empty company.addr}">${company.addr}<br/></c:if>
					${company.pcity.name }
				</div>
			</c:forEach>
		</c:if>
		<div style="padding-top: 20px">
			<c:if test="${fn:length(list)>0}">
				<hk:data key="view2.alldata_was_wrong"/>
			</c:if>
			<a href="/venue/create?name=${enc_name }"><hk:data key="view2.create_new_venue"/><span class="b">${name }</span></a>
		</div>
	</div>
</div>
</div>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>