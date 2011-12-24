<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<hk:form action="/epp/web/cmpnav_wap.do" needreturnurl="true">
<hk:hide name="companyId" value="${companyId}"/>
<hk:hide name="ch" value="1"/>
<hk:hide name="navId" value="${navId}"/>
<hk:data key="epp.url.view.tip"/><br/>
<hk:submit name="ok" value="epp.confirm" res="true"/>
<hk:submit name="cancel" value="epp.cancel" res="true"/>
</hk:form>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>