<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<%EppViewUtil.loadCmpPageBlock(request); %>
<c:set scope="request" var="html_body_content">
<div style="font-size: 18px">
信息提交成功！
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>