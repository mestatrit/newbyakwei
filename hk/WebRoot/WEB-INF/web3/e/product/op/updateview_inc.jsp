<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%String path=request.getContextPath(); %>
<div id="data" style="display: none;">
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="head">
			<a href="<%=path%>/home_web.do?userId=${loginUser.userId }"><img src="${loginUser.head48Pic }" /><br />${loginUser.nickName }</a>
		</td>
		<td class="review-body">
			<ul>
				<li class="f_l">
				<img src="<%=path %>/webst3/img/stars/star${reviewvo.cmpProductReview.star }0.gif" /></li>
				<li class="f_r ruo"><fmt:formatDate value="${reviewvo.cmpProductReview.createTime}" pattern="yy-MM-dd" /></li>
			</ul>
			<div class="clr"></div>
			${reviewvo.content} 
			<c:if test="${loginUser.userId==reviewvo.cmpProductReview.userId}"><a id="editcmpproductreview${reviewvo.cmpProductReview.labaId }" href="javascript:toeditcmpproductreview(${reviewvo.cmpProductReview.labaId })">修改</a></c:if>
		</td>
	</tr>
</table>
</div>
<script type="text/javascript">
parent.loadNewCmpProductReview(${reviewvo.cmpProductReview.labaId},document.getElementById('data').innerHTML);
</script>
