<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">忘记密码</c:set>
<c:set var="html_body_content" scope="request">
<div>
<form id="frm" method="post" onsubmit="return subsmscodefrm(this.id)" action="<%=path %>/h4/pwd_inputsmscode.do" target="hideframe">
<hk:hide name="mobile" value="${mobile}"/>
<hk:hide name="ch" value="1"/>
<table cellpadding="0" cellspacing="0">
<tr>
	<td width="150px" align="right">
	</td>
	<td><span class="b">输入短信中的验证码</span></td>
</tr>
<tr>
	<td width="150px" align="right">
	</td>
	<td><hk:text name="smscode" maxlength="50" clazz="text"/></td>
</tr>
<tr>
	<td width="150px" align="right"></td>
	<td>
		<div id="info" class="infowarn"></div>
		<hk:submit value="view2.submit" res="true" clazz="btn"/> 
	</td>
</tr>
</table>
</form>
</div>
<script type="text/javascript">
function subsmscodefrm(frmid){
	setHtml('info','');
	showGlass(frmid);
	return true;
}
function smsok(error,error_msg,respValue){
	setHtml('info','');
	hideGlass();
	tourl('<%=path %>/h4/pwd_savenewpwd.do');
}
function smserror(error,error_msg,respValue){
	setHtml('info',error_msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>