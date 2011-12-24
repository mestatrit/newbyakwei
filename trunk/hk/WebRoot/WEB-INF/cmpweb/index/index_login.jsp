<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
	<div class="hcenter" style="width: 500px">
		<form method="post" action="<%=path %>/epp/login_web.do" target="hideframe">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="ch" value="1"/>
			<hk:hide name="foradmin" value="1"/>
			<table class="nt" cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding: 7px;" width="140" align="right">
					</td>
					<td><strong>登录进行网站管理</strong>
					</td>
				</tr>
				<tr>
					<td style="padding: 7px;" width="140" align="right">E-mail或手机号：
					</td>
					<td><input type="text" name="input" class="login_text" />
					</td>
				</tr>
				<tr>
					<td style="padding: 7px;" align="right">密码：
					</td>
					<td><input type="password" name="password" class="login_text" />
					<div class="infowarn" id="loginerr_msg"></div>
					</td>
				</tr>
				<tr>
					<td style="padding: 7px;">
					</td>
					<td><input type="submit" value="登录" class="split-r btn" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
function subloginfrm(frmid){
	setHtml('loginerr_msg','');
	return true;
}
function loginerror(error,msg,v){
	setHtml('loginerr_msg',msg);
}
function loginok(error,msg,v){
	tourl('<%=path%>/epp/web/op/webadmin/admincmpnav_index.do?companyId=${companyId}');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>