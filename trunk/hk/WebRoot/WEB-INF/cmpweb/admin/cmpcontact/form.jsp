<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="oid" value="${oid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="120px" align="right">
				联系qq号码：
			</td>
			<td>
				<hk:text name="qq" clazz="text" value="${cmpContact.qq}"/><br/>
				<div class="infowarn" id="_qq"></div>
			</td>
		</tr>
		<tr>
			<td width="120px" align="right">
				显示名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpContact.name}" maxlength="20"/><br/>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				在线状态代码：
			</td>
			<td>
				<hk:textarea name="qqhtml" style="width:400px;height:100px" value="${cmpContact.qqhtml}"/>
				<div class="infowarn" id="_qqhtml"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmpcontact.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPCONTACT_QQ_ERROR %>={objid:"_qq"};
var err_code_<%=Err.CMPCONTACT_QQHTML_ERROR %>={objid:"_qqhtml"};
var err_code_<%=Err.CMPCONTACT_NAME_ERROR %>={objid:"_name"};
function subfrm(frmid){
	setHtml('_qq','');
	showGlass(frmid);
	return true;
}
</script>