<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.signup"/> - <hk:data key="view2.welcome"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="reg_l">
	<div class="step selected">
		<h2>
			<hk:data key="view2.signup"/>
		</h2>
		<img class="selected" src="<%=path %>/webst4/img/signup_arrow.gif" />
	</div>
	<div class="step">
		<h2>
			<hk:data key="view2.addfriend"/>
		</h2>
	</div>
</div>
<div class="reg_r">
	<form id="regfrm" method="post" onsubmit="return subregfrm(this.id)" action="" target="hideframe">
		<hk:hide name="inviteUserId" value="${user.userId}"/>
		<hk:hide name="ch" value="1"/>
		<div class="row">
			<h1 class="title1">
				<hk:data key="view2.welcome"/>
			</h1>
			<h3 style="font-weight: normal;">
				<hk:data key="view2.welcometip"/>
			</h3>
		</div>
		<table class="nt reg" cellpadding="0" cellspacing="0">
			<tr>
				<td width="100px" align="right">
					* <hk:data key="view2.nickname"/>
				</td>
				<td width="260px">
					<input type="text" class="text" maxlength="50" name="nickName" />
				</td>
				<td class="ruo">
					<hk:data key="7"/>
					<div id="_nickname" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td width="100px" align="right">
					* <hk:data key="view2.email"/>
				</td>
				<td width="260px">
					<input type="text" class="text" maxlength="50" name="email" />
				</td>
				<td class="s"><div id="_email" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					<hk:data key="view2.mobile"/>
				</td>
				<td>
					<input type="text" class="text" maxlength="20" name="mobile" />
				</td>
				<td class="s"><div id="_mobile" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					* <hk:data key="view2.password"/>
				</td>
				<td>
					<input type="password" class="text" maxlength="20" name="password" />
				</td>
				<td class="s ruo">
					<hk:data key="view2.password.tip"/>
					<div id="_password" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					* <hk:data key="view2.repassword"/>
				</td>
				<td>
					<input type="password" class="text" maxlength="20" name="repassword" />
				</td>
				<td class="s"><div id="_repassword" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					* <hk:data key="view2.yourzone"/>
				</td>
				<td>
					<c:set var="zoneName"><c:if test="${city!=null}">${city.city}</c:if><c:if test="${city==null}">${sys_zone_pcity.name}</c:if></c:set>
					<input type="text" class="text" maxlength="20" name="zoneName" value="${zoneName }" />
				</td>
				<td class="s"><div id="_zoneName" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					* <hk:data key="view2.sex"/>
				</td>
				<td>
					<input type="radio" name="sex" value="<%=User.SEX_MALE %>" />
					<hk:data key="view2.sex.male"/>
					<input type="radio" name="sex" value="<%=User.SEX_FEMALE %>" />
					<hk:data key="view2.sex.female"/>
				</td>
				<td class="s"><div id="_sex" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					* 邀请码
				</td>
				<td>
					<input type="text" name="inviteCode" class="text_yzm" style="width: 150px" value="${inviteCode }"/>
				</td>
				<td class="s"><div id="_inviteCode" class="infowarn"></div>
				<div id="_code" class="infowarn"></div>
				</td>
			</tr>
			<!-- 
			<tr>
				<td align="right">
					* <hk:data key="view2.validatecode"/>
				</td>
				<td>
					<input type="text" name="code" class="text_yzm" maxlength="4" size="4" />
					<img id="codeimg" alt="" src="<%=path %>/index_showImg.do?v=<%=Math.random() %>"/>
					<a href="javascript:showimg()">刷新验证码</a>
				</td>
				<td class="s"><div id="_code" class="infowarn"></div>
				</td>
			</tr>
			 -->
			<tr>
				<td></td>
				<td>
					<br />
					<hk:submit value="view2.submit" clazz="btn" res="true"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<p style="margin-left: 110px; font-size: 12px;">
						<a href="javascript:showxieyi()"><hk:data key="view2.reg.regagreement"/></a>
					</p>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="clr"></div>
<script type="text/javascript">
var return_url="${denc_return_url }";
var err_code_<%=Err.REG_INPUT_VALIDATECODE %>={objid:"_code"};
var err_code_<%=Err.INVITECODE_ERROR %>={objid:"_inviteCode"};
var err_code_<%=Err.INVITECODE_EXPIRED %>={objid:"_inviteCode"};
var err_code_<%=Err.NICKNAME_DUPLICATE %>={objid:"_nickname"};
var err_code_<%=Err.NICKNAME_ERROR %>={objid:"_nickname"};
var err_code_<%=Err.NICKNAME_ERROR2 %>={objid:"_nickname"};
var err_code_<%=Err.PASSWORD_DATA_ERROR %>={objid:"_password"};
var err_code_<%=Err.REG_2_PASSWORD_NOT_SAME %>={objid:"_repassword"};
var err_code_<%=Err.MOBILE_OR_EMAIL_INPUT %>={objid:"_email"};
var err_code_<%=Err.EMAIL_ERROR %>={objid:"_email"};
var err_code_<%=Err.MOBILE_ERROR %>={objid:"_mobile"};
var err_code_<%=Err.SEX_ERROR %>={objid:"_sex"};
var err_code_<%=Err.EMAIL_ALREADY_EXIST2 %>={objid:"_email"};
var err_code_<%=Err.MOBILE_ALREADY_EXIST %>={objid:"_mobile"};
function subregfrm(frmid){
	setHtml('_nickname','');
	setHtml('_email','');
	setHtml('_password','');
	setHtml('_repassword','');
	setHtml('_code','');
	setHtml('_sex','');
	showGlass(frmid);
	return true;
}
function regok(error,error_msg,respValue){
	if(return_url!=""){
		tourl(return_url);
		return;
	}
	tourl('/overview');
}
function regerror(error,error_msg,respValue){
	setHtml(getoidparam(error),error_msg);
	hideGlass();
}
function regerrorlist(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	hideGlass();
}
function showimg(){
	getObj('codeimg').src="<%=path %>/index_showImg.do?v="+Math.random();
}
function showxieyi(){
	var html='<hk:data key="view2.reg.regagreement2"/>';
	createBg();
	createCenterWindow("xieyi_win",500,260,'<hk:data key="view2.huoku_xieyi"/>',html,"hideWindow('xieyi_win');clearBg();");
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>