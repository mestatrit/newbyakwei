<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.table.sort"/></c:set>
<c:set var="mgr_content" scope="request">
<script type="text/javascript">
function subaddsortfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onsorterror(error,error_msg,op_func,obj_id_param,respValue){
	validateErr('msg',error_msg);
	hideSubmitDiv();
}
function onsortsuccess(error,error_msg,op_func,obj_id_param,respValue){
	refreshurl();
}
function toedit(id){
	showSubmitDivForObj('update'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/table_loadsort.do?companyId=${companyId}&sortId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			createCenterWindow("sort_update_win",450,230,'修改分类',data,"hideWindow('sort_update_win')");
			hideSubmitDiv();
		}
	});
}
function subupdatesortfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onsortupdateerror(error,error_msg,op_func,obj_id_param,respValue){
	validateErr('msg_update',error_msg);
	hideSubmitDiv();
}
function onsortupdatesuccess(error,error_msg,op_func,obj_id_param,respValue){
	refreshurl();
}
function del(id){
	if(window.confirm("确定要删除?")){
		showSubmitDivForObj('del'+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/auth/table_delsort.do?companyId=${companyId}&sortId='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
<div>
	<h3>添加新分类</h3>
	<hk:form target="hideframe" oid="addsortfrm" onsubmit="return subaddsortfrm(this.id)" action="/e/op/auth/table_createsort.do">
		<hk:hide name="companyId" value="${companyId}"/>
		<table cellpadding="0" cellspacing="0" class="infotable">
			<tr>
				<td width="80px">名称</td>
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
	<c:forEach var="t" items="${list}">
		<tr onmouseout="this.className='';" onmouseover="this.className='bg2';">
			<td><span id="p_${t.sortId }">${t.name }</span></td>
			<td>
			<a id="update${t.sortId }" href="javascript:toedit(${t.sortId })">修改</a>
			/
			<a id="del${t.sortId }" href="javascript:del(${t.sortId })">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>