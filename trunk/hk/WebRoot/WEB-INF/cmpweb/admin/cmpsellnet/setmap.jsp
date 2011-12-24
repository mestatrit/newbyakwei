<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.hk.frame.util.DesUtil"%>
<%@page import="com.hk.bean.User"%>
<%@page import="com.hk.frame.util.DataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">修改地图</div>
		<div class="mod_content">
			<div class="divrow">
				<span class="split-r">${cmpSellNet.name }</span>
				<a href="<%=path %>/epp/web/op/webadmin/cmpsellnet.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</div>
			<div>
			<%User loginUser=(User)request.getAttribute("loginUser"); %>
				<fmt:formatDate var="currentdate" scope="request" pattern="yyyy-MM-dd HH" value="<%=new Date() %>"/>
				<iframe frameborder="0" name="mapframe" src="http://www.huoku.com/cmpsellnetmapfunc.jsp?companyId=${companyId }&oid=${oid }&addr=${enc_addr }&marker_x=${cmpSellNet.markerX }&marker_y=${cmpSellNet.markerY }&sckey=<%=DesUtil.encode("websitegooglemaphkkey",(String)request.getAttribute("currentdate")) %>&op=<%=DesUtil.encode("opuserid",String.valueOf(loginUser.getUserId())) %>" style="border: none;" width="610" height="600">
				</iframe>
			</div>
		</div>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>