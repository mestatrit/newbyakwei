<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.user.equipment"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width:700px;">
	<div class="f_l">
		<div class="inactive_tips_tab"><a href="<%=path %>/h4/op/user_prize.do"><hk:data key="view2.user.boxprize"/></a></div>
		<div class="active_tips_tab"><a href="<%=path %>/h4/op/user_equ.do"><hk:data key="view2.user.equipment"/></a></div>
		<div class="inactive_tips_tab"><a href="<%=path %>/h4/op/user_equused.do"><hk:data key="view2.user.equipment.used"/></a></div>
		<div class="clr"></div>
		<div id="listbox" class="listbox" style="width: 680px">
			<c:if test="${fn:length(list)==0}">
				<div class="nodata"><hk:data key="view2.noequ.display"/></div>
			</c:if>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="ue" items="${list}" varStatus="idx">
					<c:set var="onmouseover"><c:if test="${idx.index%2!=0}">this.className='block bg1 bg2'</c:if><c:if test="${idx.index%2==0}">this.className='block bg2'</c:if></c:set>
					<c:set var="onmouseout"><c:if test="${idx.index%2!=0}">this.className='block bg1'</c:if><c:if test="${idx.index%2==0}">this.className='block'</c:if></c:set>
					<div class="block <c:if test="${idx.index%2!=0}">bg1</c:if>" onmouseover="${onmouseover}" onmouseout="${onmouseout}">
						<div class="f_l" style="text-align: center;width: 80px;height: 100%;">${ue.equipment.name }</div>
						<div style="margin-left: 80px;height: 100%;">${ue.equipment.intro }</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<div class="clr"></div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>