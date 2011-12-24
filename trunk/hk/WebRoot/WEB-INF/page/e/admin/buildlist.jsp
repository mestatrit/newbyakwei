<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.admin.buildlist.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:rmBlankLines rm="true">
			<c:if test="${cityId>0}">${city.city }|</c:if>	
			<c:if test="${provinceId>0}">${province.province }|</c:if>	
			<hk:a href="/e/admin/admin_findcity.do?f=buildlist"><hk:data key="view.selectcityzone"/></hk:a>
		</hk:rmBlankLines>
	</div>
	<div class="hang">
		<hk:form method="get" action="/e/admin/admin_buildlist.do">
			<hk:hide name="cityId" value="${cityId}"/>
			<hk:hide name="provinceId" value="${provinceId}"/>
			<hk:data key="e.admin.buildlist.name"/>:<br/>
			<hk:text name="name" value="${name}"/><br/>
			<hk:submit value="e.admin.buildlist.find" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:set var="addstr" value="name=${enc_name}&cityId=${cityId }&provinceId=${provinceId }"></c:set>
		<c:forEach var="c" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
			${c.name } 
			<hk:a href="/e/admin/admin_tocfmdelbuild.do?tid=${c.tagId}&${addstr }" page="true">删</hk:a>
			<hk:a href="/e/admin/admin_toeditbuild.do?tid=${c.tagId}&${addstr }" page="true">改</hk:a>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/e/admin/admin_toaddbuild.do?cityId=${cityId }&provinceId=${provinceId }"><hk:data key="e.admin.buildlist.addbuild"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>