<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.notice"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px">
<h1 class="bdtm"><hk:data key="view2.notice"/></h1>
<c:forEach var="sortVo" items="${list}">
	<c:if test="${fn:length(sortVo.list)>0}">
		<div class="divrow">
			<span class="b">${sortVo.noticeTypeIntro}</span>
		</div>
			<c:forEach var="n" items="${sortVo.list}" varStatus="idx">
				<div style="padding-left: 20px;" class="divrow bdtm" onmouseover="this.className='divrow bdtm bg2';" onmouseout="this.className='divrow bdtm';">
					${n.content} 
					<span class="ruo2"><fmt:formatDate value="${n.notice.createTime}" pattern="yy-MM-dd HH:mm"/> </span>
				</div>
			</c:forEach>
		<div><a href="<%=path %>/h4/op/notice_list.do?noticeType=${sortVo.noticeType}">更多</a></div>
	</c:if>
</c:forEach>
</div>
<script type="text/javascript">
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>