<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.user.equipment"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<jsp:include page="../inc/equfeed_inc.jsp"></jsp:include>
	<div class="rcon2">
		<div>
			<div id="do" class="active_tips_tab">对${user.nickName }使用的道具</div>
			<div class="clr"></div>
			<div id="listbox" class="listbox">
				<c:if test="${fn:length(list)==0}">
				<div class="divrow b">你还没有任何可以对${user.nickName }使用的道具</div>
				</c:if>
				<c:if test="${fn:length(list)>0}">
					<c:forEach var="ue" items="${list}" varStatus="idx">
						<div class="block <c:if test="${idx.index%2!=0}">bg1</c:if>">
							<div>
								<div class="f_l" style="text-align: center;width: 80px">${ue.equipment.name }</div>
								<div class="f_l" style="width: 370px">${ue.equipment.intro }</div>
								<div class="f_l" style="width: 100px;text-align: right;">
								<input type="button" class="btn" value="使用" onclick="useequ(${ue.oid})"/>
								</div>
								<div class="clr"></div>
							</div>
						</div>
					</c:forEach>
				</c:if>
				<a class="more2" href="/user/${userId }"><hk:data key="view2.return"/></a>
			</div>
		</div>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function useequ(oid){
	tourl("<%=path %>/h4/op/user_selequ.do?userId=${userId}&ch=1&oid="+oid);
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>