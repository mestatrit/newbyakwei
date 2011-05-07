<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="${appctx_path }/static/css/a.css" />
		<script type="text/javascript" language="javascript" src="${appctx_path }/static/js/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" language="javascript" src="${appctx_path }/static/js/pub.js"></script>
		<script type="text/javascript">
var path = "${appctx_path }";
var loading_path = "${appctx_path }/static/img/blue-loading.gif";</script>
<link rel="stylesheet" type="text/css" href="${appctx_path }/static/css/imgcut/imgareaselect-default.css" />
<script type="text/javascript" src="${appctx_path }/static/js/imgcut/jquery.imgareaselect.min.js"></script>
<style type="text/css">
.preview{
	position: relative;
	overflow: hidden;
	width: 200px;
	height: 200px;
}
</style>
<script type="text/javascript">
function preview(img, selection) {
    var scaleX = 200 / (selection.width || 1);
    var scaleY = 200 / (selection.height || 1);
    var img=new Image();
	img.src=getObj('pp').src;
    $('#pp2').css({
        width: Math.round(scaleX * img.width) + 'px',
        height: Math.round(scaleY * img.height) + 'px',
        marginLeft: '-' + Math.round(scaleX * selection.x1) + 'px',
        marginTop: '-' + Math.round(scaleY * selection.y1) + 'px'
    });
}
var submited=false;
var glassid=null;
function subpicfrm(frmid){
	if(submited){
		return false;
	}
	glassid=addGlass(frmid,false);
	submited=true;
	setHtml('err_content','');
	return true;
}
function updateok(err,err_msg,v){
	tourl('${appctx_path}/mgr/ppt_view.do?pptid=${slide.pptid}');
}

function updateerr(err,err_msg,v){
	setHtml('err_content',err_msg);
	submited=false;
	removeGlass(glassid);
}
$(document).ready(function(){
	$('#pp').imgAreaSelect({
        onSelectEnd: function(img, selection){
            $('input[name=x0]').val(selection.x1);
            $('input[name=y0]').val(selection.y1);
            $('input[name=x1]').val(selection.x2);
            $('input[name=y1]').val(selection.y2);
        },
        x1: 0,
        y1: 0,
        x2: 200,
        y2: 200,
        minWidth: 200,
        minHeight: 200,
        aspectRatio: '1:1',
        onSelectChange: preview
    });
});
</script>
		<title><hk:value value="${slide.title}"/> - 修改小方图 - 后台管理</title>
	</head>
	<body style="background-color: white;">
	<iframe id="hideframe" name="hideframe" class="hide"></iframe>
<div class="mod" style="margin-top: 20px;margin-left: 20px;">
	<div class="mod_title">选择小方图区域</div>
	<div class="mod_content">
		<table cellpadding="0" cellspacing="0" border="0">
		<tr>
		<td width="980px"><img id="pp" src="${slide.pic2Url}"/></td>
		<td width="200px">
			<div class="preview">
				<img id="pp2" src="${slide.pic2Url }" style="position: relative;" />
			</div>
			<div style="margin-top: 10px">
				<div class="infowarn" id="err_content"></div>
				<form id="frm" onsubmit="return subpicfrm(this.id)" action="${appctx_path }/mgr/slide_setpic1.do" target="hideframe">
					<input type="hidden" name="slideid" value="${slideid }" />
					<input type="hidden" name="ch" value="1"/>
					<input type="hidden" name="x0" />
					<input type="hidden" name="x1" />
					<input type="hidden" name="y0" />
					<input type="hidden" name="y1" />
					<input type="submit" class="btn split-r" value="确定"/>
					<a href="${appctx_path}/mgr/ppt_view.do?pptid=${pptid}">返回</a>
				</form>
			</div>
		</td>
		</tr>
		</table>
	</div>
</div>
	</body>
</html>