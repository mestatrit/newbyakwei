<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="iwant.web.admin.util.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<form id="frm" method="post" onsubmit="subfrm(this.id)" target="hideframe" action="${form_action }">
<hk:hide name="ch" value="1"/>
<hk:hide name="catid" value="${catid }"/>
<table class="formt" cellpadding="0" cellspacing="0">
	<tr>
		<td width="90px" align="right">名称</td>
		<td>
			<input maxlength="20" name="name" value="<hk:value value="${webSiteCat.name }" onerow="true"/>" class="text"/>
			<div class="infowarn" id="_name"></div>
		</td>
	</tr>
	<tr>
		<td width="90px" align="right"></td>
		<td>
			<input type="submit" value="提交" class="btn split-r"/>
			<a href="${appctx_path }/mgr/cat.do">返回</a>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CATEGORY_DUPLICATE %>={objid:"_name"};
var err_code_<%=Err.CATEGORY_NAME_ERR%>={objid:"_name"};
var glassid=null;
var submited=false;
function subfrm(frmid){
	if(submited){
		return false;
	}
	glassid=addGlass(frmid,false);
	submited=true;
	setHtml('_name','');
	return true;
}

function createerr(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	removeGlass(glassid);
	submited=false;
}

function updateerr(err,err_msg,v){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	removeGlass(glassid);
	submited=false;
}

function createok(err,err_msg,v){
	tourl('${appctx_path}/mgr/cat.do');
}

function updateok(err,err_msg,v){
	tourl('${appctx_path}/mgr/cat.do');
}
</script>