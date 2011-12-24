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
			<c:if test="${fn:length(kindlist)>0}">
				<div class="divrow">
				<c:forEach var="kind" items="${kindlist}"><a class="split-r" href="<%=path %>/epp/web/cmpsellnet.do?companyId=${companyId}&kindId=${kind.kindId}&navId=${navId}">${kind.name }</a></c:forEach>
				</div>
			</c:if>
			<c:forEach var="n" items="${list}">
				<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg3'" onmouseout="this.className='divrow bdtm'">
					<a href="<%=path %>/epp/web/cmpsellnet_map.do?companyId=${companyId}&oid=${n.oid }&navId=${navId}">${n.name }</a> <br/>
					<strong><hk:data key="epp.cmpsellnet.tel"/>：</strong>${n.tel }<br/>
					<strong><hk:data key="epp.cmpsellnet.addr"/>：</strong>${n.addr }
				</div>
			</c:forEach>
			<c:if test="${fn:length(list)==0}">
				<div class="nodata"><hk:data key="epp.cmpsellnet.nodata"/></div>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path %>/epp/web/cmpsellnet.do?companyId=${companyId}&kindId=${kindId}&navId=${navId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>