<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.photo"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang odd">
		<c:if test="${not empty cmpPhoto.name}">${cmpPhoto.name }<br/></c:if>
		<img src="${cmpPhoto.pic240 }"/><br/>
		<hk:a href="/epp/mgr/photo_list.do?companyId=${companyId}&page=${repage }"><hk:data key="view.return"/></hk:a> 
		<hk:a href="/epp/mgr/photo_del.do?companyId=${companyId}&photoId=${photoId }&page=${repage }"><hk:data key="view.delete"/></hk:a> 
		<hk:a href="/epp/mgr/photo_sethead.do?companyId=${companyId}&photoId=${photoId }&page=${repage }"><hk:data key="view.mgrsite.photo.sethead"/></hk:a>  
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>