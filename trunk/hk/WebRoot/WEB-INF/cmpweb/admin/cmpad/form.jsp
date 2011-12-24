<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" enctype="multipart/form-data" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="adid" value="${adid}"/>
	<hk:hide name="groupId" value="${groupId}"/>
	<hk:hide name="blockId" value="${blockId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="2">
				<div class="b">广告可以是站内的文章、产品等单页面也可以是其他站点的合作广告<br/>
				如果不上传图片文件，广告就为文字广告<br/>
				图片广告中，名称将不会显示在页面
				</div>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">
				广告名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpAd.name}"/> 
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				广告网址：
			</td>
			<td>
				<hk:text name="url" clazz="text" value="${cmpAd.url}"/> 
				<div class="infowarn" id="_url"></div>
			</td>
		</tr>
		<c:if test="${cmpPageBlock==null || !cmpPageBlock.cmpPageMod.modAdText}">
		<tr>
			<td width="90px" align="right">
				广告图片：
			</td>
			<td>
				<c:if test="${cmpAd!=null}">
					<div><img src="${cmpAd.picUrl }"/></div>
				</c:if>
				<input type="file" name="f" size="50"/>
				<div class="b">
					<c:if test="${o.cmpFlgEnterprise}">
						图片文件宽边不能超过200px<br/>
					</c:if>
					<c:if test="${o.cmpEdu}">
						首页通栏图片广告区域最多支持4个广告<br/>
						图片高度最大90px，超过部分将不显示<br/>
					</c:if>
					图片文件大小不能超过100K<br/>
					图片文件格式只支持jpg，gif，png
				</div>
				<div class="infowarn" id="_file"></div>
			</td>
		</tr>
		</c:if>
		<c:if test="${o.cmpEdu && !(blockId>0)}">
			<tr>
			<td width="90px" align="right"></td>
			<td>
				<hk:checkbox oid="_refflg" name="refflg" value="1" checkedvalue="${refflg}" /><label for="_refflg">推荐到二级页面左侧空白区</label><br/>
				二级栏目中宽度为200px
			</td>
			</tr>
		</c:if>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/>
				<c:if test="${blockId>0}">
				<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_content.do?companyId=${companyId}&blockId=${blockId}">返回</a>
				</c:if> 
				<c:if test="${!(blockId>0)}">
				<a href="<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}">返回</a>
				</c:if> 
				
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.IMG_FMT_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_UPLOAD_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_OUTOFSIZE_ERROR %>={objid:"_file"};
var err_code_<%=Err.CMPAD_NAME_ERROR%>={objid:"_name"};
var err_code_<%=Err.CMPAD_URL_ERROR%>={objid:"_url"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_url','');
	if(getObj('_file')!=null){
		setHtml('_file','');
	}
	showGlass(frmid);
	return true;
}
</script>