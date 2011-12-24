<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.photo"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:data key="view.mgrsite.photo"/></div>
	<div class="hang"><hk:a href="/epp/mgr/photo_toadd.do?companyId=${companyId}"><hk:data key="view.mgrsite.addphoto"/></hk:a></div>
		<c:if test="${fn:length(list)>0}">
			<c:forEach var="cp" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<img src="${cp.pic60 }"/> 
				<hk:a href="/epp/mgr/photo_big.do?companyId=${companyId}&photoId=${cp.photoId }" page="true"><hk:data key="view.mgrsite.photo.shobig"/></hk:a> 
				<hk:a href="/epp/mgr/photo_del.do?companyId=${companyId}&photoId=${cp.photoId }" page="true"><hk:data key="view.delete"/></hk:a> 
				<hk:a href="/epp/mgr/photo_sethead.do?companyId=${companyId}&photoId=${cp.photoId }"><hk:data key="view.mgrsite.photo.sethead"/></hk:a>  
			</div>
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(list)==0}"><div class="hang">暂时没有图片</div></c:if>
		<hk:simplepage href="/epp/mgr/photo_list.do?companyId=${companyId}"/>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>