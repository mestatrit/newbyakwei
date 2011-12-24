<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">添加广告</div>
		<div class="mod_content">
			<br/>
			<div class="divrow">
				<a href="<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}">回到广告列表页</a>
			</div>
			<div class="divrow">
				<a href="<%=path %>/epp/web/op/webadmin/cmpad_grouplist.do?companyId=${companyId}&adid=${adid}">为广告选择组</a>
			</div>
		</div>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>