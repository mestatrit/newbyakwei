<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.product.mgr.mgr"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
<a class="text_14" href="javascript:toadd()">添加产品</a>
<table width="500px" class="infotable" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<hk:form method="get" action="/e/op/product/op_productlistweb.do">
				<hk:hide name="companyId" value="${companyId}"/>
				名称:<hk:text name="s_name" value="${name}" clazz="text_short_1"/>
				分类:
				<hk:select name="s_sortId" checkedvalue="${s_sortId}">
					<hk:option value="0" data="全部"/>
					<c:forEach var="s" items="${sortlist}">
						<hk:option value="${s.sortId}" data="${s.name}"/>
					</c:forEach>
				</hk:select>
				<hk:submit value="查询" clazz="btn"/>
			</hk:form><br/>
			<div class="bdbtm"></div>
		</td>
	</tr>
</table>
<table class="infotable" cellpadding="0" cellspacing="0">
	<tr class="tr-title">
		<th width="100px">编号</th>
		<th width="200px">名称</th>
		<th width="80px">缩写</th>
		<th width="80px">价格</th>
		<th width="100px">分类</th>
		<th width="150px"></th>
	</tr>
	<c:forEach var="p" items="${list}">
	<tr class="tr-line" onmouseout="this.className='tr-line';" onmouseover="this.className='tr-line bg2';">
		<td>${p.pnum }</td>
		<td>${p.name }</td>
		<td>${p.shortName }</td>
		<td>￥${p.money }</td>
		<td>
			<a href="<%=path %>/e/op/product/op_productlistweb.do?companyId=${companyId }&s_sortId=${p.sortId }&s_name=${enc_name }">${p.cmpProductSort.name }</a>
			<c:if test="${p.cmpUnionKindId>0}"><br/>${p.cmpUnionKind.name}</c:if>
		</td>
		<td>
			<a id="edit_${p.productId }" href="javascript:toedit(${p.productId })">修改</a>
			/
			<a id="del_${p.productId }" href="javascript:todel(${p.productId })">删除</a>
			/
			<a id="del_${p.productId }" href="javascript:tomgrphoto(${p.productId })">图片管理</a>
			<c:if test="${uid>0}"><br/>
			<a href="javascript:toselcmpunionkind(${p.productId })">选择联盟分类</a>
			</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pagecon">
	<hk:page midcount="10" url="/e/op/product/op_productlistweb2.do?companyId=${companyId}&s_name=${enc_s_name }&s_sortId=${s_sortId }"/>
	<div class="clr"></div>
</div>
</div>
<script type="text/javascript">
function toselcmpunionkind(id){
	tourl("<%=path%>/e/op/product/op_toselcmpunionkind.do?companyId=${companyId}&productId="+id);
}
function tomgrphoto(productId){
	tourl('<%=path %>/e/op/cmpproductphoto.do?companyId=${companyId}&productId='+productId);
}
var sort=new Array();
<c:forEach var="s" items="${sortlist}" varStatus="idx">
sort[${idx.index }]=new Array(${s.sortId },'${s.name }');
</c:forEach>
function todel(id){
	if(window.confirm("确实要删除？")){
		getObj("del_pid").value=id;
		showSubmitDivForObj('del_'+id);
		getObj("del_frm").submit();
	}
}
function toadd(id){
	var html='<form id="frm" onsubmit="return subaddfrm(this.id)" action="<%=path %>/e/op/product/op_addproductweb.do" target="hideframe"> <input type="hidden" name="companyId" value="${companyId }" /> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="80px">名称</td> <td> <div class="f_l"> <input name="name" type="text" class="text" /><br/> <div id="cmpproduct_name_error" class="error"></div> </div> <div id="cmpproduct_name_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px"><label>编号</label></td> <td> <div class="f_l"> <input name="pnum" type="text" class="text" value="${o.pnum }"/><br/> <div id="cmpproduct_pnum_error" class="error"></div> </div> <div id="cmpproduct_pnum_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px"><label>缩写</label></td> <td> <div class="f_l"> <input name="shortName" type="text" class="text" value="${o.shortName }"/><br/> <div id="cmpproduct_shortName_error" class="error"></div> </div> <div id="cmpproduct_shortName_flag" class="flag"></div><div class="clr"></div> </td> </tr><tr> <td width="80px">分类</td> <td> <div class="f_l"> <select id="id_sortid" name="sortId"></select><br/> <div id="cmpproduct_sort_error" class="error"></div> </div> <div id="cmpproduct_sort_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">价格</td> <td> <div class="f_l"> <input name="money" type="text" class="text"/><br/> <div id="cmpproduct_money_error" class="error"></div> </div> <div id="cmpproduct_money_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">折扣</td> <td> <div class="f_l"> <input name="rebate" type="text" class="text"/><br/> <div id="cmpproduct_rebate_error" class="error"></div> </div> <div id="cmpproduct_rebate_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">介绍</td> <td> <div class="f_l"> <textarea name="intro" class="text_area"></textarea><br/> <div id="cmpproduct_intro_error" class="error"></div> </div> <div id="cmpproduct_intro_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td></td> <td> <div align="center"><input type="submit" value="保存" class="btn"></div> </td> </tr> </table> </form>';
	createBg();
	createCenterWindow("add_win",500,600,'添加产品',html,"hideWindow('add_win');clearBg();");
	init_sort_select(0);
}
function subaddfrm(frmid){
	clearValidate();
	showSubmitDiv(frmid);
	if(getObj('id_sortid').value==0){
		validateErr('cmpproduct_sort','请选择分类');
		hideSubmitDiv();
		return false;
	}
	return true;
}
function init_sort_select(selected_id){
	var o=getObj("id_sortid");
	o.options.length=0;
	o.options[0]=new Option("请选择",0);
	for(var i=0;i<sort.length;i++){
		o.options[i+1]=new Option(sort[i][1],sort[i][0]);
		if(sort[i][0]==parseInt(selected_id)){
			o.options[i+1].selected=true;
		}
	}
}
function toedit(id){
	showSubmitDivForObj('edit_'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/product/op_product.do',
		data:'pid='+id+"&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("edit_win",500,600,'修改产品',data,"hideWindow('edit_win');clearBg();");
			hideSubmitDiv();
		}
	})
}
function subeditfrm(frmid){
	clearValidate();
	showSubmitDiv(frmid);
	return true;
}
function clearValidate(){
	validateClear("cmpproduct_name");
	validateClear("cmpproduct_sort");
	validateClear("cmpproduct_money");
	validateClear("cmpproduct_rebate");
	validateClear("cmpproduct_intro");
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(op_func=="edit_product"){
		if(error==0){
			refreshurl();
			
		}
		else{
			validateErr(obj_id_param,error_msg);
			hideSubmitDiv();
		}
	}
	else if(op_func="add_product"){
		if(error==0){
			refreshurl();
		}
		else{
			validateErr(obj_id_param,error_msg);
			hideSubmitDiv();
		}
	}
	else{
		refreshurl();
	}
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>