<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${company.name } - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang even">${company.name }</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<img src="${p.pic60 }"/> 
			<hk:a href="/e/op/photo/photo_showbig.do?companyId=${companyId}&photoId=${p.photoId }" page="true"><hk:data key="view.company.photo.bigphoto"/></hk:a> 
			<hk:a href="/e/op/photo/photo_del.do?companyId=${companyId}&photoId=${p.photoId }" page="true"><hk:data key="view.company.photo.delete"/></hk:a> 
			<c:if test="${company.headPath!=p.path}">
				<hk:a href="/e/op/photo/photo_sethead.do?companyId=${companyId}&photoId=${p.photoId }"><hk:data key="view.company.photo.sethead"/></hk:a>  
			</c:if>
		</div>
		</c:forEach>
	</c:if>
	<div class="hang">
	<hk:simplepage href="/e/op/photo/photo_smallphoto.do?companyId=${companyId}"/>
	</div>
	<div class="hang"><hk:a href="/e/op/op_toedit.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>