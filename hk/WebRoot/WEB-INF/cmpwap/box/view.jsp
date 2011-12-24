<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
</div>
<div class="row">${box.name}</div>
<div class="row">
<hk:data key="epp.box.remain" arg0="${box.totalCount-box.openCount}"/>
</div>
<div class="row">
	<div>${box.intro}</div>
	<div><hk:data key="epp.box.boxprize"/>:</div>
	<c:forEach var="p" items="${list}" varStatus="idx">
		<div class="${clazz_var}">${p.name} ${p.pcount}个</div>
	</c:forEach>
	<div>
		<c:if test="${!onlysmsopen && begin && !stop}">
			<hk:form action="/epp/web/box_wapprvopenbox.do?companyId=${companyId}&navId=${navId }&boxId=${boxId }">
				<hk:submit value="epp.box.openbox" res="true"/>
			</hk:form>
		</c:if>
	</div>
</div>
<div class="row">
<a href="<%=path %>/epp/web/box_wap.do?companyId=${companyId}&navId=${navId}"><hk:data key="epp.return"/></a>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>