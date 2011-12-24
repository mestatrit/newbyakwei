<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="mgr_content" scope="request">
	<div>
		<c:set var="box_form_action" scope="request"><%=path %>/box/op/op_editboxweb.do</c:set>
		<jsp:include page="boxform.jsp"></jsp:include>
	</div>
<script type="text/javascript">
function subboxfrm(frmid){
	validateClear('box_name');
	validateClear('box_totalCount');
	validateClear('box_beginTime');
	validateClear('box_endTime');
	validateClear('box_boxKey');
	validateClear('box_intro');
	validateClear('box_precount');
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!=0){
		if(parseInt()==236){
			alert('没有足够的酷币宝箱数量');
			return;
		}
		validateErr(obj_id_param,error_msg);
		hideSubmitDiv();
	}
	else{
		tourl("<%=path %>/box/op/op_boxweb.do?boxId=${boxId}");
	}
}

$(function()
	{
		$('.date-pick1').datePicker({clickInput:true,createButton:false,startDate:'2009-01-01'});
		$('.date-pick2').datePicker({clickInput:true,createButton:false,startDate:'2009-01-01'});
	});
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>