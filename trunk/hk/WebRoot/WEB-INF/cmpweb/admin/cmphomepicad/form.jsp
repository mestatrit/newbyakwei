<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" enctype="multipart/form-data" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="adid" value="${adid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="150px" align="right">
				标题：
			</td>
			<td>
				<hk:text name="title" clazz="text" value="${cmpHomePicAd.title}"/> 
				<div class="infowarn" id="_title"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				网址：
			</td>
			<td>
				<hk:text name="url" clazz="text" value="${cmpHomePicAd.url}"/> 
				<div class="infowarn" id="_url"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				广告图片：
			</td>
			<td>
				<input type="file" name="f" size="50"/>
				<div class="b">
					图片合适宽701px，合适高338px<br/>
					图片文件大小不能超过100K<br/>
					图片文件格式只支持jpg，gif，png
				</div>
				<div class="infowarn" id="_file"></div>
			</td>
		</tr>
		<c:if test="${cmpHomePicAd!=null}">
		<tr>
		<td colspan="2">
		<div><img src="${cmpHomePicAd.picUrl }"/></div>
		</td>
		</tr>
		</c:if>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmphomepicad.do?companyId=${companyId}">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPHOMEPICAD_TITLE_ERROR %>={objid:"_title"};
var err_code_<%=Err.CMPHOMEPICAD_URL_ERROR%>={objid:"_url"};
var err_code_<%=Err.MUST_PIC_UPLOAD %>={objid:"_file"};
var err_code_<%=Err.IMG_UPLOAD_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_OUTOFSIZE_ERROR %>={objid:"_file"};
function subfrm(frmid){
	setHtml('_title','');
	setHtml('_url','');
	setHtml('_file','');
	showGlass(frmid);
	return true;
}
</script>