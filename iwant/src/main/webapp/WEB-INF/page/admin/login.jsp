<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"
%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set scope="request" var="html_body_content">
<div class="mod">
	<div class="mod_title">后台登录</div>
	<div class="mod_content">
		<div class="hcenter" style="width: 600px">
			<form id="frm" action="${appctx_path }/sitemgrlogin.do" onsubmit="return subfrm(this.id)" method="post" target="hideframe">
				<hk:hide name="ch" value="1"/>
				<div class="infowarn" id="errmsg"></div>
				<table class="formt" cellpadding="0" cellspacing="0">
					<tr>
						<td width="90px">用户名：</td>
						<td><input type="text" class="text" name="username" maxlength="20"/></td>
					</tr>
					<tr>
						<td width="90px">密码：</td>
						<td><input type="password"" class="text" name="pwd" maxlength="20"/></td>
					</tr>
					<tr>
						<td width="90px"></td>
						<td><input type="submit" class="btn" value="登录"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
function subfrm(frmid){
	return true;
}
function loginerr(err,err_msg,v){
	setHtml('errmsg',err_msg);
}
function loginok(err,err_msg,v){
	alert('login ok');
}
</script>
</c:set><jsp:include page="inc/frame.jsp"></jsp:include>