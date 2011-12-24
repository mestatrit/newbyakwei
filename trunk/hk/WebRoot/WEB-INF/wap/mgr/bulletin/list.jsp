<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.bulletin"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:data key="view.mgrsite.bulletin"/>|<hk:a href="/epp/mgr/bulletin_tocreate.do?companyId=${companyId}"><hk:data key="view.mgrsite.bulletin.create"/></hk:a></div>
	<c:if test="${fn:length(cmpbulletinlist)>0}">
		<c:forEach var="b" items="${cmpbulletinlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/epp/mgr/bulletin.do?companyId=${companyId}&bulletinId=${b.bulletinId }">${b.title}</hk:a> 
				<hk:a href="/epp/mgr/bulletin_del.do?companyId=${companyId}&bulletinId=${b.bulletinId }"><hk:data key="view.delete"/></hk:a>
			</div>
		</c:forEach>
	</c:if>
	<div class="hang even"><c:if test="${fn:length(cmpbulletinlist)==0}">没有公告数据</c:if></div>
	<hk:simplepage2 href="/epp/mgr/bulletin_list.do?companyId=${companyId}"/>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>