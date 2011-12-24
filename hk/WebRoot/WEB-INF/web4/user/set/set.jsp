<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.user.setting"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px">
<div class="f_l">
	<div id="do" class="active_tips_tab"><a href="#">个人信息</a></div>
	<div id="todo" class="inactive_tips_tab"><a href="#">头像</a></div>
	<div class="clr"></div>
	<div id="listbox" class="listbox">
	</div>
</div>
</div>
<script type="text/javascript">
</script>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>