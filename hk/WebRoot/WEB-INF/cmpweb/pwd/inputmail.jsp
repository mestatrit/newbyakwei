<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.forgotpwd"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 500px">
	<div class="mod">
		<div class="mod_title"><hk:data key="epp.forgotpwd"/></div>
		<div class="mod_content">
			<form id="frm" method="post" onsubmit="return submailfrm(this.id)" action="<%=path %>/epp/web/pwd.do" target="hideframe">
			<hk:hide name="ch" value="1"/><hk:hide name="companyId" value="${companyId}"/>
			<table class="nt all " cellpadding="0" cellspacing="0">
			<tr>
				<td><hk:data key="epp.forgotpwd.inputmail"/>：
				<hk:text name="email" maxlength="50" clazz="text"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<div id="info" class="infowarn"></div>
					<hk:submit value="epp.submit" res="true" clazz="btn"/> 
				</td>
			</tr>
			</table>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
function submailfrm(frmid){
	setHtml('info','');
	showGlass(frmid);
	return true;
}
function pwdok(error,error_msg,respValue){
	setHtml('info','<hk:data key="epp.forgotpwd.sendmail.success"/>');
	hideGlass();
	tourl('<%=path %>/epp/web/pwd_mailok.do?companyId=${companyId}&email='+encodeURL(getObj('frm').email.value));
}
function pwdok2(error,error_msg,respValue){
	tourl('<%=path%>/epp/web/pwd_inputprotect.do?companyId=${companyId}&userId='+respValue);
}
function pwderror(error,error_msg,respValue){
	setHtml('info',error_msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>