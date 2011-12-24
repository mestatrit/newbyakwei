<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1"><hk:data key="epp.box.boxinfo" arg0="${box.name }"/></h1>
		<div class="mod_content">
			<c:if test="${prize!=null}">
				<div class="divrow">
					<h1>${prize.name }</h1>
					<c:if test="${not empty prize.path}">
						<img src="${prize.h_2Pic }"/><br/>
					</c:if>
					${prize.tip }
				</div>
				<c:if test="${userBoxPrize!=null && prize.useSignal}">
					<div class="divrow">
						<hk:data key="epp.box.boxprize.duihuan.serialnumber"/>：<span class="b">${userBoxPrize.prizeNum }</span><br/>
						<hk:data key="epp.box.boxprize.duihuan.cipher"/>：<span class="b">${userBoxPrize.prizePwd }</span><br/>
					</div>
				</c:if>
			</c:if>
			<a href="<%=path %>/epp/web/box_view.do?companyId=${companyId}&boxId=${boxId}&navId=${navId}" class="more2"><hk:data key="epp.return"/></a>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>