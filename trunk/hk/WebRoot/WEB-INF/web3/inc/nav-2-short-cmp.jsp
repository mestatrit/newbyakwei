<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();%>
<div class="nav-2">
	<br class="linefix" />
	<div class="subnav short">
		<div class="l"></div>
		<div class="mid">
			<ul>
				<li class="path">
					<ul>
						<li><a class="home" href="http://<%=HkWebConfig.getWebDomain()%>"></a></li>
						<li><a class="nav-a" href="<%=path %>/cmp_pklist.do?parentId=${jsp_company.parentKindId }&${url_add }">${jsp_company.parentKind.name }</a></li>
						<li><a class="nav-a" href="<%=path %>/cmp_klist.do?kindId=${jsp_company.kindId }&${url_add }">${jsp_company.companyKind.name }</a></li>
						<li><a class="nav-a" href="<%=path %>/cmp.do?companyId=${companyId }&kindId=${kindId }&${url_add }">${jsp_company.name }</a></li>
						<li>${nav_2_short_content }</li>
					</ul>
				</li>
			</ul>
			<div class="clr"></div>
		</div>
		<div class="r"></div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
</div>