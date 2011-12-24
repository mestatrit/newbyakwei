<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">设置</c:set>
<c:set var="html_main_content" scope="request">
<div class="nav2">设置</div>
<div class="row">
<hk:a href="/union/op/userset_toupdatenickname.do?uid=${uid}">修改昵称</hk:a>
</div>
<div class="row">
<hk:a href="/union/op/userset_toupdatepwd.do?uid=${uid}">修改密码</hk:a>
</div>
<div class="row">
<hk:a href="/union/home.do?uid=${uid}&userId=${cmpUnion_loginUser.userId }">返回</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>