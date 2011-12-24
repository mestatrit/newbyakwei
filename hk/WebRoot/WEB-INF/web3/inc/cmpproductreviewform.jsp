<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.List"%><%@page import="com.hk.svr.impl.CompanyScoreConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:if test="${empty cmpproductreview_form_submitFunctionName}">
<c:set var="cmpproductreview_form_submitFunctionName">subaddcmpproductreviewfrm</c:set>
</c:if>
<hk:form oid="review_frm${cmpproductreview_form_id_prefix }" onsubmit="return ${cmpproductreview_form_submitFunctionName}${cmpproductreview_form_id_prefix }(this.id)" action="${cmpproductreview_form_action}" target="hideframe">
	<a name="write_review"></a>
	<hk:hide name="productId" value="${pid}"/>
	<hk:hide name="labaId" value="${labaId}"/>
		<table class="infotable" cellpadding="0" cellspacing="0" width="600px">
			<tr>
				<td>
				<div class="f_l" style="width: 500px;">
					<%List<CompanyScoreConfig> list=CompanyScoreConfig.getList();
						request.setAttribute("companyScoreConfigList",list);%>
					打分
					<hk:select oid="id_score${cmpproductreview_form_id_prefix }" name="score" checkedvalue="${cmpProductReviewVo.cmpProductReview.score}">
						<hk:option value="0" data="view.notsetscore" res="true"/>
						<c:forEach var="conf" items="${companyScoreConfigList}">
						<hk:option value="${conf.score}" data="company.score_${conf.score}" res="true"/>
						</c:forEach>
					</hk:select><span id="review_score_error${cmpproductreview_form_id_prefix }" class="error"></span><br/>
					
				</div>
				</td>
			</tr>
			<tr>
				<td>
					<hk:textarea oid="id_content${cmpproductreview_form_id_prefix }" name="content" value="${cmpProductReviewVo.content}" style="width:500px;height:100px"/><br/>
						<div id="review_content_error${cmpproductreview_form_id_prefix }" class="error"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<c:if test="${cmpproductreview_form_foredit!=null && cmpproductreview_form_foredit}">
						<hk:submit value="修改评论" clazz="btn"/>
						</c:if>
						<c:if test="${cmpproductreview_form_foredit==null || !cmpproductreview_form_foredit}">
						<hk:submit value="写新评论" clazz="btn"/>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
</hk:form>
<script type="text/javascript">
function subaddcmpproductreviewfrm${cmpproductreview_form_id_prefix }(frmid){
	if(!user_login){
		alert("请先登录");
		return false;
	}
	setHtml("review_score_error${cmpproductreview_form_id_prefix }","");
	if(getObj("id_score${cmpproductreview_form_id_prefix }").value=="0" && isEmpty(getObj("id_content${cmpproductreview_form_id_prefix }").value)){
		setHtml("review_score_error${cmpproductreview_form_id_prefix }","请正确评分和写下你的评论");
		return false;
	}
	if(trim(getObj("id_content${cmpproductreview_form_id_prefix }").value).length>500){
		alert("评论内容不能超过500字");
		return false;
	}
	showSubmitDiv(frmid);
	return true;
}
function oncmpproductreviewerror${cmpproductreview_form_id_prefix }(error,error_msg,op_func,obj_id_param,respValue){
	hideSubmitDiv();
	alert(error_msg);
}
function oncmpproductreviewok${cmpproductreview_form_id_prefix }(error,error_msg,op_func,obj_id_param,respValue){
	if(op_func=="editreview"){
	}
	else{
		hideSubmitDiv();
		refreshurl();
	}
}
function loadNewCmpProductReview(labaId,data){
	setHtml('review'+labaId,data);
	hideSubmitDiv();
	hideWindow('review_win');
	clearBg();
}
function keydown${cmpproductreview_form_id_prefix }(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(${cmpproductreview_form_submitFunctionName}${cmpproductreview_form_id_prefix }("review_frm${cmpproductreview_form_id_prefix }")){
			getObj("review_frm${cmpproductreview_form_id_prefix }").submit();
		}
	}
}
</script>