<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${vo.company.name } - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<c:set var="url" value="/e/cmp.do?companyId=${companyId}"/>
	<div class="hang even"><hk:data key="view.who's_photo" arg0="${url}" arg1="${vo.company.name}"/></div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
		<c:if test="${not empty p.name}">${p.name }<br/></c:if>
		<img src="${p.pic240 }"/><br/>
		<c:if test="${nextPage!=-1}">
			<hk:a clazz="line" href="/e/photo.do?companyId=${companyId}&page=${nextPage}"><hk:data key="view.nextpic"/></hk:a> 
		</c:if>
		<c:if test="${prePage!=-1}">
			<hk:a clazz="line" href="/e/photo.do?companyId=${companyId}&page=${prePage}"><hk:data key="view.prepic"/></hk:a> 
		</c:if>
		<c:if test="${vo.company.headPath==p.path}"><hk:data key="view.currenthead"/></c:if>
		</div>
		<div class="hang">
			<hk:a href="/e/cmp.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a> 
			<c:if test="${(loginUser.userId==p.userId) || canmgrphoto }">
				<hk:a href="/e/op/photo/photo_del.do?companyId=${companyId}&photoId=${p.photoId }"><hk:data key="view.delete"/></hk:a>
			</c:if>		
		</div>
		</c:forEach>
	</c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>