<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.order.set"/></c:set>
<c:set var="mgr_content" scope="request">
<h3><hk:data key="view.company.order.home.set_zone" arg0="${zone}"/></h3>
<div>
<form id="order_frm" method="post" onsubmit="return suborderfrm(this.id)" action="<%=path %>/e/op/cmporder_editorder.do" target="hideframe">
	<hk:hide name="move_oid" value="${move_oid}"/>
	<hk:hide name="cityId" value="${cityId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="kind" value="${kind}"/>
<table class="infotable" cellpadding="0" cellspacing="0" width="100%">
	<c:if test="${cannotfmodify}">
		<tr>
			<td width="90px"></td>
			<td>
				<div class="errorlong">
					<strong class="text_14"><hk:data key="view.company.order.update_only_once_per_day"/></strong>
				</div>
			</td>
		</tr>
	</c:if>
	<tr>
		<td width="90px"></td>
		<td><strong class="text_14">目前余额${company.money }<br/>到期时间<fmt:formatDate value="${o.overTime}" pattern="yyyy-MM-dd"/></strong><br/></td>
	</tr>
	<tr>
		<td width="90px">竞价</td>
		<td>
			<div class="f_l" style="width: 200px;">
			<c:if test="${move_money!=null && move_money>0}">
				<hk:text name="money" value="${move_money}" maxlength="50" clazz="text_short_1"/>元<br/>
			</c:if>
			<c:if test="${move_money==null || move_money<=0}">
				<hk:text name="money" value="${o.money}" maxlength="50" clazz="text_short_1"/>元<br/>
			</c:if>
				<div id="hkobjorder_money_error" class="error"></div>
			</div>
			<div id="hkobjorder_money_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<tr>
		<td width="90px">竞价期限</td>
		<td>
			<div class="f_l" style="width: 200px;">
				<hk:radioarea name="addflg" checkedvalue="0">
					<hk:radio value="0" data="增加"/>
					<hk:radio value="1" data="减少"/>
				</hk:radioarea><br/>
				<hk:text name="pday" maxlength="10" clazz="text_short_1"/>天<br/>
				<div id="hkobjorder_pday_error" class="error"></div>
			</div>
			<div id="hkobjorder_pday_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<tr>
		<td width="90px"></td>
		<td>
			<div class="f_l">
				<c:if test="${cannotfmodify==null || !cannotfmodify}">
				<hk:submit value="提交" clazz="btn size1"/>
				</c:if>
			</div>
		</td>
	</tr>
</table>
</form>
</div>
<script type="text/javascript">
function suborderfrm(frmid){
	clearValidate();
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!=0){
		if(error==206){
			alert("酷币余额不足，不能购买");
		}
		else{
			$('#'+obj_id_param+'_error').html(error_msg);
			getObj(obj_id_param+'_flag').className="flag error";
		}
		hideSubmitDiv();
	}
	else{
		tourl("<%=path %>/e/op/cmporder_myorderlist.do?companyId=${companyId}");
	}
}
function returnweb(){
	tourl("<%=path %>/e/op/cmporder_myorderlist.do?companyId=${companyId}");
}
function clearValidate(){
	setHtml("hkobjorder_pday_error","");
	getObj("hkobjorder_pday_flag").className="flag";
	setHtml("hkobjorder_money_error","");
	getObj("hkobjorder_money_flag").className="flag";
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>