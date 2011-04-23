<%@ page language="java" pageEncoding="UTF-8"
%><%@page import="iwant.web.admin.util.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<form id="frm" method="post" enctype="multipart/form-data" onsubmit="subfrm(this.id)" target="hideframe" action="${form_action }">
<hk:hide name="ch" value="1"/>
<hk:hide name="pptid" value="${pptid }"/>
<hk:hide name="slideid" value="${slideid }"/>
<table class="formt" cellpadding="0" cellspacing="0">
	<tr>
		<td width="90" align="right">标题</td>
		<td>
			<input maxlength="20" name="title" value="<hk:value value="${slide.title }"/>" class="text"/>
			<div class="ruo"><hk:data key="10"/></div>
			<div class="infowarn" id="err_title"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">副标题</td>
		<td>
			<input maxlength="30" name="subtitle" value="<hk:value value="${slide.subtitle }" onerow="true"/>" class="text"/>
			<div class="ruo"><hk:data key="11"/></div>
			<div class="infowarn" id="err_subtitle"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">描述</td>
		<td>
			<textarea name="descr" style="width: 270px;height: 80px;"><hk:value value="${slide.descr}" textarea="true"/></textarea>
			<div class="ruo"><hk:data key="12"/></div>
			<div class="infowarn" id="err_descr"></div>
		</td>
	</tr>
	<tr>
		<td width="90" align="right">图片文件</td>
		<td>
			<input id="file_" type="file" class="text" size="20" name="f"/>
			<div>图片文件像素建议为960px*640px，建议图片格式jpg,png，图片文件大小不要超过3M</div>
			<div class="infowarn" id="err_f"></div>
		</td>
	</tr>
	<c:if test="${slide!=null}">
		<tr>
			<td width="90" align="right">&nbsp;</td>
			<td>
				<img src="${slide.pic1Url}"/>
			</td>
		</tr>
	</c:if>
	<tr>
		<td width="90" align="right"></td>
		<td>
			<input type="submit" value="提交" class="btn split-r"/>
			<a href="${appctx_path }/mgr/slide_back.do?pptid=${pptid}">返回</a>
		</td>
	</tr>
</table>
</form>

<script type="text/javascript">
var err_code_<%=Err.SLIDE_TITLE_ERR %>={objid:"err_title"};
var err_code_<%=Err.SLIDE_SUBTITLE_ERR%>={objid:"err_subtitle"};
var err_code_<%=Err.SLIDE_DESCR_ERR%>={objid:"err_descr"};
var err_code_<%=Err.SLIDE_IMG_FORMAT_ERR%>={objid:"err_f"};
var err_code_<%=Err.SLIDE_IMG_SIZE_ERR%>={objid:"err_f"};
var err_code_<%=Err.PROCESS_IMAGEFILE_ERR%>={objid:"err_f"};
var from='<hk:value value="${from}"/>';
var glassid=null;
var submited=false;
function subfrm(frmid){
	if(submited){
		return false;
	}
	glassid=addGlass(frmid,false);
	submited=true;
	setHtml('err_title','');
	setHtml('err_subtitle','');
	setHtml('err_descr','');
	setHtml('err_f','');
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

function createerr2(err,err_msg,v){
	removeGlass(glassid);
	submited=false;
	alert(err_msg);
}

function updateerr(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	removeGlass(glassid);
	submited=false;
}

function createok(err,err_msg,v){
	if(getObj('file_').value.length>0){
		tourl('${appctx_path}/mgr/slide_setpic1.do?pptid=${pptid}&slideid='+v);
		return;
	}
	if(from=='mainppt'){
		tourl('${appctx_path}/mgr/ppt_viewmain.do?pptid=${pptid}');
		return;
	}
	tourl('${appctx_path}/mgr/ppt_view.do?pptid=${pptid}');
}

function updateok(err,err_msg,v){
	if(getObj('file_').value.length>0){
		tourl('${appctx_path}/mgr/slide_setpic1.do?pptid=${pptid}&slideid=${slideid}');
		return;
	}
	if(from=='mainppt'){
		tourl('${appctx_path}/mgr/ppt_viewmain.do?pptid=${pptid}');
		return;
	}
	tourl('${appctx_path}/mgr/ppt_view.do?pptid=${slide.pptid}');
}
</script>