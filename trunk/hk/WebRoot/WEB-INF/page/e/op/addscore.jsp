<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.List"%><%@page import="com.hk.svr.impl.CompanyScoreConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.company.addscore"/></c:set>
<%List<CompanyScoreConfig> list=CompanyScoreConfig.getList();
request.setAttribute("companyScoreConfigList",list);%>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even" onkeydown="submitLaba(event)">
		<hk:form action="/review/op/op_addscore.do">
			${company.name }<br/>
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:data key="view.company.addscore"/>:<br/>
			<hk:select name="score" checkedvalue="${companyUserScore.score}">
				<hk:option value="0" data="view.notsetscore" res="true"/>
				<c:forEach var="conf" items="${companyScoreConfigList}">
				<hk:option value="${conf.score}" data="company.score_${conf.score}" res="true"/>
				</c:forEach>
			</hk:select><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/cmp.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>