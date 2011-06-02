<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set scope="request" var="html_body_content">
<div class="mod">
	<div class="mod_title">后台登录</div>
	<div class="mod_content">
		<div class="hcenter" style="width: 600px">
			<form id="frm" action="${appctx_path }/sitemgrlogin.do" onsubmit="return subfrm(this.id)" method="post" target="hideframe">
				<input type="hidden" name="ch" value="1"/>
				<div class="infowarn" id="errmsg"></div>
				<table class="formt" cellpadding="0" cellspacing="0">
					<tr>
						<td width="90">用户名：</td>
						<td><input type="text" class="text" name="username" maxlength="20"/></td>
					</tr>
					<tr>
						<td width="90">密码：</td>
						<td><input type="password" class="text" name="pwd" maxlength="20"/></td>
					</tr>
					<tr>
						<td width="90"></td>
						<td><input type="submit" class="btn" value="登录"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
var glassid_op=null;
function subfrm(frmid){
	glassid_op=addGlass('frm',false);
	setHtml('errmsg','');
	return true;
}
function loginerr(err,err_msg,v){
	setHtml('errmsg',err_msg);
	removeGlass(glassid_op);
}
function loginok(err,err_msg,v){
	tourl('${appctx_path}/mgr/cat.do');
}
</script>
</c:set><jsp:include page="inc/frame.jsp"></jsp:include>