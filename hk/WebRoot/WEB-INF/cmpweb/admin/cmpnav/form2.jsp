<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="navfrm" method="post" enctype="multipart/form-data" onsubmit="return subnavfrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="parentId" value="${parentId}"/>
	<hk:hide name="oid" value="${oid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="120px" align="right">
				上级栏目：
			</td>
			<td>
				${parent.name }
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td width="120px" align="right">
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
					<hk:option value="0" data="请选择"/>
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
					<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==1}">
						<hk:option value="<%=CmpNav.REFFUNC_LINK %>" data="文字或文字链接"/>
						<hk:option value="<%=CmpNav.REFFUNC_LOGINANDREG %>" data="登录与注册"/>
						<hk:option value="<%=CmpNav.REFFUNC_RETURNHOME %>" data="回到首页"/>
					</c:if>
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
				<c:if test="${o.cmpFlgEnterprise && cmpInfo.tmlflg==1}">
					<div id="reffunc_link_div" class="divrow" style="display: none;">
						栏目链接：<br/>
						http://<hk:text name="url" value="${cmpNav.url}" maxlength="200" clazz="text" style="width:200px"/><br/>
						不填写链接时，栏目只显示名称
					</div>
				</c:if>
			</td>
			<td>
				<div id="_reffunc" class="infowarn"></div>
			</td>
		</tr>
		<c:if test="${cmpInfo.tmlflg==1}">
			<tr>
				<td colspan="3">
					<div id="mod_0">
					</div>
					<table class="nt all" cellpadding="0" cellspacing="0">
						<tr>
							<td width="120px" align="right">栏目标语：</td>
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
				</td>
			</tr>
		</c:if>
		<tr>
			<td></td>
			<td>
				<div class="infowarn" id="_frm"></div>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/admincmpnav_list2.do?parentId=${parentId }&companyId=${companyId}">返回</a>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPNAV_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPNAV_REFFUNC_ERROR %>={objid:"_reffunc"};
var err_code_<%=Err.CMPNAV_SHOWFLG_ERROR %>={objid:"_showflg"};
var err_code_<%=Err.UPLOAD_ERROR %>={objid:"_frm"};
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
</script>