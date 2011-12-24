<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="<hk:data key="epp.cmpsellnet"/>|${o.name}"/>
<meta name="description" content="<hk:data key="epp.cmpsellnet"/>|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<div class="divrow">
				${cmpSellNet.name } <a href="<%=path %>/epp/web/cmpsellnet.do?companyId=${companyId}&navId=${navId}"><hk:data key="epp.return"/></a><br/>
				<strong><hk:data key="epp.cmpsellnet.tel"/>：</strong>${cmpSellNet.tel }<br/>
				<strong><hk:data key="epp.cmpsellnet.addr"/>：</strong>${cmpSellNet.addr }
			</div>
			<iframe scrolling="no" width="600" height="600" frameborder="0" style="border: none;" src="http://www.huoku.com/cmpsellnetmap.jsp?marker_x=${cmpSellNet.markerX }&marker_y=${cmpSellNet.markerY }&addr=${enc_addr}"></iframe>
			<a class="more2" href="<%=path %>/epp/web/cmpsellnet.do?companyId=${companyId}&navId=${navId}"><hk:data key="epp.return"/></a>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>