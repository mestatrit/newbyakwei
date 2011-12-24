<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<div class="top">
<span class="split-r"><a clazz="noline" href="/m">${o.name }</a></span>
<c:if test="${loginUser!=null}">你好，${loginUser.nickName }</c:if></div>
<%String msg = MessageUtil.getMessage(request);%>
<%if(msg!=null){%>
<div class="warn">
<%=msg %>
</div>
<%}%>