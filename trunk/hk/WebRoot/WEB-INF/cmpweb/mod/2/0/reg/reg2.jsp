<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.login"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter divrow" style="width:750px">
	<div class="mod">
		<div class="mod_content">
			<h1><span class="f"><hk:data key="epp.cmporg.reg"/></span></h1>
			<br/>
			<div>请如实填写以下信息，以便于网站管理人员与您联系</div>
			<form id="regfrm" method="post" onsubmit="return subregfrm(this.id)" action="<%=path %>/epp/web/user_reg.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="ch" value="1"/>
				<hk:hide name="orgflg" value="1"/>
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px" align="right">
							* 机构名称
						</td>
						<td width="260px">
							<input type="text" class="text" maxlength="50" name="orgname" />
						</td>
						<td class="ruo">
							<div id="_orgname" class="infowarn"></div>
						</td>
					</tr>
					<c:if test="${cmpOtherWebInfo.orgNeedCheck}">
						<tr>
							<td width="100px" align="right">
								* 姓名
							</td>
							<td width="260px">
								<input type="text" class="text" maxlength="20" name="userName" />
							</td>
							<td class="ruo">
								<div id="_userName" class="infowarn"></div>
							</td>
						</tr>
						<tr>
							<td width="100px" align="right">
								* 联系电话
							</td>
							<td width="260px">
								<input type="text" class="text" maxlength="20" name="tel" />
							</td>
							<td class="ruo">
								<div id="_tel" class="infowarn"></div>
							</td>
						</tr>
					</c:if>
					<tr>
						<td width="100px" align="right">
							* <hk:data key="epp.nickname"/>
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
							* <hk:data key="epp.email"/>
						</td>
						<td width="260px">
							<input type="text" class="text" maxlength="50" name="email" />
						</td>
						<td class="s"><div id="_email" class="infowarn"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							<hk:data key="epp.mobile"/>
						</td>
						<td>
							<input type="text" class="text" maxlength="20" name="mobile" />
						</td>
						<td class="s"><div id="_mobile" class="infowarn"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							* <hk:data key="epp.password"/>
						</td>
						<td>
							<input type="password" class="text" maxlength="20" name="password" />
						</td>
						<td class="s ruo">
							<hk:data key="epp.password.tip"/>
							<div id="_password" class="infowarn"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							* <hk:data key="epp.repassword"/>
						</td>
						<td>
							<input type="password" class="text" maxlength="20" name="repassword" />
						</td>
						<td class="s"><div id="_repassword" class="infowarn"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							* <hk:data key="epp.yourzone"/>
						</td>
						<td>
							<input type="text" class="text" maxlength="20" name="zoneName" value="${city.city }" />
						</td>
						<td class="s"><div id="_zoneName" class="infowarn"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							* <hk:data key="epp.sex"/>
						</td>
						<td>
							<input id="sex_<%=User.SEX_MALE %>" type="radio" name="sex" value="<%=User.SEX_MALE %>" />
							<label for="sex_<%=User.SEX_MALE %>"><hk:data key="epp.sex.male"/></label>
							<input id="sex_<%=User.SEX_FEMALE %>" type="radio" name="sex" value="<%=User.SEX_FEMALE %>" />
							<label for="sex_<%=User.SEX_FEMALE %>"><hk:data key="epp.sex.female"/></label>
						</td>
						<td class="s"><div id="_sex" class="infowarn"></div>
						</td>
					</tr>
					<tr>
						<td align="right">
							* <hk:data key="epp.validatecode"/>
						</td>
						<td>
							<input type="text" name="code" class="text" maxlength="4" size="4" style="width: 50px"/>
							<img id="codeimg" style="vertical-align: middle;" alt="" src="<%=path %>/index_showImg.do?v=<%=Math.random() %>"/>
							<a href="javascript:showimg()"><hk:data key="epp.getnewvalidatecode"/></a>
						</td>
						<td class="s"><div id="_code" class="infowarn"></div>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<br />
							<hk:submit value="epp.submit" clazz="btn" res="true"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
var return_url="${denc_return_url }";
var err_code_<%=Err.REG_INPUT_VALIDATECODE %>={objid:"_code"};
var err_code_<%=Err.NICKNAME_DUPLICATE %>={objid:"_nickname"};
var err_code_<%=Err.NICKNAME_ERROR %>={objid:"_nickname"};
var err_code_<%=Err.NICKNAME_ERROR2 %>={objid:"_nickname"};
var err_code_<%=Err.PASSWORD_DATA_ERROR %>={objid:"_password"};
var err_code_<%=Err.REG_2_PASSWORD_NOT_SAME %>={objid:"_repassword"};
var err_code_<%=Err.MOBILE_OR_EMAIL_INPUT %>={objid:"_email"};
var err_code_<%=Err.EMAIL_ERROR %>={objid:"_email"};
var err_code_<%=Err.MOBILE_ERROR %>={objid:"_mobile"};
var err_code_<%=Err.SEX_ERROR %>={objid:"_sex"};
var err_code_<%=Err.EPP_EMAIL_ALREADY_EXIST %>={objid:"_email"};
var err_code_<%=Err.MOBILE_ALREADY_EXIST %>={objid:"_mobile"};
var err_code_<%=Err.CMPORG_NAME_ERROR %>={objid:"_orgname"};
var err_code_<%=Err.CMPORGAPPLY_ORGNAME_ERROR %>={objid:"_orgname"};
var err_code_<%=Err.CMPORGAPPLY_USERNAME_ERROR %>={objid:"_userName"};
var err_code_<%=Err.CMPORGAPPLY_TEL_ERROR %>={objid:"_tel"};
function subregfrm(frmid){
	setHtml('_orgname','');
	if(getObj('_userName')!=null){
		setHtml('_userName','');
	}
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
	<c:if test="${!cmpOtherWebInfo.orgNeedCheck}">
	tourl('/edu/${companyId}/'+respValue);
	</c:if>
	<c:if test="${cmpOtherWebInfo.orgNeedCheck}">
	tourl('http://<%=request.getServerName() %>');
	</c:if>
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
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>