<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpUserTableField"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" onsubmit="return subfrm(this.id)" method="post" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="dataId" value="${dataId}"/>
	<hk:hide name="fieldId" value="${fieldId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="navoid" value="${navoid}"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px;" align="right">字段名称：</td>
			<td>
				<hk:text name="name" clazz="text" maxlength="20" value="${cmpUserTableField.name}"/>
				<div class="infowarn" id="_info"></div>
			</td>
		</tr>
		<tr>
			<td width="90px;" align="right">字段类型：</td>
			<td>
				<hk:select name="field_type" checkedvalue="${cmpUserTableField.field_type}">
					<hk:option value="<%=CmpUserTableField.FIELD_TYPE_TEXT %>" data="单行文本输入"/>
					<hk:option value="<%=CmpUserTableField.FIELD_TYPE_TEXTAREA %>" data="多行文本输入"/>
					<hk:option value="<%=CmpUserTableField.FIELD_TYPE_SELECT %>" data="下拉选择框"/>
					<hk:option value="<%=CmpUserTableField.FIELD_TYPE_RADIO %>" data="单选按钮"/>
					<hk:option value="<%=CmpUserTableField.FIELD_TYPE_CHECKBOX %>" data="多选按钮"/>
				</hk:select>
			</td>
		</tr>
		<tr>
			<td width="90px;" align="right"></td>
			<td width="90px;"><hk:submit value="提交" clazz="btn split-r"/>
			<a href="<%=path %>/epp/web/op/webadmin/cmpusertable.do?companyId=${companyId}&navoid=${navoid}">取消</a>
			</td>
		</tr>
	</table>
</form>
<div>
	<c:if test="${fieldId>0 && cmpUserTableField.hasOption}">
		添加选项：<br/>
		<c:forEach var="option" items="${optionlist}">
			<ul class="datalist">
				<li>
					<div class="f_l" style="width: 200px;margin-right: 20px">${option.data }</div>
					<div class="f_l" style="width: 200px;">
						<a id="op_update_option_${option.optionId }" href="javascript:updateoption(${option.optionId })" class="split-r">修改</a>
						<a href="javascript:deloption(${option.optionId })">删除</a>
					</div>
					<div class="clr"></div>
				</li>
			</ul>
		</c:forEach>
		<div>
		<form id="optionfrm" method="post" onsubmit="return suboptionfrm(this.id)" action="<%=path %>/epp/web/op/webadmin/cmpusertable_createusertabledataoption.do" target="hideframe">
			<hk:hide name="ch" value="1"/>
			<hk:hide name="fieldId" value="${fieldId}"/>
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:text name="data" clazz="text"/><hk:submit clazz="btn" value="添加"/>
			<div class="infowarn" id="_optioninfo"></div>
		</form>
		</div>
	</c:if>
</div>
<script type="text/javascript">
var glassid=null;
var submited=false;
var glassid2=null;
var submited2=false;
function subfrm(frmid){
	if(submited){
		return false;
	}
	submited=true;
	glassid=addGlass(frmid,false);
	setHtml('_info','');
	return true;
}
function suboptionfrm(frmid){
	if(submited){
		return false;
	}
	submited2=true;
	glassid2=addGlass(frmid,false);
	setHtml('_optioninfo','');
	return true;
}

function createoptionerr(err,err_msg,v){
	setHtml("_optioninfo",err_msg);
	submited2=false;
	removeGlass(glassid2);
}
function createoptionok(err,err_msg,v){
	refreshurl();
}

function subupdateoptionfrm(frmid){
	if(submited){
		return false;
	}
	submited2=true;
	glassid2=addGlass(frmid,false);
	setHtml('_updateoptioninfo','');
	return true;
}
function updateoptionerr(err,err_msg,v){
	setHtml("_updateoptioninfo",err_msg);
	submited2=false;
	removeGlass(glassid2);
}
function updateoptionok(err,err_msg,v){
	refreshurl();
}
function updateoption(optionId){
	var glassid_option=addGlass('op_update_option_'+optionId,true);
	var title="修改";
	$.ajax({
		type:"POST",
		url:"<%=path %>/epp/web/op/webadmin/cmpusertable_updateusertabledataoption.do?companyId=${companyId}&optionId="+optionId,
		cache:false,
    	dataType:"html",
		success:function(data){
			removeGlass(glassid_option);
			createSimpleCenterWindow('optoinwin',450, 200, title, data,"hideWindow('optoinwin')");
		}
	});
}
function deloption(optionId){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/cmpusertable_deleteusertabledataoption.do?companyId=${companyId}&optionId="+optionId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>