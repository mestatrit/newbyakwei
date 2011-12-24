<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%>
<%@page import="web.pub.util.EppViewUtil"%>
<%@page import="com.hk.bean.CmpActor"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" enctype="multipart/form-data" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="actorId" value="${actorId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<c:if test="${!cmpActor.onWorkDay}">
			<tr>
				<td width="160px" align="right">
				</td>
				<td>
					<c:if test="${can_set_work && cmpActor!=null}">
						<input type="button" class="btn" value="设置为今天值班" onclick="setworktoday()"/>
					</c:if>
					<c:if test="${!can_set_work && cmpActor!=null}">
						值班中... ...
						<a href="javascript:deltodaywork()">取消值班</a>
					</c:if>
				</td>
			</tr>
		</c:if>
		<tr>
			<td width="160px" align="right">
				姓名：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpActor.name}"/> 
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="160px" align="right">
				性别：
			</td>
			<td>
				<hk:radioarea name="gender" checkedvalue="${cmpActor.gender}">
					<hk:radio oid="male" value="<%=CmpActor.GENDER_MALE %>"/><label for="male">男</label>
					<hk:radio oid="female" value="<%=CmpActor.GENDER_MALE %>"/><label for="female">女</label>
				</hk:radioarea>
				<div class="infowarn" id="_gender"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				角色：
			</td>
			<td><%EppViewUtil.loadCmpActorRoleList(request); %>
				<hk:select name="roleId" checkedvalue="${cmpActor.roleId}">
					<hk:option value="0" data="请选择"/>
					<c:forEach var="role" items="${rolelist}">
						<hk:option value="${role.roleId}" data="${role.name}"/>
					</c:forEach>
				</hk:select>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				是否可以进行预约管理：
			</td>
			<td>
				<hk:radioarea name="reserveflg" checkedvalue="${cmpActor.reserveflg}">
					<hk:radio value="<%=CmpActor.RESERVEFLG_N %>" oid="reserveflg_0"/><label for="reserveflg_0">否</label>
					<hk:radio value="<%=CmpActor.RESERVEFLG_Y %>" oid="reserveflg_1"/><label for="reserveflg_1">是</label>
				</hk:radioarea>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				描述：
			</td>
			<td>
				<hk:textarea name="intro" value="${cmpActor.intro}" style="width:300px;height:100px;"/>
				<div class="infowarn" id="_intro"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				照片：
			</td>
			<td>
				<c:if test="${not empty cmpActor.picPath}">
					<div><img src="${cmpActor.pic500Url }"/></div>
				</c:if>
				<input type="file" name="f" size="50"/>
				<div class="b">
					图片文件大小不能超过500K<br/>
					图片文件格式只支持jpg，gif，png
				</div>
				<div class="infowarn" id="_file"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/>
				<a href="<%=path %>/h4/op/venue/actor.do?companyId=${companyId}">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.IMG_FMT_ERROR %>={objid:"_file"};
var err_code_<%=Err.UPLOAD_FILE_SIZE_LIMIT %>={objid:"_file"};
var err_code_<%=Err.IMG_UPLOAD_ERROR %>={objid:"_file"};
var err_code_<%=Err.IMG_OUTOFSIZE_ERROR %>={objid:"_file"};
var err_code_<%=Err.CMPACTOR_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPACTOR_INTRO_ERROR %>={objid:"_intro"};
var err_code_<%=Err.CMPACTOR_GENDER_ERROR %>={objid:"_gender"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_intro','');
	setHtml('_gender','');
	setHtml('_file','');
	showGlass(frmid);
	return true;
}
function setworktoday(){
	$.ajax({
		type:"POST",
		url:"<%=path %>/h4/op/venue/actor_settodaywork.do?companyId=${companyId}&actorId=${actorId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function deltodaywork(){
	$.ajax({
		type:"POST",
		url:"<%=path %>/h4/op/venue/actor_deltodaywork.do?companyId=${companyId}&actorId=${actorId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>