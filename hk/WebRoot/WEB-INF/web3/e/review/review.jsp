<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view.company.review" /></c:set>
<c:set var="page_url" scope="request"><%=path%>/cmpreview.do?companyId=${companyId}</c:set>
<c:set var="cmp_content" scope="request"><jsp:include page="review_inc.jsp"></jsp:include></c:set>
<jsp:include page="../inc/cmpframe.jsp"></jsp:include>