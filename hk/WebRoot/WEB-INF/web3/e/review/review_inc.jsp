<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="iteratorreview">
	<c:forEach var="reviewvo" items="${companyreviewvolist}">
		<li class="review" onmouseout="this.className='review'" onmouseover="this.className='review bg3'">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="head">
						<a href="<%=path %>/home.do?userId=${reviewvo.user.userId }"><img src="${reviewvo.user.head48Pic }"/><br/>${reviewvo.user.nickName }</a>
					</td>
					<td class="review-body">
						<ul>
							<li class="f_l"><img class="imgd" src="<%=path %>/webst3/img/stars/star${reviewvo.starRate }0.gif" /></li>
							<li class="f_r ruo"><fmt:formatDate value="${reviewvo.companyReview.createTime}" pattern="yy-MM-dd HH:mm"/></li>
						</ul>
						<div class="clr"></div>
						${reviewvo.content }<c:if test="${not empty reviewvo.companyReview.longContent}"><hk:a clazz="line" href="/laba/laba.do?labaId=${reviewvo.companyReview.labaId}">...</hk:a></c:if>
						<c:if test="${reviewvo.companyReview.userId==loginUser.userId}">
							<a id="cmpreview${reviewvo.companyReview.labaId }" class="edit text_12" href="javascript:toeditcmpreview(${reviewvo.companyReview.labaId })">修改</a>
						</c:if>
					</td>
				</tr>
			</table>
		</li>
	</c:forEach>
</c:set>
<ul class="usercmpreviewlist2" id="usercmpreviewlist2"><c:if test="${iteratorreview!=null}">${iteratorreview}</c:if></ul>
<jsp:include page="../../inc/pagesupport_inc2.jsp"></jsp:include>
<script type="text/javascript">
function toeditcmpreview(labaId){
	showSubmitDivForObj('cmpreview'+labaId);
	$.ajax({
		type:"POST",
		url:'<%=path %>/review/op/op_loadreview.do?labaId='+labaId,
		cache:false,
    	dataType:"html",
		success:function(data){
			createSimpleCenterWindow('edit_cmpreview',550, 300, "修改点评", data,"hideWindow('edit_cmpreview')");
			hideSubmitDiv();
		}
	})
}
function keydown(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(subcmpreviewfrm("review_frm_edit_cmpreview")){
			getObj("review_frm_edit_cmpreview").submit();
		}
	}
}
function subcmpreviewfrm(frmid){
	if(!user_login){
		alert("请先登录");
		return false;
	}
	setHtml("review_score_error_edit_cmpreview","");
	if(getObj("id_score_edit_cmpreview").value=="0" && isEmpty(getObj("id_content_edit_cmpreview").value)){
		setHtml("review_score_error_edit_cmpreview","请正确评分和写下你的评论");
		return false;
	}
	if(trim(getObj("id_content_edit_cmpreview").value).length>500){
		alert("评论内容不能超过500字");
		return false;
	}
	showSubmitDiv(frmid);
	return true;
}
function edit_reviewerror(error,error_msg,respValue){
	hideSubmitDiv();
	alert(error_msg);
}
function edit_reviewok(error,error_msg,respValue){
	refreshurl();
}
</script>