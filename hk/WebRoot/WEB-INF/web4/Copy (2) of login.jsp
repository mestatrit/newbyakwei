<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.login"/> - <hk:data key="view2.welcome"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
<form id="loginfrm" method="post" onsubmit="return subloginfrm(this.id)" action="/login" target="hideframe">
<hk:hide name="ch" value="1"/>
<table cellpadding="0" cellspacing="0">
<tr>
	<td width="150px" align="right">
		<hk:data key="view2.login.input"/>
	</td>
	<td><hk:text name="input" maxlength="50" clazz="text" value="${input}"/></td>
</tr>
<tr>
	<td width="150px" align="right">
		<hk:data key="view2.password"/>
	</td>
	<td><hk:pwd name="password" maxlength="50" clazz="text"/></td>
</tr>
<tr>
	<td width="150px" align="right"></td>
	<td>
		<div id="logininfo" class="infowarn"></div>
		<hk:submit value="view2.login" res="true" clazz="btn"/> 
		<a class="split-r" href="/signup"><hk:data key="view2.nothaveid_pleasesignup"/></a> 
		<a href="<%=path %>/h4/pwd.do">忘记密码</a>
	</td>
</tr>
</table>
</form>
</div>
<script type="text/javascript">
function subloginfrm(frmid){
	setHtml('logininfo','');
	showGlass(frmid);
	return true;
}
function loginok(error,error_msg,respValue){
	tourl('<%=path%>/h4/login_checkip.do?selpcityid=1&return_url=${return_url}');
}
function loginerror(error,error_msg,respValue){
	setHtml('logininfo',error_msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="inc/frame.jsp"></jsp:include>