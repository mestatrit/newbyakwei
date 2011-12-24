<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="boxId" value="${boxId}"/>
	<hk:hide name="prizeId" value="${prizeId}"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${boxPrize.name}"/> 
			</td>
			<td>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="100px" align="right">
				物品提示语：
			</td>
			<td>
				最多50字<br/>
				<hk:textarea name="tip" value="${boxPrize.tip}" style="width:270px;height:100px;"/>
			</td>
			<td>
				<div class="infowarn" id="_tip"></div>
			</td>
		</tr>
		<tr>
			<td width="100px" align="right">
				数量：
			</td>
			<td>
				<hk:text name="pcount" clazz="text" value="${boxPrize.pcount}" style="width:100px"/> 
			</td>
			<td>
				<div class="infowarn" id="_pcount"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/box_view.do?companyId=${companyId}&boxId=${boxId}&navoid=${navoid }">返回</a>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.BOXPRIZE_NAME_LENGTH_TOO_LONG %>={objid:"_name"};
var err_code_<%=Err.BOXPRIZE_COUNT_ERROR %>={objid:"_pcount"};
var err_code_<%=Err.BOXPRIZE_OUT_OF_REMAIN_LIMIT %>={objid:"_pcount"};
var err_code_<%=Err.BOXPRIZE_COUNT_MORE_THAN_BOXREMAINCOUNT %>={objid:"_pcount"};
var err_code_<%=Err.BOXPRIZE_TIP_ERROR %>={objid:"_tip"};
var err_code_<%=Err.BOXPRIZE_TIP_LENGTH_TOO_LONG %>={objid:"_tip"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_pcount','');
	setHtml('_tip','');
	showGlass(frmid);
	return true;
}
</script>