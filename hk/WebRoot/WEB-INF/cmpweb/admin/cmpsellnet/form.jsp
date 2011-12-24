<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="oid" value="${oid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpSellNet.name}"/> 
			</td>
			<td>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				分类：
			</td>
			<td>
				<%EppViewUtil.loadCmpSellNetKindList(request); %>
				<hk:select name="kindId" checkedvalue="${cmpSellNet.kindId}">
					<hk:option value="0" data="选择分类"/>
					<c:forEach var="kind" items="${kindlist}">
						<hk:option value="${kind.kindId}" data="${kind.name}"/>
					</c:forEach>
				</hk:select> 
			</td>
			<td>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				电话：
			</td>
			<td>
				<hk:text name="tel" clazz="text" value="${cmpSellNet.tel}"/>
			</td>
			<td><div class="infowarn" id="_tel"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				地址：
			</td>
			<td>
				<hk:textarea name="addr" value="${cmpSellNet.addr}" style="width:270px;height:100px;"/>
			</td>
			<td>
			<div class="infowarn" id="_addr"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmpsellnet.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPSELLNET_TEL_ERROR %>={objid:"_tel"};
var err_code_<%=Err.CMPSELLNET_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPSELLNET_ADDR_ERROR %>={objid:"_addr"};
function subfrm(frmid){
	setHtml('_tel','');
	setHtml('_name','');
	setHtml('_addr','');
	showGlass(frmid);
	return true;
}
</script>