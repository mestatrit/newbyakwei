<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.productsort.mgr.mgr"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
	<h3>添加新分类</h3>
	<hk:form target="hideframe" oid="addsortfrm" onsubmit="return subaddsortfrm(this.id)" action="/e/op/product/op_addsortweb.do">
		<hk:hide name="companyId" value="${companyId}"/>
		<table cellpadding="0" cellspacing="0" class="infotable">
			<tr>
				<td width="80px"><label>名称</label></td>
				<td>
					<div class="f_l">
						<hk:text name="name" clazz="text"/><br/>
						<div id="msg_error" class="error"></div>
					</div>
					<div id="msg_flag" class="flag"></div><div class="clr"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="center">
				<hk:submit value="提交" clazz="btn"/>
				</td>
			</tr>
		</table>
	</hk:form>
</div>
<div class="bdbtm"></div>
<div>
<br/>
	<table class="infotable2" cellpadding="0" cellspacing="0">
		<tr>
			<th width="200px">名称</th>
			<th width="200px"></th>
		</tr>
	<c:forEach var="p" items="${list}">
		<tr onmouseout="this.className='';" onmouseover="this.className='bg2';">
			<td><span id="p_${p.sortId }">${p.name }</span></td>
			<td>
			<a id="edit_${p.sortId }" href="javascript:toedit(${p.sortId })">修改</a>
			/
			<a id="del_${p.sortId }" href="javascript:todel(${p.sortId })">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<hk:form oid="del_frm" clazz="hide" action="/e/op/product/op_delsortweb.do" target="hideframe">
<hk:hide name="companyId" value="${companyId}"/><input id="del_sortid" name="sortId"/>
</hk:form>
<script type="text/javascript">
var current_sortid=0;
function todel(id){
	if(window.confirm("确实要删除？")){
		getObj("del_sortid").value=id;
		showSubmitDivForObj('del_'+id);
		getObj("del_frm").submit();
	}
}
function toedit(id){
	var name=getHtml('p_'+id);
	var sortId=id;
	current_sortid=id;
	var html='<form id="edit_sort" target="hideframe" onsubmit="return subeditsortfrm(this.id)" action="<%=path %>/e/op/product/op_editsortweb.do"> <input type="hidden" name="sortId" value="'+id+'" /> <input type="hidden" name="companyId" value="${companyId }" /> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="80px"><label>名称</label></td> <td> <div class="f_l"> <input id="id_name" name="name" type="text" class="text_short_2" value="'+name+'"/><br/> <div id="msg_error" class="error"></div> </div> <div id="msg_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td></td> <td> <div><input type="submit" value="保存" class="btn"></div> </td> </tr> </table> </form>';
	createCenterWindow("edit_win",450,230,'修改分类',html,"hideWindow('edit_win')");
}
function subeditsortfrm(frmid){
	clearValidate();
	showSubmitDiv(frmid);
	return true;
}
function subaddsortfrm(frmid){
	clearValidate();
	showSubmitDiv(frmid);
	return true;
}
function clearValidate(){
	validateClear("msg");
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(op_func=="edit_sort"){
		if(error==0){
			setHtml('p_'+current_sortid,getObj('id_name').value);
			setHtml('edit_win_content','<strong class="text_16 green">信息修改成功</strong>');
			hideSubmitDiv();
			delay("hideWindow('edit_win')",2000);
		}
		else{
			validateErr("msg",error_msg);
			hideSubmitDiv();
		}
	}
	else if(op_func="add_sort"){//添加新分类
		if(error==0){
			refreshurl();
		}
		else{
			validateErr("msg",error_msg);
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