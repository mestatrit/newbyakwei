<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.selzone"/></c:set>
<c:set var="html_body_content" scope="request">
<style type="text/css">
a.city{
float: left;
display: block;
width: 120px;
height: 35px;
text-align: center;
border: 1px solid #e5e5e5;
font-size: 16px;
font-weight: bold;
line-height: 35px;
overflow: hidden;
}
a.city:hover{
background-color: #ccc;
}
</style>
<div>
<h1><hk:data key="view2.selzone"/></h1>
<br/>
<h3 class="bdtm">${province.province }</h3><br/>
<c:forEach var="city" items="${citylist}">
<a href="<%=path %>/index_selcity.do?cityId=${city.cityId}&return_url=${return_url}" class="city">${city.city }</a>
</c:forEach>
</div>
<script type="text/javascript">
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>