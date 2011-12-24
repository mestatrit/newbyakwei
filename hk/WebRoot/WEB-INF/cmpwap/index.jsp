<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="row">
	${o.name }<br/>
	<c:if test="${not empty o.tel}">${o.tel }<br/></c:if>
	<c:if test="${not empty o.addr}">${o.addr }<br/></c:if>
	<c:if test="${not empty o.traffic}">${o.traffic }<br/></c:if>
	<c:if test="${not empty o.intro}">${o.intro }<br/></c:if>
<c:if test="${not empty o.headPath}">
<img src="${o.head240}"/>
</c:if>
</div>
<c:forEach var="nav" items="${navlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
<div class="row ${clazz_var }">
	<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId }&navId=${nav.oid}">${nav.name }</a>
</div>
</c:forEach>
<c:if test="${loginUser==null}">
<div class="row">
<hk:form action="/epp/login_validate.do" needreturnurl="true">
	<hk:hide name="companyId" value="${companyId}"/>
	<div class="ha">
		<hk:data key="epp.login.input"/>:<br/>
		<hk:text name="input" value="${input}"/>
	</div>
	<div class="ha">
		<hk:data key="epp.login.pwd"/>:<br/>
		<hk:pwd name="password"/>
	</div>
	<div class="ha"><hk:submit value="epp.login" res="true"/></div>
</hk:form>
</div>
</c:if>
</c:set>
<jsp:include page="inc/frame.jsp"></jsp:include>