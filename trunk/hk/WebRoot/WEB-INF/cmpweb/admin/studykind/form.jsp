<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="kindfrm" method="post" onsubmit="return subkindfrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="parentId" value="${parentId}"/>
	<hk:hide name="kindId" value="${kindId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="130px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpStudyKind.name}"/> 
			</td>
			<td>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPSTUDYKIND_NAME_ERROR %>={objid:"_name"};
function subkindfrm(frmid){
	setHtml('_name','');
	showGlass(frmid);
	return true;
}
</script>