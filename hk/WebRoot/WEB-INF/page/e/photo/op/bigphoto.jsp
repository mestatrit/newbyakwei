<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${company.name } - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${not empty o.name}">${o.name }<br/></c:if>
		<img src="${o.pic320 }"/><br/>
		<hk:a href="/e/op/photo/photo_smallphoto.do?companyId=${companyId}&page=${repage }"><hk:data key="view.return"/></hk:a> 
		<hk:a href="/e/op/photo/photo_del.do?companyId=${companyId}&photoId=${photoId }" page="true"><hk:data key="view.company.photo.delete"/></hk:a> 
		<c:if test="${company.headPath!=p.path}">
			<hk:a href="/e/op/photo/photo_sethead.do?companyId=${companyId}&photoId=${photoId }"><hk:data key="view.company.photo.sethead"/></hk:a>  
		</c:if>
	</div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>