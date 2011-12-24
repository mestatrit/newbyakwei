<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.user.boxprize"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width:700px;">
	<div class="f_l">
		<div class="active_tips_tab"><a href="<%=path %>/h4/op/user_prize.do"><hk:data key="view2.user.boxprize"/></a></div>
		<div class="inactive_tips_tab"><a href="<%=path %>/h4/op/user_equ.do"><hk:data key="view2.user.equipment"/></a></div>
		<div class="inactive_tips_tab"><a href="<%=path %>/h4/op/user_equused.do"><hk:data key="view2.user.equipment.used"/></a></div>
		<div class="clr"></div>
		<div id="listbox" class="listbox" style="width: 680px">
			<c:if test="${fn:length(list)==0}">
				<div class="nodata"><hk:data key="view2.noprize.display"/></div>
			</c:if>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="up" items="${list}" varStatus="idx">
				<c:set var="onmouseover"><c:if test="${idx.index%2!=0}">this.className='block bg1 bg2'</c:if><c:if test="${idx.index%2==0}">this.className='block bg2'</c:if></c:set>
				<c:set var="onmouseout"><c:if test="${idx.index%2!=0}">this.className='block bg1'</c:if><c:if test="${idx.index%2==0}">this.className='block'</c:if></c:set>
					<div class="block <c:if test="${idx.index%2!=0}">bg1</c:if>" onmouseover="${onmouseover}" onmouseout="${onmouseout}">
						<div class="f_l" style="width: 60px">
							<c:if test="${not empty up.boxPrize.path}">
								<img alt="${up.boxPrize.name }" title="${up.boxPrize.name }" src="${up.boxPrize.h_0Pic }">
							</c:if>
						</div>
						<div class="f_r" style="width: 90px;height: 100%;text-align: center;">
							<c:if test="${up.drawed }">已兑换</c:if>
						</div>
						<div style="margin-left: 70px;margin-right:90px;">
						<span class="b split-r">${up.boxPrize.name }</span>
						<c:if test="${up.boxPrize.useSignal}">
							<span class="split-r">序列号：${up.prizeNum }</span>
							<span class="split-r">暗号：${up.prizePwd }</span>
						</c:if>
						<br/>
						${up.boxPrize.tip }</div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</c:if>
			<c:set var="page_url" scope="request"><%=path %>/h4/op/user_prize.do?v=1</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
	<div class="clr"></div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>