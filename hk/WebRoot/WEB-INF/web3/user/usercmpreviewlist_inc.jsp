<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="iteratorreview">
	<c:forEach var="vo" items="${usercmpreviewvolist}">
		<li class="review" onmouseout="this.className='review';" onmouseover="this.className='review bg3';">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="head">
						<a href="<%=path%>/cmp.do?companyId=${vo.company.companyId }"><img src="${vo.company.head60 }" /><br />${vo.company.name }</a>
					</td>
					<td class="review-body">
						<ul>
							<li class="f_l"><img src="<%=path %>/webst3/img/stars/star${vo.company.starsLevel }.gif" /></li>
							<li class="f_r ruo"><fmt:formatDate value="${vo.companyReviewVo.companyReview.createTime}" pattern="yy-MM-dd" /></li>
						</ul>
						<div class="clr"></div>
						${vo.companyReviewVo.content}
					</td>
				</tr>
			</table>
		</li>
	</c:forEach>
</c:set>
<ul class="usercmpreviewlist2" id="usercmpreviewlist2"><c:if test="${iteratorreview!=null}">${iteratorreview}</c:if></ul>
<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>