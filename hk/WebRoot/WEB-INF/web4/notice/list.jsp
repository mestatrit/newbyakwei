<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.notice"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px">
<div><h1><a href="<%=path %>/h4/op/notice.do"><hk:data key="view2.return"/></a></h1></div>
<c:forEach var="n" items="${noticevolist}" varStatus="idx">
	<div class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2';" onmouseout="this.className='divrow bdtm';">
		${n.content} 
		<span class="ruo2"><fmt:formatDate value="${n.notice.createTime}" pattern="yy-MM-dd HH:mm"/></span>
	</div>
</c:forEach>
<c:set var="page_url"><%=path %>/h4/op/notice_list.do?noticeType=${noticeType }</c:set>
<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
</div>
<script type="text/javascript">
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>