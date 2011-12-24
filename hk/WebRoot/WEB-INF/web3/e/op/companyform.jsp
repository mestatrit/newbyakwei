<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.hk.bean.CompanyKindUtil"%>
<%@page import="com.hk.bean.CompanyKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
List<CompanyKind> kindlist=CompanyKindUtil.getCompanKindList();
request.setAttribute("kindlist",kindlist);%>
<hk:value_in_attr value_in_attr="${value_in_attr}" name="${value_in_attr_name}">
<hk:form oid="company_frm" onsubmit="return subcompanyfrm(this.id)" action="${company_form_action}" target="hideframe">
<hk:hide name="companyId" value="${companyId}"/>
<table class="infotable" cellpadding="0" cellspacing="0">
	<tr>
		<td width="90px">名称</td>
		<td>
			<div class="f_l">
				<hk:text name="name" value="${o.name}" maxlength="50" clazz="text"/><br/>
				<div id="company_name_error" class="error"></div>
			</div>
			<div id="company_name_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<tr>
		<td>电话</td>
		<td>
			<div class="f_l">
				<hk:text name="tel" value="${o.tel}" maxlength="50" clazz="text"/><br/>
				<div id="company_tel_error" class="error"></div>
			</div>
			<div id="company_tel_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<tr>
		<td width="90px">选择地区</td>
		<td>
			<div class="f_l" style="width: 300px;">
				<jsp:include page="../../../web4/inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">
				initselected(${pcityId});
				</script>
				<br/>
				<div id="zone_error" class="error"></div>
			</div>
			<div id="zone_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<c:if test="${hide_kind==null || hide_kind==false}">
	<tr>
		<td>分类</td>
		<td>
			<div class="f_l">
				<hk:select oid="id_kindid" name="kindId" checkedvalue="${o.kindId}">
					<hk:option value="0" data="请选择"/>
				<c:forEach var="k" items="${kindlist}">
					<hk:option value="${k.kindId}" data="${k.name}"/>
				</c:forEach>
				</hk:select> 
				<br/>
				<div id="company_kindId_error" class="error"></div>
			</div>
			<div id="company_kindId_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	</c:if>
	<c:if test="${o.uid>0}">
	<tr>
		<td>联盟分类</td>
		<td>
			<div class="f_l">
				<hk:select name="unionKindId" checkedvalue="${o.unionKindId}">
					<hk:option value="0" data="请选择"/>
				<c:forEach var="k" items="${cmpunionkindlist}">
					<hk:option value="${k.kindId}" data="${k.name}"/>
				</c:forEach>
				</hk:select> 
				<div id="company_unionKindId_error" class="error"></div>
			</div>
		</td>
	</tr>
	</c:if>
	<tr>
		<td>地址</td>
		<td>
			<div class="f_l">
				<hk:textarea name="addr" value="${o.addr}" clazz="text_area"/><br/>
				<div id="company_addr_error" class="error"></div>
			</div>
			<div id="company_addr_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<tr>
		<td>交通</td>
		<td>
			<div class="f_l">
				<hk:textarea name="traffic" value="${o.traffic}" clazz="text_area"/><br/>
				<div id="company_traffic_error" class="error"></div>
			</div>
			<div id="company_traffic_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<tr>
		<td>介绍</td>
		<td>
			<div class="f_l">
				<hk:textarea name="intro" value="${o.intro}" clazz="text_area"/><br/>
				<div id="company_intro_error" class="error"></div>
			</div>
			<div id="company_intro_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<c:if test="${companyKind.priceTip>0}">
	<tr>
		<td><hk:data key="companykind.prizetip${o.kindId}"/></td>
		<td>
			<div class="f_l">
				<hk:text name="price" value="${o.price}" clazz="text_short_1"/>元<br/>
				<div id="company_price_error" class="error"></div>
			</div>
			<div id="company_price_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	</c:if>
	<tr>
		<td>折扣</td>
		<td>
			<div class="f_l">
				<c:if test="${o.rebate==0}"><hk:text name="rebate" clazz="text_short_1"/>折</c:if>
				<c:if test="${o.rebate!=0}"><hk:text name="rebate" value="${o.rebate}" clazz="text_short_1"/>折</c:if><br/>
				<div id="company_rebate_error" class="error"></div>
			</div>
			<div id="company_rebate_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<div>
				<hk:submit value="提交" clazz="btn"/> 
				<c:if test="${show_companyform_return}">
					<hk:a href="${companyform_return_url}">返回</hk:a>
				</c:if>
			</div>
		</td>
	</tr>
</table>
</hk:form>
<script type="text/javascript">
function subcompanyfrm(frmid){
	clearValidate();
	if(getObj('id_kindid').value==0){
		validateErr('company_kindId','请选择分类');
		return false;
	}
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!=0){
		validateErr(obj_id_param,error_msg);
		hideSubmitDiv();
	}
	else{
		if(op_func=="edit_company"){
			tourl(current_url);
		}
		else{
			
		}
	}
}
function afterSuccess(value,op_func){
	tourl('<%=path %>/cmp.do?companyId='+value);
}
function returnweb(){
	tourl("<%=path %>/e/op/cmporder_myorderlist.do");
}

function clearValidate(){
	validateClear("company_name");
	validateClear("company_tel");
	validateClear("zone");
	validateClear("company_kindId");
	validateClear("company_addr");
	validateClear("company_traffic");
	validateClear("company_intro");
	<c:if test="${companyKind.priceTip>0}">
	validateClear("company_price");
	</c:if>
	validateClear("company_rebate");
}
var cmpchildkind=new Array();
<c:forEach var="cmpchild" items="${cmpchildkindlist}" varStatus="idx">
cmpchildkind[${idx.index }]=new Array(${cmpchild.oid},${cmpchild.kindId},'${cmpchild.name }');
</c:forEach>
function initcmpchildkind(kindId,selId){
	var o=getObj("childkind");
	o.options.length=0;
	o.options[0]=new Option('请选择',0);
	for(var i=0;i<cmpchildkind.length;i++){
		if(parseInt(kindId)==cmpchildkind[i][1]){
			o.options[i+1]=new Option(cmpchildkind[i][2],cmpchildkind[i][0]);
			if(selId==cmpchildkind[i][0]){
				o.options[i+1].selected=true;
			}
		}
	}
}
//initcmpchildkind(${o.kindId},${childKindId});
</script>
</hk:value_in_attr>