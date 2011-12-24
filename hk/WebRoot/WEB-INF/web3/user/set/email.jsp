<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.mgr.setemail" /></c:set>
<c:set var="mgr_content" scope="request">
	<div>
		<hk:form oid="emailfrm" method="post" onsubmit="return subfrm(this.id)" action="/user/set/set_setemailweb.do" target="hideframe">
		<table cellpadding="0" cellspacing="0" class="infotable">
			<tr>
				<td width="120px">
					<c:if test="${info.authedMail}"><hk:data key="view.inputnewemail" /></c:if>
					<c:if test="${!info.authedMail}"><hk:data key="view.inputemail" /></c:if>
				</td>
				<td>
					<hk:text name="email" value="${info.email}" maxlength="50" clazz="text"/><br/>
					<div class="error" id="msg_error"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="form_btn"><hk:submit value="提交并发送认证链接" /></div>
				</td>
			</tr>
		</table>
		</hk:form>
	</div>
<script type="text/javascript">
function subfrm(frmid){
	validateClear('msg');
	showSubmitDiv(frmid);
	return true;
}
function onemailerror(error,error_msg,op_func,obj_id_param){
	validateErr('msg',error_msg);
	hideSubmitDiv();
}
function onemailsuccess(error,error_msg,op_func,obj_id_param,respValue){
	tourl("<%=path %>/user/set/set_tosetemailweb.do");
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>