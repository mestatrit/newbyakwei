<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${company.name} - <hk:data key="view2.venue.editpic"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px;">
<h1><hk:data key="view2.venue.editpic"/></h1>
<br/>
<form method="post" action="<%=path %>/h4/op/user/venue_editpic.do">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<c:forEach var="p" items="${list}">
		<div class="row bdtm" style="padding-bottom: 10px;">
			<hk:hide name="photoId" value="${p.photoId}"/>
			名称：<hk:text name="name${p.photoId}" clazz="text" maxlength="20"/><br/>
			<img src="${p.pic320 }"/>
		</div>
	</c:forEach>
	<div style="padding-left: 150px;">
	<hk:submit value="view2.submit" res="true" clazz="btn split-r"/> 
	<a href="/venue/${companyId }/"><hk:data key="view2.return"/></a>
	</div>
</form>
</div>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>