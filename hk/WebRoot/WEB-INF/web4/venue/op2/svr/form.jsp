<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="svrId" value="${svrId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="150px" align="right">
			</td>
			<td>
				<c:if test="${cmpSvr!=null}">
					<c:if test="${cmpSvr.photosetId>0}">
						<img src="${cmpPhotoSet.pic60 }"/><br/>
						${cmpPhotoSet.name }
					</c:if>
				</c:if>
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpSvr.name}"/> 
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				分类：
			</td>
			<td>
			<%EppViewUtil.loadCmpSvrKindList(request); %>
			<hk:select name="kindId" checkedvalue="${cmpSvr.kindId}">
					<hk:option value="0" data="请选择"/>
				<c:forEach var="kind" items="${kindlist}">
					<hk:option value="${kind.kindId}" data="${kind.name}"/>
				</c:forEach>
			</hk:select>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				价格：
			</td>
			<td>
				<hk:text name="price" clazz="text" value="${cmpSvr.price}"/>元
				<div class="infowarn" id="_price"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				时长：
			</td>
			<td>
				<hk:text name="svrmin" clazz="text" value="${cmpSvr.svrmin}"/>单位为分钟
				<div class="infowarn" id="_svrmin"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				描述：
			</td>
			<td>
				<hk:textarea name="intro" style="width:300px;height:100px" value="${cmpSvr.intro}"/>
				<div class="infowarn" id="_intro"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/>
				<a href="<%=path %>/h4/op/venue/svr.do?companyId=${companyId}">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPSVR_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPSVR_INTRO_ERROR %>={objid:"_intro"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_intro','');
	showGlass(frmid);
	return true;
}
function selset(svrId){
	tourl("<%=path %>/h4/op/venue/photo_selphotosetforsvr.do?companyId=${companyId}&svrId="+svrId+"&return_url="+encodeLocalURL());
}
</script>