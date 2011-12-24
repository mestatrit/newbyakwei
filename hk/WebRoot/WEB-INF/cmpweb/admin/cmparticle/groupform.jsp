<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.bean.CmpArticle"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="articlefrm" method="post" onsubmit="return subarticlefrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="navoid" value="${navoid}"/>
	<hk:hide name="groupId" value="${groupId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90" align="right">名称：</td>
			<td>
				<hk:text name="name" maxlength="50" clazz="text" value="${cmpArticleGroup.name}"/> 
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<div class="infowarn" id="_frm"></div>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmparticle_grouplist.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPARTICLEGROUP_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPARTICLEGROUP_NAME_DUPLICATE %>={objid:"_name"};
function subarticlefrm(frmid){
	setHtml('_name','');
	showGlass(frmid);
	return true;
}
</script>