<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">火酷后台管理</c:set>
<c:set var="html_body_content" scope="request">
<div class="f_l" style="width: 150px;">
<ul class="datalist">
	<li><a href="<%=path %>/h4/admin/authcmp.do">足迹认证审核</a></li>
	<li><a href="<%=path %>/h4/admin/cmp.do">足迹管理</a></li>
	<li><a href="<%=path %>/h4/admin/actor_pinklist.do">推荐美发师管理</a></li>
</ul>
</div>
<div class="f_r" style="width: 690px">${mgr_body_content }
</div>
<div class="clr"></div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>