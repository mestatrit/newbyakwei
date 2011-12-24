<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="navfrm" method="post" enctype="multipart/form-data" onsubmit="return subnavfrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="oid" value="${oid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="130px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpNav.name}"/> 
			</td>
			<td>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td align="right">
				挂接功能：
			</td>
			<td>
				<hk:select name="reffunc" checkedvalue="${cmpNav.reffunc}" onchange="check_navcontentref(this.value)">
					<hk:option value="<%=CmpNav.REFFUNC_NONE %>" data="目录"/>
					<hk:option value="<%=CmpNav.REFFUNC_HOME %>" data="网站首页"/>
					<hk:option value="<%=CmpNav.REFFUNC_SINGLECONTENT %>" data="单篇文章"/>
					<hk:option value="<%=CmpNav.REFFUNC_LISTCONTENT %>" data="连载文章"/>
					<hk:option value="<%=CmpNav.REFFUNC_BOX %>" data="宝箱"/>
					<hk:option value="<%=CmpNav.REFFUNC_COUPON %>" data="优惠券"/>
					<hk:option value="<%=CmpNav.REFFUNC_BBS %>" data="论坛"/>
					<hk:option value="<%=CmpNav.REFFUNC_MSG %>" data="留言"/>
					<hk:option value="<%=CmpNav.REFFUNC_CONTACT %>" data="在线联系"/>
					<hk:option value="<%=CmpNav.REFFUNC_MAP %>" data="地图"/>
					<hk:option value="<%=CmpNav.REFFUNC_SELNET %>" data="销售网点"/>
					<hk:option value="<%=CmpNav.REFFUNC_PRODUCT %>" data="产品"/>
					<c:if test="${cmpSvrCnf.openFile}">
						<hk:option value="<%=CmpNav.REFFUNC_CMPDOWNFILE%>" data="文件管理系统"/>
					</c:if>
						<c:if test="${cmpSvrCnf.openVideo}">
					<hk:option value="<%=CmpNav.REFFUNC_VIDEO %>" data="视频管理系统"/>
					</c:if>
					<c:if test="${o.cmpHairDressing}">
						<hk:option value="<%=CmpNav.REFFUNC_RESERVE %>" data="预约服务"/>
					</c:if>
					<c:forEach var="funcref" items="${funcreflist}">
						<c:if test="${funcref.hasFuncUserTable}">
							<hk:option value="<%=CmpNav.REFFUNC_TABLE_DATA %>" data="自定义数据表"/>
						</c:if>
					</c:forEach>
				</hk:select>
				<div id="_navcontentref" style="display: none;">
					<div class="divrow">
						内容显示方式：<br/>
						<hk:radioarea name="showflg" checkedvalue="${cmpNav.showflg}" forcecheckedvalue="<%=CmpNav.SHOWFLG_TITLE %>">
							<hk:radio oid="show_img" value="<%=CmpNav.SHOWFLG_IMG %>"/><label for="show_img">图片方式</label><br/>
							<hk:radio oid="show_title" value="<%=CmpNav.SHOWFLG_TITLE %>"/><label for="show_title">列表方式</label>
						</hk:radioarea>
						<div class="infowarn" id="_showflg"></div>
					</div>
				</div>
				<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==0}">
				<div class="divrow">
					文章是否挂接申请加盟表单：<br/>
					<hk:radioarea name="applyformflg" checkedvalue="${cmpNav.applyformflg}" forcecheckedvalue="<%=CmpNav.APPLYFORMFLG_N %>">
						<hk:radio oid="applyformflg_n" value="<%=CmpNav.APPLYFORMFLG_N %>"/> <label for="applyformflg_n">否</label>
						<hk:radio oid="applyformflg_y" value="<%=CmpNav.APPLYFORMFLG_Y %>"/> <label for="applyformflg_y">是</label>
					</hk:radioarea>
				</div>
				</c:if>
			</td>
			<td>
				<div id="_reffunc" class="infowarn"></div>
			</td>
		</tr>
		<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==0}">
		<!-- 企业网站模板0可使用的 -->
			<tr>
				<td align="right">
					图片或flash文件：<br/>
					<strong>（可选）</strong><br/>
				</td>
				<td>
					<hk:file name="f"/>
					<hk:select name="fileShowflg" checkedvalue="${cmpNav.fileShowflg}">
						<hk:option value="<%=CmpNav.FILESHOWFLG_RIGHT %>" data="两栏模式(宽630px，高73px)"/>
						<hk:option value="<%=CmpNav.FILESHOWFLG_ALL %>" data="通栏模式(宽860px，高自动)"/>
					</hk:select><br/>
					<span class="b">
					图片只支持大小在200k以内的jpg，gif，png文件<br/>
					flash只支持大小在500k以内swf格式的文件<br/>
					</span>
				</td>
				<td>
					<div class="infowarn" id="_file"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					文件链接：<br/>
					<strong>（可选）</strong>
				</td>
				<td>
					<hk:text name="fileShowLink" value="${cmpNav.fileShowLink}" clazz="text"/><br/>
					<span class="b">当上传了文件时，此内容有效</span>
				</td>
				<td>
					<div class="infowarn" id="_fileShowLink"></div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<c:if test="${not empty cmpNav.filepath}">
						<div style="width: 600px;overflow: hidden;">
							<c:if test="${cmpNav.imageShow}">
									<a href="${cmpNav.picHUrl }" target="_blank"><img src="${cmpNav.picHUrl }"/></a>
								
							</c:if>
							<c:if test="${cmpNav.flashShow}">
								<embed width="400" height="200" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${cmpNav.flashHUrl }" play="true" loop="true" menu="true"></embed>
							</c:if><br/>
							<a href="javascript:delfile(${oid })">删除文件</a>
						</div>
					</c:if>
				</td>
			</tr>
		</c:if>
		<c:if test="${cmpInfo.tmlflg==1}">
			<!-- 企业网站模板1可使用 -->
			<tr>
				<td colspan="3">
					<div id="home_ref" style="display: none;">
						<table class="nt all" cellpadding="0" cellspacing="0">
							<tr>
								<td width="130px" align="right">栏目标语：</td>
								<td>
									<hk:text name="title" value="${cmpNav.title}" clazz="text"/>
								</td>
								<td>
									<div id="_title" class="infowarn"></div>
								</td>
							</tr>
							<tr>
								<td align="right">栏目副标语：</td>
								<td>
									<hk:textarea name="intro" value="${cmpNav.intro}" style="width:270px;height:100px"/>
								</td>
								<td>
									<div id="_intro" class="infowarn"></div>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right">背景图片：</td>
				<td>
					<hk:radioarea name="bgflg" checkedvalue="${cmpNav.bgflg}" clazz="input_radio">
						<hk:radio oid="bg_def" value="<%=CmpNav.BGFLG_DEFAULT %>"/>
						<label for="bg_def">使用默认背景图</label>
						<hk:radio oid="bg_set" value="<%=CmpNav.BGFLG_SET %>"/>
						<label for="bg_set">使用自设背景图</label>
						<hk:radio oid="bg_none" value="<%=CmpNav.BGFLG_NONE %>"/>
						<label for="bg_none">不使用背景图</label>
					</hk:radioarea>
					<div id="bgfile" style="display: none;">
						<hk:file name="bgf"/>
					</div>
					<script type="text/javascript">
					if($('#bg_set').attr('checked')==true){
						$('#bgfile').css('display','block');
					}
					</script>
				</td>
				<td>
					<div id="_bgf" class="infowarn"></div>
				</td>
			</tr>
			<c:if test="${not empty cmpNav.bgPicPath}">
			<tr>
				<td colspan="3">
					<a href="javascript:delbgfile(${oid })">删除背景图文件</a>
					<div style="width: 600px;height: 400px;overflow: hidden;">
						<a href="${cmpNav.bgPicUrl }" target="_blank"><img src="${cmpNav.bgPicUrl }"/></a>
					</div>
				</td>
			</tr>
			</c:if>
		</c:if>
		<tr>
			<td></td>
			<td>
				<div class="infowarn" id="_frm"></div>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">返回</a>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPNAV_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPNAV_SHOWFLG_ERROR %>={objid:"_showflg"};
var err_code_<%=Err.UPLOAD_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_FMT_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE %>={objid:"_file"};
var err_code_<%=Err.CMPNAV_FILESHOWLINK_ERROR %>={objid:"_fileShowLink"};
var err_code_<%=Err.CMPNAV_REFFUNC_BBS_EXIST %>={objid:"_reffunc"};
var err_code_<%=Err.CMPNAV_REFFUNC_PRODUCT_EXIST %>={objid:"_reffunc"};
var err_code_<%=Err.CMPNAV_TITLE_ERROR %>={objid:"_title"};
var err_code_<%=Err.CMPNAV_INTRO_ERROR %>={objid:"_intro"};
var err_code_<%=Err.CMPNAV_BGIMG_ERROR %>={objid:"_bgf"};
var err_code_<%=Err.CMPNAV_BGIMG_UPLOAD_ERROR %>={objid:"_bgf"};
function subnavfrm(frmid){
	setHtml('_name','');
	setHtml('_showflg','');
	setHtml('_frm','');
	$('#_title').html('');
	$('#_intro').html('');
	$('#_bgf').html('');
	showGlass(frmid);
	return true;
}
function delfile(oid){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/admincmpnav_delfile.do?companyId=${companyId}&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function delbgfile(oid){
	if(window.confirm('确实要删除背景图片？')){
		$.ajax({
			type:"POST",
			url:"<%=path %>/epp/web/op/webadmin/admincmpnav_delbgfile.do?companyId=${companyId}&oid="+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
$(document).ready(function(){
	$('#bg_def').bind('click',function(){
		if($(this).attr('checked')==true){
			$('#bgfile').css('display','none');
		}
	});
	$('#bg_none').bind('click',function(){
		if($(this).attr('checked')==true){
			$('#bgfile').css('display','none');
		}
	});
	$('#bg_set').bind('click',function(){
		if($(this).attr('checked')==true){
			$('#bgfile').css('display','block');
		}
	});
});
</script>