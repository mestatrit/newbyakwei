<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.frame.util.page.SimplePage"%><%@page import="com.hk.frame.util.HkUtil"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.photo"/></c:set>
<%SimplePage simplePage=(SimplePage)request.getAttribute(HkUtil.SIMPLEPAGE_ATTRIBUTE);
request.setAttribute("simplePage",simplePage);
%>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:if test="${fn:length(list)==0}"><div class="hang even">暂时没有图片</div></c:if>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="cmpPhoto" items="${list}">
			<div class="hang odd">
				<c:if test="${not empty cmpPhoto.name}">${cmpPhoto.name }<br/></c:if>
				<img src="${cmpPhoto.pic240 }"/><br/>
				<c:if test="${simplePage.hasNext}">
					<hk:a clazz="line" href="/epp/photo_ignorehead.do?companyId=${companyId}&page=${simplePage.page+1}"><hk:data key="view.nextpic"/></hk:a> 
				</c:if>
				<c:if test="${simplePage.page>1}">
					<hk:a clazz="line" href="/epp/photo_ignorehead.do?companyId=${companyId}&page=${simplePage.page-1}"><hk:data key="view.prepic"/></hk:a> 
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>