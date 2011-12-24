<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.order.kind"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
<h3>设置在${zone }${companyKind.name }中的排名信息</h3>
	<div class="bdbtm"></div>
	<form id="order_frm" method="post" onsubmit="return suborderfrm(this.id)" action="<%=path %>/e/op/cmporder_editkindorder.do" target="hideframe">
	<hk:hide name="move_oid" value="${move_oid}"/>
	<hk:hide name="cityId" value="${cityId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="kindId" value="${kindId}"/>
	<div style="padding-left: 70px;">
		<table class="infotable" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="90px">目前余额</td>
				<td><strong style="padding-left: 30px;">${company.money }</strong><br/><br/></td>
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
						<div id="hkobjkindorder_money_error" class="error"></div>
					</div>
					<div id="hkobjkindorder_money_flag" class="flag"></div><div class="clr"></div>
				</td>
			</tr>
			<tr>
				<td width="90px">竞价期限</td>
				<td>
					<div class="f_l" style="width: 200px;">
						<hk:text name="pday" value="${o.pday}" maxlength="50" clazz="text_short_1"/>天<br/>
						<div id="hkobjkindorder_pday_error" class="error"></div>
					</div>
					<div id="hkobjkindorder_pday_flag" class="flag"></div><div class="clr"></div>
				</td>
			</tr>
			<tr>
				<td width="90px"></td>
				<td>
					<div class="f_l">
						<div ><hk:submit value="提交" clazz="btn size1"/>
							<a href="javascript:returnweb();" onclick="">返回</a>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
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
		tourl("<%=path %>/e/op/cmporder_myorderlist.do");
	}
}
function returnweb(){
	tourl("<%=path %>/e/op/cmporder_myorderlist.do");
}
function clearValidate(){
	setHtml("hkobjkindorder_pday_error","");
	getObj("hkobjkindorder_pday_flag").className="flag";
	setHtml("hkobjkindorder_money_error","");
	getObj("hkobjkindorder_money_flag").className="flag";
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>