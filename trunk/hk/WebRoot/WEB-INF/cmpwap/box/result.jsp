<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
</div>
<div class="row"><hk:data key="epp.box.boxinfo" arg0="${box.name }"/></div>
<div class="row">${prize.name}</div>
<div class="row odd">
	${prize.tip}<br/>
	<c:if test="${prize.useSignal}">
		<hk:data key="epp.box.boxprize.duihuanmethod"/>：<br/>
		<hk:data key="epp.box.boxprize.duihuan.serialnumber"/>：<span class="b">${userBoxPrize.prizeNum }</span><br/>
		<hk:data key="epp.box.boxprize.duihuan.cipher"/>：<span class="b">${userBoxPrize.prizePwd }</span><br/>
	</c:if>
</div>
<div class="hang">
	<hk:form action="/epp/web/box_wapview.do?companyId=${companyId }&boxId=${box.boxId}&navId=${navId }">
		<hk:submit value="epp.box.continue" res="true"/>
	</hk:form>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>