<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">认领审核</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width:600px">
<div class="mod">
<div class="mod_title">填写认证申请</div>
<div class="mod_content"><br/>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/h4/op/authcmp.do" target="hideframe">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="ch" value="1"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="100px" align="right">联系人姓名：</td>
			<td>
				<hk:text name="username" clazz="text" maxlength="30"/>
				<div class="infowarn" id="_username"></div>
			</td>
		</tr>
		<tr>
			<td width="100px" align="right">联系电话：</td>
			<td>
				<hk:text name="tel" clazz="text" maxlength="40"/>
				<div class="infowarn" id="_tel"></div>
			</td>
		</tr>
		<tr>
			<td width="100px" align="right">公司名称：</td>
			<td>
				<hk:text name="name" clazz="text" maxlength="30" value="${name}"/>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="100px" align="right">申请内容：</td>
			<td>
				<hk:textarea name="content" clazz="text" style="width:270px;height:100px"/>
				<div class="infowarn" id="_content"></div>
			</td>
		</tr>
		<tr>
			<td width="100px" align="right"></td>
			<td>
			<hk:submit clazz="btn split-r" value="提交"/>
			<a href="${denc_return_url }">返回</a>
			</td>
		</tr>
	</table>
</form>
</div>
</div>
</div>
<script type="text/javascript">
var return_url="${denc_return_url }";
var err_code_<%=Err.AUTHCOMPANY_CONTENT_ERROR %>={objid:"_content"};
var err_code_<%=Err.AUTHCOMPANY_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.AUTHCOMPANY_TEL_ERROR %>={objid:"_tel"};
var err_code_<%=Err.AUTHCOMPANY_USERNAME_ERROR %>={objid:"_username"};
function subfrm(frmid){
	showGlass(frmid);
	setHtml('_username','');
	setHtml('_tel','');
	setHtml('_name','');
	setHtml('_content','');
	return true;
}
function errlist(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	hideGlass();
}
function createok(err,msg,v){
	tourl("${denc_return_url}");
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>