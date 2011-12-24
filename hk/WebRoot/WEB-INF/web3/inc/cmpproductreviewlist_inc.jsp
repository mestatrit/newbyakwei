<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="iteratorreview">
	<c:forEach var="reviewvo" items="${cmpproductreviewvolist}">
		<li class="review" id="review${reviewvo.cmpProductReview.labaId }">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="head">
						<a href="<%=path%>/home_web.do?userId=${reviewvo.cmpProductReview.userId }"><img src="${reviewvo.cmpProductReview.user.head48Pic }" /><br />${reviewvo.cmpProductReview.user.nickName }</a>
					</td>
					<td class="review-body">
						<ul>
							<li class="f_l">
							<c:if test="${reviewvo.cmpProductReview.score>0}">
							<img src="<%=path %>/webst3/img/stars/star${reviewvo.cmpProductReview.star }0.gif" /></c:if>
							</li>
							<li class="f_r ruo"><fmt:formatDate value="${reviewvo.cmpProductReview.createTime}" pattern="yy-MM-dd" /></li>
						</ul>
						<div class="clr"></div>
						${reviewvo.content} 
						<div align="right">
							<c:if test="${loginUser.userId==reviewvo.cmpProductReview.userId}">
								<a id="editcmpproductreview${reviewvo.cmpProductReview.labaId }" href="javascript:toeditcmpproductreview(${reviewvo.cmpProductReview.labaId })">修改</a> 
							</c:if>
							<c:if test="${loginUser.userId==reviewvo.cmpProductReview.userId || company.userId==loginUser.userId || company.createrId==loginUser.userId}">
								<a id="editcmpproductreview${reviewvo.cmpProductReview.labaId }" href="javascript:todelcmpproductreview(${reviewvo.cmpProductReview.labaId })">删除</a>
							</c:if>
						</div>
					</td>
				</tr>
			</table>
		</li>
	</c:forEach>
</c:set>
<ul class="usercmpreviewlist2" id="usercmpreviewlist2"><c:if test="${iteratorreview!=null}">${iteratorreview}</c:if></ul>
<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
<script type="text/javascript">
var tmp_data='';
function todelcmpproductreview(labaId){
	if(window.confirm("确实要删除？")){
		tourl("<%=path%>/op/product_delreview.do?labaId="+labaId);
	}
}
function toeditcmpproductreview(labaId){
	showSubmitDivForObj("editcmpproductreview"+labaId);
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/product_loadreviewforedit.do?labaId='+labaId,
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			var title='修改点评';
			var html=data;
			createCenterWindow("review_win",600,300,title,html,"oncloseWin();");
			hideSubmitDiv("editcmpproductreview"+labaId);
		}
	})
}
function oncloseWin(){
	//setHtml('createcmpproductreviewcon',tmp_data);
	hideWindow('review_win');
	clearBg();
}
</script>