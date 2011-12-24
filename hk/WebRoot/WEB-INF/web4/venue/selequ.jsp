<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">报到 - ${company.name }</c:set>
<c:set var="html_body_content" scope="request">
<div>
	<jsp:include page="../inc/equfeed_inc.jsp"></jsp:include>
	<div class="rcon2">
		<div>
			<form id="checkinfrm" method="post" action="<%=path %>/h4/op/user/venue_selequ.do">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="ch" value="1"/>
				<hk:hide name="oid" value=""/>
				<div id="do" class="active_tips_tab">报到 - ${company.name }</div>
				<div class="clr"></div>
				<div id="listbox" class="listbox">
					<br/>
					<div style="padding-left: 5px;">
					<h2 style="display: inline;">顺便说一句</h2> <span>(可以为空)</span>
					<textarea id="status" name="content" class="text_laba"></textarea>
					</div>
					<c:if test="${fn:length(list)==0}">
					<div class="divrow b">你还没有任何可以对${company.name }使用的道具</div>
					</c:if>
					<c:if test="${fn:length(list)>0}">
						<c:forEach var="ue" items="${list}" varStatus="idx">
							<div class="block <c:if test="${idx.index%2!=0}">bg1</c:if>">
								<div>
									<div class="f_l" style="text-align: center;width: 80px">${ue.equipment.name }</div>
									<div class="f_l" style="width: 320px">
									<c:if test="${ue.prePoints>0}">
										可获得<span class="infowarn b">${ue.prePoints }</span>个点数
									</c:if>
									<c:if test="${ue.prePoints==0}">
										${ue.equipment.intro }
									</c:if>
									</div>
									<div class="f_l" style="width: 150px;text-align: right;">
									<input type="submit" class="btn" value="使用并报道" onclick="useequ(${ue.oid})"/>
									</div>
									<div class="clr"></div>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<div class="divrow" style="text-align: center;">
						<c:if test="${onlycheckpoints==0}">
							<input type="submit" class="btn split-r" value="<hk:data key="view2.checkin4"/>"/>
						</c:if>
						<c:if test="${onlycheckpoints>0}">
							<input type="submit" class="btn split-r" value="<hk:data key="view2.checkin3" arg0="${onlycheckpoints}"/>"/>
						</c:if>
						<input type="button" class="btn6" value="<hk:data key="view2.return"/>" onclick="tourl('/venue/${companyId }/')"/>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function useequ(oid){
	getObj('checkinfrm').oid.value=oid;
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>