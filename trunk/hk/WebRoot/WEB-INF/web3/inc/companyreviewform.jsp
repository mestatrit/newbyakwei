<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.List"%><%@page import="com.hk.svr.impl.CompanyScoreConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:form oid="review_frm" onsubmit="return subaddreviewfrm(this.id)" action="${companyreview_form_action}" target="hideframe">
	<a name="write_review"></a>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="pcflg" value="1"/>
	<hk:hide name="labaId" value="${labaId}"/>
		<table class="infotable" cellpadding="0" cellspacing="0" width="600px">
			<tr>
				<td>
				<div class="f_l" style="width: 500px;">
					<%List<CompanyScoreConfig> list=CompanyScoreConfig.getList();
						request.setAttribute("companyScoreConfigList",list);%>
					打分
					<hk:select oid="id_score" name="score" checkedvalue="${companyreviewvo.companyReview.score}">
						<hk:option value="0" data="view.notsetscore" res="true"/>
						<c:forEach var="conf" items="${companyScoreConfigList}">
						<hk:option value="${conf.score}" data="company.score_${conf.score}" res="true"/>
						</c:forEach>
					</hk:select><span id="review_score_error" class="error"></span><br/>
					
				</div>
				</td>
			</tr>
			<tr>
				<td>
					<hk:textarea oid="id_content" name="content" value="${companyreviewvo.content}" style="width:500px;height:100px"/><br/>
						<div id="review_content_error" class="error"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<hk:submit value="提交" clazz="btn"/>
						<c:if test="${companyreview_form_showreturn}">
						<a style="padding-left: 20px" href="<%=path %>/cmp.do?companyId=${companyId }&${url_add }">返回</a>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
</hk:form>
<script type="text/javascript">
function subaddreviewfrm(frmid){
	if(!user_login){
		alert("请先登录");
		return false;
	}
	setHtml("review_score_error","");
	if(getObj("id_score").value=="0" && isEmpty(getObj("id_content").value)){
		setHtml("review_score_error","请正确评分和写下你的评论");
		return false;
	}
	if(trim(getObj("id_content").value).length>500){
		alert("评论内容不能超过500字");
		return false;
	}
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error==0){
		hideSubmitDiv();
		tourl("<%=path%>/cmp.do?companyId=${companyId}");
	}
	else{
		alert(error_msg);
	}
}
</script>