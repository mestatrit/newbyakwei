<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.List"%><%@page import="com.hk.svr.impl.CompanyScoreConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%List<CompanyScoreConfig> list=CompanyScoreConfig.getList();
request.setAttribute("companyScoreConfigList",list);%>
<c:if test="${companyreviewvo!=null}">
<c:set var="score" value="${companyreviewvo.companyReview.score}"/>
</c:if>
<c:if test="${companyUserScore!=null}">
<c:set var="score" value="${companyUserScore.score}"/>
</c:if>
<hk:form name="labafrm" onsubmit="return confirmCreate()" action="${form_action }">
	<hk:hide name="labaId" value="${labaId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="repage" value="${repage}"/>
	<hk:hide name="returnurl" value="${returnurl}"/>
	<hk:hide name="backurl" value="${backurl}"/>
	<hk:data key="view.setscoreabel"/>:<br/>
	<hk:select name="score" checkedvalue="${score}">
		<hk:option value="0" data="view.notsetscore" res="true"/>
		<c:forEach var="conf" items="${companyScoreConfigList}">
		<hk:option value="${conf.score}" data="company.score_${conf.score}" res="true"/>
		</c:forEach>
	</hk:select><br/>
	<hk:data key="view.inputyourreview"/>:<br/>
	<hk:textarea oid="status" name="content" clazz="ipt2" rows="2" value="${companyreviewvo.content}"/><br/>
	<hk:submit value="view.submit" res="true"/> <span id="remaining" class="ruo s">140</span><span class="ruo s"><hk:data key="view.char"/></span>
</hk:form>
<jsp:include page="labainputjs.jsp"></jsp:include>