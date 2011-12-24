<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">用户登录
</c:set><c:set var="html_body_content" scope="request">
<div class="f_l" style="width:500px">
	<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${ctx_path }/tb/login" target="hideframe">
		<hk:hide name="return_url" value="${return_url}"/>
		<hk:hide name="ch" value="1"/>
		<table class="formt" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90" align="right">E-mail</td>
				<td>
					<input type="text" name="input" value="" maxlength="50" class="text"/>
				</td>
			</tr>
			<tr>
				<td width="90" align="right">密码</td>
				<td>
					<input type="password" name="pwd" maxlength="50" class="text"/>
					<div class="infowarn" id="info"></div>
				</td>
			</tr>
			<tr>
				<td width="90" align="right"></td>
				<td>
					<input type="submit" value="登录" class="btn"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="f_r" style="width:300px">
	<div class="row">
		<a href="${ctx_path }/tb/login_loginsina?return_url=${return_url}"><img src="${ctx_path }/webtb/img/sina_login_32.png"/></a>
	</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function loginerr(e,msg,v){
	setHtml('info',msg);
	hideGlass();
}
var return_url="${denc_return_url}";
function loginok(){
	if(return_url!='' && return_url!='null'){
		tourl(return_url);
	}
	else{
		tourl('${ctx_path}/tb/user_home');
	}
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>