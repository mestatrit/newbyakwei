<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<%long begin=System.currentTimeMillis(); %>
<c:forEach var="i" begin="0" end="1000">
jspjspjspjspjspjspjspjspjspjspjspjsp
</c:forEach>
<%long end=System.currentTimeMillis(); %>
<h1><%=(end-begin) %></h1><br/><br/>
<%begin=System.currentTimeMillis(); %>
<c:forEach var="i" begin="0" end="1000">
<jsp:include page="b_inc.jsp"></jsp:include>
</c:forEach>
<%end=System.currentTimeMillis(); %>
<h1><%=(end-begin) %></h1><br/><br/>