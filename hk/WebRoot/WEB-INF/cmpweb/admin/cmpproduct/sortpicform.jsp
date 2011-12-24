<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" enctype="multipart/form-data" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="oid" value="${oid}"/>
	<hk:hide name="sortId" value="${sortId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="150px" align="right">
				标题：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpProductSortFile.name}" maxlength="20"/> 
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				网址：
			</td>
			<td>
				<hk:text name="url" clazz="text" value="${cmpProductSortFile.url}"/> 
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
					广告区域可放置2张图片<br/>
					图片宽度自定义但是2张广告图片的总宽度不能超过725px<br/>
					每张图片合适高300px<br/>
					每张图片文件大小不能超过100K<br/>
					图片文件格式只支持jpg，gif，png
				</div>
				<div class="infowarn" id="_file"></div>
			</td>
		</tr>
		<c:if test="${cmpProductSortFile!=null}">
		<tr>
		<td colspan="2" align="center">
		<div><img src="${cmpProductSortFile.picUrl }"/></div>
		</td>
		</tr>
		</c:if>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortpic.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPPRODUCTSORTFILE_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPPRODUCTSORTFILE_URL_ERROR%>={objid:"_url"};
var err_code_<%=Err.MUST_PIC_UPLOAD %>={objid:"_file"};
var err_code_<%=Err.IMG_UPLOAD_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_OUTOFSIZE_ERROR %>={objid:"_file"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_url','');
	setHtml('_file','');
	showGlass(frmid);
	return true;
}
</script>