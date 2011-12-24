<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">用户登录
</c:set><c:set var="html_body_content" scope="request">
<div class="f_l" style="width:300px">
	<div class="f_l" style="margin-right: 20px">
		<img src="${sina_user.profileImageURL }"/>
	</div>
	<div class="f_l">
		<c:if test="${not empty sina_user.name}">${sina_user.name}</c:if>
		<c:if test="${empty sina_user.name}">${sina_user.screenName}</c:if><br/>
		${sina_user.location }<br/>
		<span class="split-r">${sina_user.friendsCount }关注</span>
		<span class="split-r">${sina_user.followersCount }粉丝</span>
		<span class="split-r">${sina_user.statusesCount }微博</span>
		<div class="b">您通过新浪微博来到顾问家</div>
	</div>
	<div class="clr"></div>
</div>
<div class="f_r" style="width:500px">
	<div class="row">
		没有顾问家账号？<input type="button" class="btn" value="直接登录" onclick="tourl('${ctx_path}/tb/login_createloginsina?return_url=${denc_return_url }')"/>
	</div>
	<div class="row">
		<div class="row b">与已有账号绑定：</div>
		<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${ctx_path }/tb/login_bindloginsina" target="hideframe">
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
						<input type="submit" value="绑定" class="btn"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function binderr(e,msg,v){
	setHtml('info',msg);
	hideGlass();
}
var return_url="${denc_return_url}";
function bindok(){
	if(return_url!='' && return_url!='null'){
		tourl(return_url);
	}
	else{
		tourl('${ctx_path}/tb/user_home');
	}
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>