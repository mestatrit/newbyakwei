<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.user.setting"/></c:set>
<c:set var="html_body_content" scope="request">
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/op.css" />
<div class="hcenter" style="width: 800px">
<div class="userop_l">
	<jsp:include page="set_inc.jsp"></jsp:include>
</div>
<div class="userop_r">
<div>
	<form id="infofrm" method="post" onsubmit="return subinfofrm(this.id)" action="<%=path %>/h4/op/user/set_setinfo.do" target="hideframe">
		<hk:hide name="ch" value="1"/>
		<table class="reg nt all" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90px" align="right">
					昵称
				</td>
				<td>
					<hk:text name="nickName" value="${user.nickName}" maxlength="12" clazz="text"/>
				</td>
				<td>
					<div id="_nickname" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					姓名
				</td>
				<td>
					<span class="ruo">可不填写</span><br/>
					<hk:text name="name" value="${info.name}" maxlength="12" clazz="text"/>
				</td>
				<td>
					<div id="_name" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					性别
				</td>
				<td>
					<hk:radioarea name="sex" checkedvalue="${user.sex}">
						<hk:radio value="<%=User.SEX_MALE %>" data="view2.sex.male" res="true"/>
						<hk:radio value="<%=User.SEX_FEMALE %>" data="view2.sex.female" res="true"/>
					</hk:radioarea>
				</td>
				<td><div id="_sex" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td align="right">
					个人介绍
				</td>
				<td><span class="ruo">可不填写</span><br/>
					<hk:textarea name="intro" value="${info.intro}" style="width:265px;height:100px;"/>
				</td>
				<td>
					<div id="_intro" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td><hk:submit value="view2.submit" res="true" clazz="btn"/>
				</td>
				<td>
				</td>
			</tr>
		</table>
	</form>
</div>
</div>
<div class="clr"></div>
</div>
<script type="text/javascript">
var err_code_<%=Err.NICKNAME_DUPLICATE %>={objid:"_nickname"};
var err_code_<%=Err.NICKNAME_ERROR %>={objid:"_nickname"};
var err_code_<%=Err.SEX_ERROR %>={objid:"_sex"};
var err_code_<%=Err.USEROTHERINFO_SEX_ERROR %>={objid:"_sex"};
var err_code_<%=Err.USER_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.USEROTHERINFO_INTRO_LENGTH_TOO_LONG %>={objid:"_intro"};
function subinfofrm(frmid){
	showGlass();
	setHtml('_nickname','');
	setHtml('_sex','');
	setHtml('_name','');
	setHtml('_intro','');
	return true;
}
function editinfook(){
	refreshurl();
}
function editinfoerror2(error,error_msg,respValue){
	setHtml(getoidparam(error),error_msg);
	hideGlass();
}
function editinfoerror(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>