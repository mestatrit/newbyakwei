<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
</div>
<div class="row">
<hk:form action="/epp/web/cmpmsg_msgwapcreate.do">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="navId" value="${navId}"/>
	<hk:data key="epp.cmpmsg.name"/>：<br/>
	<hk:text name="name" value="${cmpMsg.name}"/><br/><br/>
	<hk:data key="epp.cmpmsg.tel"/>：<br/>
	<hk:text name="tel" value="${cmpMsg.tel}"/><br/><br/>
	<hk:data key="epp.cmpmsg.content"/>：<br/>
	<hk:textarea name="content" value="${cmpMsg.content}"/><br/>
	<hk:submit value="epp.submit" res="true"/>
</hk:form>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>