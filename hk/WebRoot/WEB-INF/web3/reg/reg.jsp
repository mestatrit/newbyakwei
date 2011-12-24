<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.User"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view.regwebsite"/></c:set>
<c:set var="body_hk_content" scope="request">
	<div class="mod-8">
		<div class="bg"><div class="rd"><div class="l"></div><div class="mid"></div><div class="r"></div><div class="clr"></div></div></div>
		<div class="cont">
			<div class="regcon">
				<div class="reg_left">
					<div class="mod">
						<h3>输入用户信息</h3>
						<div class="bdbtm"></div>
						<c:set var="td_width" value="90px"/>
						<div class="formcon">
							<form id="reg_frm" method="post" onsubmit="return subregfrm(this.id)" action="<%=path %>/reg_regweb.do" target="hideframe">
							<c:if test="${inviteId>0 || inviterId>0}">
								<hk:hide name="code" value="${regCode.name}"/>
							</c:if>
							<hk:hide name="pcflg" value="1"/>
							<hk:hide name="prouserId" value="${prouserId}"/>
							<hk:hide name="inviteId" value="${inviteId}"/>
							<hk:hide name="inviterId" value="${inviterId}"/>
							<table class="infotable" cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td width="90px"></td>
									<td>
										<div class="ok text_14"><hk:data key="view.reg.tip-1"/></div>
									</td>
								</tr>
								<tr>
									<td width="90px">E-mail</td>
									<td>
										<div class="f_l">
											<hk:text name="email" maxlength="50" clazz="text"/><br/>
											<div id="email_error" class="error"></div>
										</div>
										<div id="email_flag" class="flag"></div><div class="clr"></div>
									</td>
								</tr>
								<tr>
									<td>手机号</td>
									<td>
										<div class="f_l">
											<hk:text name="mobile" maxlength="11" clazz="text"/><br/>
											<div id="mobile_error" class="error"></div>
										</div>
										<div id="mobile_flag" class="flag"></div><div class="clr"></div>
									</td>
								</tr>
								<tr>
									<td>密码</td>
									<td>
										<div class="f_l">
											<hk:pwd name="password" maxlength="16" clazz="text"/><br/>
											<div id="password_error" class="error"></div>
										</div>
										<div id="password_flag" class="flag"></div><div class="clr"></div>
									</td>
								</tr>
								<tr>
									<td>重复输入密码</td>
									<td>
										<div class="f_l">
											<hk:pwd name="password1" maxlength="16" clazz="text"/><br/>
											<div id="password1_error" class="error"></div>
										</div>
										<div id="password1_flag" class="flag"></div><div class="clr"></div>
									</td>
								</tr>
								<tr>
									<td>性别</td>
									<td>
										<div class="f_l">
											<hk:radioarea name="sex">
												<span class="span_rdo"><hk:radio value="<%=User.SEX_MALE+"" %>" data="男"/></span>
												<span class="span_rdo"><hk:radio value="<%=User.SEX_FEMALE+"" %>" data="女"/></span>
											</hk:radioarea><br/>
											<div id="sex_error" class="error"></div>
										</div>
										<div id="sex_flag" class="flag"></div><div class="clr"></div>
									</td>
								</tr>
								<c:if test="${(inviteId==null && inviterId==null) || (inviteId==0 && inviterId==0)}">
									<tr>
										<td>邀请码</td>
										<td>
											<div class="f_l">
												<hk:text clazz="text_short_1" name="code" value="${regCode.name}"/><br/>
												<span class="ruo s">请通过你身边的朋友和同事获得火酷邀请码</span><br/>
												<div id="regcode_error" class="error"></div>
											</div>
											<div id="regcode_flag" class="flag"></div>
											<div class="clr"></div>
										</td>
									</tr>
								</c:if>
								<tr>
									<td>
									</td>
									<td>
										<div class="hang">
											<div class="f_l">
												<hk:checkbox name="agree" value="1" checkedvalue="${agree}"/>
												同意<hk:a href="/pub/xieyi.do?inviteId=${inviteId}&inviterId=${inviterId }&prouserId=${prouserId}" target="_blank">火酷协议</hk:a><br/>
												<div id="xieyi_error" class="error"></div>
											</div>
											<div id="xieyi_flag" class="flag"></div>
											<div class="clr"></div>
										</div>
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<div align="center"><hk:submit value="注册" clazz="btn size1"/></div>
									</td>
								</tr>
							</table>
							</form>
						</div>
					</div>
				 </div>
				 <div class="reg_right">
				 	<div class="mod">
						<h3>登录</h3>
						<div class="bdbtm"></div>
						<div class="formcon">
							<form id="login_frm" method="post" onsubmit="return subloginfrm(this.id)" action="<%=path %>/login.do" target="hideframe">
								<hk:hide name="pcflg" value="1"/>
								<table class="infotable" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td width="90px">E-mail或手机</td>
										<td>
											<div class="f_l">
												<hk:text name="input" maxlength="50" clazz="text_short_2"/><br/>
											</div>
										</td>
									</tr>
									<tr>
										<td>密码</td>
										<td>
											<div class="f_l">
												<hk:pwd name="password" maxlength="50" clazz="text_short_2"/><br/>
												<div id="login_error" class="error"></div>
											</div>
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<hk:checkbox name="autologin" value="1" data="下次自动登录" checkedvalue="1"/>
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<div class="form_btn" >
											<input type="submit" value="登录" class="btn size1"/> 
											<a href="<%=path %>/fgtpwd2.do">忘记密码</a>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				 </div>
				 <div class="clr"></div>
			</div>
		</div>
		<div class="bg-bottom"><div class="rd"><div class="l"></div><div class="mid"></div><div class="r"></div><div class="clr"></div></div></div>
	</div>
<script type="text/javascript">
var return_url="${denc_return_url}";
<c:if test="${(inviteId==null && inviterId==null) || (inviteId==0 && inviterId==0)}">
var need_input_regcode=true;
</c:if>
function subregfrm(frmid){
	clearRegTipInfo();
	showSubmitDiv(frmid);
	return true;
}
function clearRegTipInfo(){
	setHtml("email_error","");
	setHtml("mobile_error","");
	setHtml("sex_error","");
	setHtml("password_error","");
	setHtml("password1_error","");
	setHtml("xieyi_error","");
	getObj("email_flag").className="flag";
	getObj("mobile_flag").className="flag";
	getObj("sex_flag").className="flag";
	getObj("password_flag").className="flag";
	getObj("password1_flag").className="flag";
	getObj("xieyi_flag").className="flag";
	if(need_input_regcode){
		setHtml("regcode_error","");
		getObj("regcode_flag").className="flag";
	}
	
}
function subloginfrm(frmid){
	$('#login_error').html('');
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(op_func=='login'){
		if(error!=0){
			$('#login_error').html(error_msg);
			hideSubmitDiv();
		}
		else{
			getObj('login_error').className="ok";
			$('#login_error').html('登录成功，页面跳转中... ...');
			hideSubmitDiv();
			if(return_url.length!=""){
				tourl(return_url);
			}
			else{
				tourl("http://<%=HkWebConfig.getWebDomain() %>/home_web.do");
			}
		}
	}
	else{
		if(error==0){//注册成功
			if(return_url.length!=""){
				tourl(return_url);
			}
			else{
				tourl("http://<%=HkWebConfig.getWebDomain() %>/home_web.do");
			}
		}
		else {
			$('#'+obj_id_param+'_error').html(error_msg);
			getObj(obj_id_param+'_flag').className="flag errorflg";
			hideSubmitDiv();
		}
	}
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>