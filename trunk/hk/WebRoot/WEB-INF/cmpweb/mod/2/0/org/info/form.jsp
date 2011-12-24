<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="articlefrm" method="post" onsubmit="return subfrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="orgId" value="${orgId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="70">名称：</td>
			<td>
				<hk:text name="name" value="${cmpOrg.name}" clazz="text" maxlength="50"/>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="70"></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPORG_NAME_ERROR %>={objid:"_name"};
function subfrm(frmid){
	setHtml('_name','');
	showGlass(frmid);
	return true;
}
</script>