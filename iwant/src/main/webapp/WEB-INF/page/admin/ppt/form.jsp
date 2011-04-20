<%@ page language="java" pageEncoding="UTF-8"
%><%@page import="iwant.web.admin.util.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<form id="frm" method="post" onsubmit="subfrm(this.id)" target="hideframe" action="${form_action }">
<hk:hide name="ch" value="1"/>
<hk:hide name="pptid" value="${pptid }"/>
<hk:hide name="projectid" value="${project.projectid }"/>
<table class="formt" cellpadding="0" cellspacing="0">
	<tr>
		<td width="90" align="right">名称</td>
		<td>
			<input maxlength="20" name="name" value="<hk:value value="${ppt.name }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="9"/></div>
			<div class="infowarn" id="err_name"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right"></td>
		<td>
			<input type="submit" value="提交" class="btn split-r"/>
			<a href="${appctx_path }/mgr/ppt_back.do?projectid=${projectid}">返回</a>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.PPT_NAME_ERR %>={objid:"err_name"};

var glassid=null;
var submited=false;
function subfrm(frmid){
	if(submited){
		return false;
	}
	glassid=addGlass(frmid,false);
	submited=true;
	setHtml('err_name','');
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
	tourl('${appctx_path}/mgr/ppt_view.do?pptid='+v);
}

function updateok(err,err_msg,v){
	if(back_url.length==0){
		tourl('${appctx_path}/mgr/ppt_view.do?pptid=${ppt.pptid}');
		return;
	}
	tourl(decodeURIComponent(back_url)); 
}
var back_url="${backUrl.lastEncUrl}";
</script>