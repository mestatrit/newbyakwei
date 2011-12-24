<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">重设密码</c:set>
<c:set var="html_body_content" scope="request">
<div>
<form id="frm" method="post" onsubmit="return subpwdfrm(this.id)" action="<%=path %>/h4/pwd_savenewpwd.do" target="hideframe">
<hk:hide name="v" value="${v}"/>
<hk:hide name="ch" value="1"/>
<table cellpadding="0" cellspacing="0">
<tr>
	<td width="150px" align="right">
	</td>
	<td><span class="b">重设密码</span></td>
</tr>
<tr>
	<td align="right">
		<hk:data key="view2.password"/>
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
		<hk:data key="view2.repassword"/>
	</td>
	<td>
		<input type="password" class="text" maxlength="20" name="repassword" />
	</td>
	<td class="s"><div id="_repassword" class="infowarn"></div>
	</td>
</tr>
<tr>
	<td align="right">
	</td>
	<td>
	<hk:submit clazz="btn" value="view2.submit" res="true"/>
	</td>
	<td class="s">
	</td>
</tr>
</table>
</form>
</div>
<script type="text/javascript">
var err_code_<%=Err.PASSWORD_DATA_ERROR %>={objid:"_password"};
var err_code_<%=Err.REG_2_PASSWORD_NOT_SAME %>={objid:"_repassword"};
function subpwdfrm(frmid){
	setHtml('_password','');
	setHtml('_repassword','');
	showGlass(frmid);
	if(getObj('frm').password.value!=getObj('frm').repassword.value){
		setHtml('_repassword','<hk:data key="174"/>');
		hideGlass();
		return false;
	}
	return true;
}
function pwdok(error,error_msg,respValue){
	hideGlass();
	tourl('/login');
}
function pwderror(error,error_msg,respValue){
	setHtml(getoidparam(error),error_msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>