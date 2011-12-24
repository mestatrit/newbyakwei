<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${sys_zone_pcity.city.city} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${cityId>0}"><c:set var="city_css">nn</c:set></c:if>
		<c:if test="${other>0}"><c:set var="other_css">nn</c:set></c:if>
		<hk:a clazz="${city_css}" href="/op/user_findcmp.do?ch=1&cityId=${sys_zone_pcityId}">${sys_zone_pcity.city.city }</hk:a> |
		<hk:a clazz="${other_css}" href="/op/user_findcmp.do?ch=1&other=1">其他地区</hk:a> |
		<hk:a href="/index_changecity2.do" needreturnurl="true">切换地区</hk:a>
	</div>
	<div class="hang even">
		<hk:form method="get" action="/op/user_findcmp.do">
			<hk:hide name="ch" value="1"/>
			<hk:hide name="other" value="${other}"/>
			<hk:hide name="cityId" value="${cityId}"/>
			<hk:text name="name" value="${name}"/>
			<hk:submit value="搜索足迹"/>
		</hk:form>
	</div>
	<c:if test="${ch==0}">
		<div class="hang">最近报到</div>
		<c:forEach var="u" items="${cmpcheckinuserlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }"><hk:a href="/e/cmp.do?companyId=${u.companyId}">${u.company.name}</hk:a></div>
		</c:forEach>
		<hk:simplepage2 href="/op/user_findcmp.do"/>
	</c:if>
	<c:if test="${ch>0}">
		<jsp:include page="../inc/companylist_inc.jsp"></jsp:include>
		<hk:simplepage2 href="/op/user_findcmp.do?cityId=${cityId}&other=${other }&name=${enc_name }"/>
		<c:if test="${fn:length(companylist)==0}">
			<hk:data key="view2.nocmpdata"/>
			<div class="hang">
				<hk:a href="/e/op/op_toadd.do?name=${enc_name }">创建足迹${name}</hk:a>
			</div>
		</c:if>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>