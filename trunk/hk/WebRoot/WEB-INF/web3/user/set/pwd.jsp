<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.mgr.setpwd" /></c:set>
<c:set var="mgr_content" scope="request">
	<div>
		<hk:form oid="emailfrm" method="post" onsubmit="return subfrm(this.id)" action="/user/set/set_setpwdweb.do" target="hideframe">
		<table cellpadding="0" cellspacing="0" class="infotable">
			<tr>
				<td width="120px">
					<hk:data key="view.user.mgr.pwd.nowpwd"/>
				</td>
				<td>
					<hk:pwd name="oldPwd" clazz="text"/><br/>
					<div class="error" id="oldPwd_error"></div>
				</td>
			</tr>
			<tr>
				<td width="120px">
					<hk:data key="view.user.mgr.pwd.newpwd"/>
				</td>
				<td>
					<hk:pwd name="newPwd" clazz="text"/><br/>
					<div class="error" id="password_error"></div>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<div class="form_btn"><hk:submit value="保存" clazz="btn"/></div>
				</td>
			</tr>
		</table>
		</hk:form>
	</div>
<script type="text/javascript">
function subfrm(frmid){
	validateClear('oldPwd');
	validateClear('password');
	showSubmitDiv(frmid);
	return true;
}
function onpwderror(error,error_msg,op_func,obj_id_param){
	validateErr(obj_id_param,error_msg);
	hideSubmitDiv();
}
function onpwdsuccess(error,error_msg,op_func,obj_id_param,respValue){
	tourl("<%=path %>/user/set/set_tosetpwdweb.do");
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>