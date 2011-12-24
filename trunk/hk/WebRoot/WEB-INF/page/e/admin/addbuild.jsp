<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.admin.addbuild.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:rmBlankLines rm="true">
			<c:if test="${cityId>0}">
			${city.city }
			</c:if>
			<c:if test="${provinceId>0}">
			${province.province }
			</c:if>|
			<hk:a href="/e/admin/admin_findcity.do?f=build"><hk:data key="e.admin.addbizcle.changecity"/></hk:a>
		</hk:rmBlankLines>
	</div>
	<hk:form action="/e/admin/admin_addbuild.do">
		<hk:hide name="cityId" value="${cityId}"/>
		<hk:hide name="provinceId" value="${provinceId}"/>
		<div class="hang even"><hk:data key="buildingtag.name"/></div>
		<div class="hang odd"><hk:text name="name" value="${name}"/></div>
		<hk:submit value="e.admin.addbuild.sbumit" res="true"/>
	</hk:form>
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
	<div class="hang"><hk:a href="/e/admin/admin_buildlist.do?cityId=${cityId}&provinceId=${provinceId}"><hk:data key="e.admin.addbuild.tobuildlist"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>