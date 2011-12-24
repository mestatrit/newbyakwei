<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="css.jsp"></jsp:include>
<% %>
<c:if test="${not empty o.logo48}"><div class="menu menu-top"><hk:a href="/epp/index.do?companyId=${companyId}"><img src="${o.logo48}"/></hk:a></div></c:if>
<%String msg = MessageUtil.getMessage(request);
request.setAttribute("sysmsg",msg); %>
<c:if test="${not empty sysmsg}"><div class="warn"><div>${sysmsg}</div></div></c:if>