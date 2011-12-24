<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${company.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter"  style="width: 700px">
<h1 class="title2"><a href="/venue/${companyId }/">${company.name }</a></h1>
${company.intro }<br/>
<a class="more2" href="/venue/${companyId }/"><hk:data key="view2.return"/></a>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>