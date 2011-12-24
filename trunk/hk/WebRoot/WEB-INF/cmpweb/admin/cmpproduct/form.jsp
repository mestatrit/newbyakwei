<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<%EppViewUtil.loadCmpProductSortEachNlevel(request); %>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="productId" value="${productId}"/>
	<hk:hide name="sortId" value="${cmpProduct.sortId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="120" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpProduct.name}"/> 
			</td>
			<td>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				分类：
			</td>
			<td>
				<span class="split-r" id="l_1"></span>
				<span class="split-r" id="l_2"></span>
				<span class="split-r" id="l_3"></span>
			</td>
			<td>
				<div class="infowarn" id="_sortId"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				价格（可选）：
			</td>
			<td>
				<hk:text name="money" clazz="text" value="${cmpProduct.money}"/> 元
			</td>
			<td>
				<div class="infowarn" id="_money"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				折扣（可选）：
			</td>
			<td>
				<hk:text name="rebate" clazz="text" value="${cmpProduct.rebate}"/> 折
			</td>
			<td>
				<div class="infowarn" id="_rebate"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				介绍（可选）：
			</td>
			<td>
				<hk:textarea name="intro" value="${cmpProduct.intro}" style="width:270px;height:100px;"/> 
			</td>
			<td>
				<div class="infowarn" id="_intro"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmpproduct.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var l_1_list=new Array();
var l_2_list=new Array();
var l_3_list=new Array();
<c:forEach var="sort" items="${l_1_list}" varStatus="idx">
l_1_list[${idx.index }]=new Array(${sort.sortId },${sort.parentId },'${sort.name }'); 
</c:forEach>
<c:forEach var="sort" items="${l_2_list}" varStatus="idx">
l_2_list[${idx.index }]=new Array(${sort.sortId },${sort.parentId },'${sort.name }'); 
</c:forEach>
<c:forEach var="sort" items="${l_3_list}" varStatus="idx">
l_3_list[${idx.index }]=new Array(${sort.sortId },${sort.parentId },'${sort.name }'); 
</c:forEach>
var err_code_<%=Err.CMPPRODUCT_NAME_DUPLICATE %>={objid:"_name"};
var err_code_<%=Err.CMPPRODUCT_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPPRODUCT_MONEY_ERROR %>={objid:"_money"};
var err_code_<%=Err.CMPPRODUCT_REBATE_ERROR %>={objid:"_rebate"};
var err_code_<%=Err.CMPPRODUCT_INTRO_LENGTH_TOO_LONG %>={objid:"_intro"};
var err_code_<%=Err.CMPPRODUCT_SORTID_ERROR %>={objid:"_sortId"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_money','');
	setHtml('_rebate','');
	setHtml('_intro','');
	setHtml('_sortId','');
	showGlass(frmid);
	var tmp_sortId=0;
	var el=document.getElementsByName('sortId_de');
	for(var i=0;i<el.length;i++){
		tmp_sortId=el[i].value;
	}
	getObj(frmid).sortId.value=tmp_sortId;
	return true;
}
function showNlevel1(sortId){
	var s='<select name="sortId_de" onchange="showNlevel2(this.value)"><option value="0">请选择</option>';
	for(var i=0;i<l_1_list.length;i++){
		if(sortId==l_1_list[i][0]){
			s+='<option value="'+l_1_list[i][0]+'" selected="selected">'+l_1_list[i][2]+'</option>';
		}
		else{
			s+='<option value="'+l_1_list[i][0]+'">'+l_1_list[i][2]+'</option>';
		}
	}
	s+='</select>';
	setHtml('l_1',s);
}
function showNlevel2(parentId,sortId){
	var hasdata=false;
	var s='<select name="sortId_de" onchange="showNlevel3(this.value)"><option value="0">请选择</option>';
	for(var i=0;i<l_2_list.length;i++){
		if(parentId==parseInt(l_2_list[i][1])){
			hasdata=true;
			if(sortId==l_2_list[i][0]){
				s+='<option value="'+l_2_list[i][0]+'" selected="selected">'+l_2_list[i][2]+'</option>';
			}
			else{
				s+='<option value="'+l_2_list[i][0]+'">'+l_2_list[i][2]+'</option>';
			}
		}
	}
	s+='</select>';
	if(hasdata){
		setHtml('l_2',s);
	}
	else{
		setHtml('l_2','');
	}
	setHtml('l_3','');
}
function showNlevel3(parentId,sortId){
	var hasdata=false;
	var s='<select name="sortId_de"><option value="0">请选择</option>';
	for(var i=0;i<l_3_list.length;i++){
		if(parentId==l_3_list[i][1]){
			hasdata=true;
			if(sortId==l_3_list[i][0]){
				s+='<option value="'+l_3_list[i][0]+'" selected="selected">'+l_3_list[i][2]+'</option>';
			}
			else{
				s+='<option value="'+l_3_list[i][0]+'">'+l_3_list[i][2]+'</option>';
			}
		}
	}
	s+='</select>';
	if(hasdata){
		setHtml('l_3',s);
	}
	else{
		setHtml('l_3','');
	}
}
function initSortId(sortId){
	for(var i=0;i<l_1_list.length;i++){
		if(sortId==l_1_list[i][0]){
			showNlevel1(sortId);
			return;
		}
	}
	for(var i=0;i<l_2_list.length;i++){
		if(sortId==l_2_list[i][0]){
			var parentId=l_2_list[i][1];
			showNlevel1(parentId);
			showNlevel2(parentId,sortId);
			return;
		}
	}
	for(var i=0;i<l_3_list.length;i++){
		if(sortId==l_3_list[i][0]){
			var parentId=l_3_list[i][1];
			for(var k=0;i<l_2_list.length;k++){
				if(parentId==l_2_list[k][0]){
					var rootId=l_2_list[k][1];
					showNlevel1(rootId);
					showNlevel2(rootId,parentId);
					showNlevel3(parentId,sortId);
					return;
				}
			}
		}
	}
	
}
var sortId=0;
<c:if test="${cmpProduct!=null}">
sortId=${cmpProduct.sortId};
</c:if>
if(sortId>0){
	initSortId(sortId)
}
else{
	showNlevel1(0);
}
</script>