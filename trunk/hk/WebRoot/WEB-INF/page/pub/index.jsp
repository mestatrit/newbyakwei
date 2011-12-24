<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${sys_zone_pcity.city.city } - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<!-- 不用的页面 -->
	<div class="hang">
		<c:if test="${cityId==null}"><c:set var="css_all">nn</c:set></c:if>
		<c:if test="${cityId>0}"><c:set var="css_city">nn</c:set></c:if>
		<hk:a clazz="${css_city}" href="/index_city.do?cityId=${sys_zone_pcityId}">${sys_zone_pcity.city.city}</hk:a> |
		<hk:a clazz="${css_all}" href="/index_all.do">全部</hk:a>|
		<hk:a href="/index_changecity2.do">切换地区</hk:a><br/>
		<hk:form method="get" action="/e/cmp_list.do">
			<hk:hide name="cityId" value="${cityId}"/>
			<hk:text name="name"/><hk:submit value="搜索足迹"/>
		</hk:form>
	</div>
	<div class="hang even">
		<c:if test="${fn:length(companyvolist)>0}">
			<hk:data key="view.hkhottop"/><br/>
			<c:forEach var="vo" items="${companyvolist}">
				<div class="ha">
					<hk:a href="/e/cmp.do?companyId=${vo.company.companyId}">${vo.company.name}</hk:a>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(companyvolist)==0}"><hk:data key="view.hkhottop"/><br/>暂时没有数据</c:if>
	</div>
	<c:if test="${loginUser!=null}">
		<div class="hang even"><hk:data key="view.publiclaba"/></div>
		<c:set var="addlabastr" value="from=index2" scope="request"/>
		<jsp:include page="../inc/labavo2.jsp"></jsp:include>
		<c:if test="${listalllaba}"><c:set var="laba_url" value="/square.do"></c:set></c:if>
		<c:if test="${!listalllaba}"><c:set var="laba_url" value="/square.do?w=city&ipCityId=${ipCityId}"></c:set></c:if>
		<c:if test="${morelaba}"><div class="hang even"><hk:a href="${laba_url}"><hk:data key="view.more"/></hk:a></div></c:if>
	</c:if>
	<c:if test="${loginUser!=null}">
		<div class="hang even"><hk:data key="view.hktopuser"/><br/>
			<c:forEach var="u" items="${userlist}">
				<hk:a href="/home.do?userId=${u.userId}">${u.nickName} <c:if test="${u.fansCount>0}"><span class="ruo s">(<hk:data key="view.userfanscount" arg0="${u.fansCount}"/>)</span></c:if></hk:a><br/>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${listalluser}"><c:set var="user_url" value="/user/list2.do"></c:set></c:if>
	<c:if test="${!listalluser}"><c:set var="user_url" value="/user/list2.do?w=city&ipCityId=${ipCityId}"></c:set></c:if>
	<c:if test="${moreuser}"><div class="hang even"><hk:a href="${user_url }"><hk:data key="view.more"/></hk:a></div></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>