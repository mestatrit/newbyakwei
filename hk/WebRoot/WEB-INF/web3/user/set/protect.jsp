<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.mgr.setprotect" /></c:set>
<c:set var="mgr_content" scope="request">
	<div>
		<hk:form oid="qfrm" method="post" onsubmit="return subfrm(this.id)" action="/user/set/set_setprotectweb.do" target="hideframe">
		<table cellpadding="0" cellspacing="0" class="infotable">
			<tr>
				<td width="120px">选择问题</td>
				<td>
					<hk:select name="pconfig" checkedvalue="${userProtect.pconfig}">
						<c:forEach var="p" items="${list}">
							<hk:option value="${p.id}" data="${p.name}" />
						</c:forEach>
					</hk:select><br/>
					<div class="error" id="q_error"></div>
				</td>
			</tr>
			<tr>
				<td width="120px">密码保护答案</td>
				<td>
					<hk:text name="pvalue" value="${userProtect.pvalue}" clazz="text" maxlength="50"/><br/>
					<div class="error" id="ans_error"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><div class="form_btn"><hk:submit value="保存" clazz="btn"/></div></td>
			</tr>
		</table>
		</hk:form>
	</div>
<script type="text/javascript">
function subfrm(frmid){
	validateClear('q');
	validateClear('ans');
	showSubmitDiv(frmid);
	return true;
}
function onproerror(error,error_msg,op_func,obj_id_param){
	validateErr(obj_id_param,error_msg);
	hideSubmitDiv();
}
function onprosuccess(error,error_msg,op_func,obj_id_param,respValue){
	tourl("<%=path %>/user/set/set_tosetprotectweb.do");
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>