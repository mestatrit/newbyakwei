<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.svr.CompanyService"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="com.hk.bean.CmpZoneInfo"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.inputcityname"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form method="get" action="/index_searchcity.do">
			<hk:hide name="show_country" value="${show_country}"/>
			<hk:hide name="fn" value="${fn}"/>
			<hk:hide name="kindId" value="${kindId}"/>
			<hk:hide name="countryId" value="${countryId}"/>
			<hk:data key="view.inputcityname"/>:<br/>
			<hk:text name="name" value="${name}"/><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${noresult}"><hk:data key="nodataview"/></c:if>
<%CompanyService companyService=(CompanyService)HkUtil.getBean("companyService");
List<CmpZoneInfo> cmpzoneinfoList = companyService.getCmpZoneInfoList();
request.setAttribute("cmpzoneinfoList", cmpzoneinfoList); %>
	<c:if test="${fn:length(cmpzoneinfoList)>0}">
	<div class="hang">
			<c:if test="${show_country}">
				<hk:a href="/index_routefn.do?fn=${fn}&cityId=0&provinceId=0">全国</hk:a>
			</c:if> 
		<c:forEach var="z" items="${cmpzoneinfoList}">
			<hk:a href="/index_routefn.do?fn=${fn}&cityId=${z.pcityId }">${z.name}</hk:a> 
		</c:forEach>
	</div>
	</c:if>
	<c:if test="${hide_all!=1}"><div class="hang even"><hk:a href="/index_all.do"><hk:data key="view.zone_all"/></hk:a></div></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>