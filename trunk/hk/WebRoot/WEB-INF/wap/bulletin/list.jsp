<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.bulletin"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">${o.name }的公告</div>
	<c:if test="${fn:length(cmpbulletinlist)>0}">
		<c:forEach var="b" items="${cmpbulletinlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/epp/bulletin.do?companyId=${companyId}&bulletinId=${b.bulletinId }">${b.title}</hk:a> 
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(cmpbulletinlist)==0}"><div class="hang even">目前没有公告</div></c:if>
	<hk:simplepage2 href="/epp/bulletin_list.do?companyId=${companyId}"/>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }">返回首页</a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>