<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.user.setting"/> - ${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter">
<div class="pl">
	<jsp:include page="set_inc.jsp"></jsp:include>
</div>
<div class="pr">
<div>
	<h2 class="bdtm"><hk:data key="epp.user.edit.pwd"/></h2>
	<div class="bdbtm"></div>
	<form id="infofrm" method="post" onsubmit="return subinfofrm(this.id)" action="<%=path %>/epp/web/op/user/set_setpwd.do" target="hideframe">
		<hk:hide name="companyId" value="${companyId}"/>
		<hk:hide name="ch" value="1"/>
		<table class="reg nt all" cellpadding="0" cellspacing="0">
			<tr>
				<td width="120px" align="right">
					<hk:data key="epp.user.mgr.pwd.nowpwd"/>
				</td>
				<td>
					<hk:pwd name="old_pwd" clazz="text"/>
				</td>
				<td>
					<div id="_old_pwd" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">
					<hk:data key="epp.user.mgr.pwd.newpwd"/>
				</td>
				<td width="250">
					<hk:pwd name="new_pwd" clazz="text"/>
				</td>
				<td>
					<div id="_new_pwd" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td width="120px" align="right">
					<hk:data key="epp.user.mgr.pwd.renewpwd"/>
				</td>
				<td>
					<hk:pwd name="renew_pwd" clazz="text"/>
				</td>
				<td>
					<div id="_renew_pwd" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td><hk:submit value="epp.submit" res="true" clazz="btn"/>
				</td>
				<td>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
<div class="clr"></div>
</div>
<script type="text/javascript">
var err_code_<%=Err.PASSWORD_OLD_ERROR %>={objid:"_old_pwd"};
var err_code_<%=Err.PASSWORD_DATA_ERROR %>={objid:"_new_pwd"};
var err_code_<%=Err.PASSWORD_CONFIRM_ERROR %>={objid:"_renew_pwd"};
function subinfofrm(frmid){
	showGlass();
	setHtml('_old_pwd','');
	setHtml('_new_pwd','');
	setHtml('_renew_pwd','');
	return true;
}
function updateok(){
	refreshurl();
}
function updateerror(error,error_msg,respValue){
	setHtml(getoidparam(error),error_msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>