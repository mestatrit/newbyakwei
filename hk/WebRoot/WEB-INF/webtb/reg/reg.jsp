<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.taobao.Tb_User"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">用户注册
</c:set><c:set var="html_body_content" scope="request">
	<div class="reg_l">
		<div class="step selected"><h2>注册</h2><img class="selected" src="${ctx_path }/webtb/img/signup_arrow.gif" /></div>
		<div class="step"><h2>关注朋友</h2></div>
		<div class="step"><h2>绑定应用</h2></div>
	</div>
	<div class="reg_r">
		<form id="regfrm" method="post" onsubmit="return subregfrm(this.id)" action="${ctx_path }/tb/register" target="hideframe">
			<hk:hide name="ch" value="1"/>
			<h1 class="title1">顾问家会员注册信息申请</h1>
			<h3 style="font-weight: normal;">
				顾问家的目的通过有公信力和影响力的人来让那些货真价实的淘宝商家和商品浮出水平，并对推荐者给与荣誉和奖励，请认证填写其信息。
				<br/>带有*的为必填项
			</h3>
			<table class="formt" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right" width="90"><span class="must">*</span>昵称</td>
					<td>
						<input type="text" class="text" name="nick" maxlength="20" />
						<div class="ruo">
							昵称为2-10个中文或者4-20个英文，不能包含特殊符号
						</div>
						<div class="infowarn" id="_nick"></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="90"><span class="must">*</span>常用E-mail</td>
					<td>
						<input type="text" class="text" name="email" maxlength="60" />
						<div class="infowarn" id="_email"></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="90"><span class="must">*</span>密码设定</td>
					<td>
						<input type="password" class="text" name="pwd" maxlength="10" />
						<div class="ruo">
							密码为4-16个字符
						</div>
						<div class="infowarn" id="_pwd"></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="90"><span class="must">*</span>再次输入密码</td>
					<td>
						<input type="password" class="text" name="repwd" maxlength="10" />
						<div class="infowarn" id="_repwd"></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="90"><span class="must">*</span>性别</td>
					<td>
						<input type="radio" id="male" name="gender" value="<%=Tb_User.GENDER_MALE %>"/>
						<label for="male">男</label>
						<input type="radio" id="female" name="gender"  value="<%=Tb_User.GENDER_FEMALE %>"/>
						<label for="female">女</label>
						<div class="infowarn" id="_gender"></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="90"><span class="must">*</span>所在地</td>
					<td>
						<jsp:include page="../inc/zone_sel.jsp"></jsp:include>
						<div class="infowarn" id="_cityid"></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="90">淘宝昵称</td>
					<td>
						<input type="text" class="text" name="taobao_nick" maxlength="20" />
						<div class="infowarn" id="_taobao_nick"></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="90"><span class="must">*</span>验证码</td>
					<td>
						<input type="text" class="text_yzm" name="code" maxlength="4" />
						<img id="yzm" src="${ctx_path }/tb/register_img?v=<%=Math.random() %>" style="vertical-align: middle;"/>
						<a href="javascript:chgyzm()">更换验证码</a>
						<div class="infowarn" id="_code"></div>
					</td>
				</tr>
				<tr>
					<td align="right" width="90"></td>
					<td>
						<input type="submit" class="btn split-r" value="确认提交注册信息" />
						<input type="button" class="btn" value="返回" onclick="tourl('/tb/index')" />
					</td>
				</tr>
				<tr>
					<td align="right" width="90"></td>
					<td>
						<input type="checkbox" name="aggree" value="1" checked="checked"/>
						<a href="#">我已看过并同意顾问家注册协议</a>
						<div class="infowarn" id="_aggree"></div>
					</td>
				</tr>
			</table>
		</form>
	</div>
<div class="clr"></div>
<script type="text/javascript">
var return_url="${denc_return_url }";
var err_code_<%=Err.TB_USER_NICK_DUPLICATE %>={objid:"_nick"};
var err_code_<%=Err.TB_USER_NICK_ERROR %>={objid:"_nick"};
var err_code_<%=Err.TB_USER_CITYID_ERROR %>={objid:"_cityid"};
var err_code_<%=Err.TB_USER_EMAIL_ERROR %>={objid:"_email"};
var err_code_<%=Err.TB_USER_EMAIL_DUPLICATE %>={objid:"_email"};
var err_code_<%=Err.TB_USER_GENDER_ERROR %>={objid:"_gender"};
var err_code_<%=Err.TB_USER_CODE_ERROR %>={objid:"_code"};
var err_code_<%=Err.TB_USER_REPWD_ERROR %>={objid:"_repwd"};
var err_code_<%=Err.TB_USER_PWD_ERROR %>={objid:"_pwd"};
var err_code_<%=Err.TB_USER_TAOBAO_NICK_ERROR %>={objid:"_taobao_nick"};
var submited=false;
function subregfrm(frmid){
	if(submited){
		return false;
	}
	submited=true;
	setHtml('_nick','');
	setHtml('_email','');
	setHtml('_pwd','');
	setHtml('_repwd','');
	setHtml('_code','');
	setHtml('_gender','');
	setHtml('_cityid','');
	setHtml('_taobao_nick','');
	showGlass(frmid);
	return true;
}
function regok(error,error_msg,respValue){
	if(return_url!=""){
		tourl(return_url);
		return;
	}
	tourl('${ctx_path}/tb/op/guide_bindapp');
}
function regerr(json,errorlist){
	submited=false;
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	hideGlass();
}
function chgyzm(){
	getObj('yzm').src="${ctx_path}/tb/register_img?v="+Math.random();
}
getObj('regfrm').reset();
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>