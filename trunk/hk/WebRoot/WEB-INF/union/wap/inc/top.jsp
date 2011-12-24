<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.frame.util.MessageUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<div class="top">
<span class="split-r"><hk:a clazz="noline" href="/union/union.do?uid=${uid}">${cmpUnion.name }</hk:a> </span>
<c:if test="${cmpUnion_loginUser!=null}">
你好，<hk:a href="/union/home.do?uid=${uid }&userId=${cmpUnion_loginUser.userId}">${cmpUnion_loginUser.nickName }</hk:a>|<hk:a href="/union/logout.do?uid=${uid}">退出</hk:a>
</c:if>
<c:if test="${cmpUnion_loginUser==null}">
<hk:a href="/union/login_tologin.do?uid=${uid}">登录</hk:a>|<hk:a href="/union/reg_toreg.do?uid=${uid}">注册</hk:a>
</c:if>
</div>
<%String msg = MessageUtil.getMessage(request);%>
<%if(msg!=null){%>
<div class="warn">
<%=msg %>
</div>
<%}%>