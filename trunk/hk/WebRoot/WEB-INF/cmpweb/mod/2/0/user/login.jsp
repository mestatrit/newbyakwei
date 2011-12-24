<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.login"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 500px">
	<div class="mod">
		<div class=""><h2><hk:data key="epp.login"/></h2></div>
		<div class="content">
			<div class="divrow">
				<form id="loginfrm" method="post" onsubmit="return subloginfrm(this.id)" action="<%=path %>/epp/login_web.do" target="hideframe">
					<hk:hide name="companyId" value="${companyId}"/><hk:hide name="ch" value="1"/>
					<table cellpadding="0" cellspacing="0" class="nt all">
						<tr>
							<td align="right" width="120px">
							<hk:data key="epp.login.input"/>：
							</td>
							<td>
								<input type="text" name="input" class="text" />
							</td>
						</tr>
						<tr>
							<td align="right">
							<hk:data key="epp.login.pwd"/>：
							</td>
							<td>
								<input type="password" name="password" class="text" />
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td>
								<div class="infowarn" id="loginerr_msg"></div>
								<hk:submit value="epp.login" res="true" clazz="split-r btn"/>
								<a href="<%=path %>/epp/web/pwd.do?companyId=${companyId}"><hk:data key="epp.forgotpwd"/></a>
								<br />
								<a class="split-r" href="<%=path %>/epp/web/user_reg.do?companyId=${companyId}"><hk:data key="epp.gotoreg"/></a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function subloginfrm(frmid){
	setHtml('loginerr_msg','');
	showGlass(frmid);
	return true;
}
function loginerror(error,msg,v){
	setHtml('loginerr_msg',msg);
	hideGlass();
}
function loginok(error,msg,v){
	tourl("http://<%=request.getServerName() %>");
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>