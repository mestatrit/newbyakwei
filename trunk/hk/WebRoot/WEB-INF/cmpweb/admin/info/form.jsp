<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${company.name}"/> <br/>
			</td>
			<td><div class="infowarn" id="_name"></div></td>
		</tr>
		<tr>
			<td width="90px" align="right">
				电话：
			</td>
			<td>
				<hk:text name="tel" clazz="text" value="${company.tel}"/> <br/>
			</td>
			<td>
			<div class="infowarn" id="_tel"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				地区：
			</td>
			<td>
				<div style="margin-bottom: 10px">
				<jsp:include page="../inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">
				initselected(${company.pcityId});
				</script>
				</div>
			</td>
			<td><div class="infowarn" id="_pcityid"></div></td>
		</tr>
		<tr>
			<td width="90px" align="right">
				介绍：
			</td>
			<td>
				<hk:textarea name="intro" clazz="text" value="${company.intro}" style="width:270px;height:100px;"/><br/>
			</td>
			<td>
			<div class="infowarn" id="_intro"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				地址：
			</td>
			<td>
				<hk:textarea name="addr" clazz="text" value="${company.addr}" style="width:270px;height:100px;"/><br/>
			</td>
			<td>
				<div class="infowarn" id="_addr"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				交通：
			</td>
			<td>
				<hk:textarea name="traffic" clazz="text" value="${company.traffic}" style="width:270px;height:100px;"/><br/>
			</td>
			<td>
				<div class="infowarn" id="_traffic"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
			</td>
			<td></td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.COMPANY_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.COMPANY_ADDR_LEN_TOOLONG %>={objid:"_addr"};
var err_code_<%=Err.COMPANY_TRAFFIC_ERROR %>={objid:"_traffic"};
var err_code_<%=Err.COMPANY_TEL_LEN_TOOLONG %>={objid:"_tel"};
var err_code_<%=Err.COMPANY_INTRO_LEN_TOOLONG %>={objid:"_intro"};
function subfrm(frmid){
	if(getObj('id_pcityId').value==0){
		setHtml('_pcityid','请选择地区');
		return false;
	}
	setHtml('_pcityid','');
	setHtml('_name','');
	setHtml('_addr','');
	setHtml('_traffic','');
	setHtml('_tel','');
	setHtml('_intro','');
	showGlass(frmid);
	return true;
}
</script>