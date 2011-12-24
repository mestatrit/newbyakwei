<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" enctype="multipart/form-data" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="navoid" value="${navoid}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="oid" value="${oid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="120px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpVideo.name}" maxlength="20"/> 
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				视频html代码<br/>（可选）：
			</td>
			<td>
				<hk:textarea name="html" value="${cmpVideo.html}" style="width:270px;height:100px"/>
				<div class="infowarn" id="_html"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				文件（可选）：
			</td>
			<td>
				<input type="file" name="f" size="50"/>
				<div class="b">
					文件大小不能超过1M<br/>
				</div>
				<div class="infowarn" id="_file"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				介绍（可选）：
			</td>
			<td>
				<hk:textarea name="intro" value="${cmpVideo.intro}" style="width:270px;height:100px"/>
				<div class="infowarn" id="_intro"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmpvideo.do?companyId=${companyId}&navoid=${navoid}">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPVIDEO_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPVIDEO_HTML_ERROR %>={objid:"_html"};
var err_code_<%=Err.CMPVIDEO_INTRO_ERROR %>={objid:"_intro"};
var err_code_<%=Err.UPLOAD_FILE_SIZE_LIMIT %>={objid:"_file"};
var err_code_<%=Err.UPLOAD_ERROR %>={objid:"_file"};
var err_code_<%=Err.CMPVIDEO_FILE_OR_HTML_EMPTY %>={objid:"_file"};
var err_code_<%=Err.CMPOTHERWEBINFO_NO_FILESIZE %>={objid:"_file"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_html','');
	setHtml('_intro','');
	setHtml('_file','');
	showGlass(frmid);
	return true;
}
</script>