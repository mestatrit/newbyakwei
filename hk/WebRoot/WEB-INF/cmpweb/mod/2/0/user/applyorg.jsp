<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.login"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 650px;">
	<div class="mod">
		<div class="content">
			<h1>申请创建招生企业</h1>
			<div class="b">填写以下信息，以便于管理员尽快审核</div>
			<form id="regfrm" method="post" onsubmit="return subregfrm(this.id)" action="<%=path %>/epp/web/user_prvapplyorg.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="ch" value="1"/>
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px" align="right">
							* 企业名称
						</td>
						<td width="260px">
							<input type="text" class="text" maxlength="50" name="orgname" />
						</td>
						<td class="ruo">
							<div id="_orgname" class="infowarn"></div>
						</td>
					</tr>
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
					<tr>
						<td width="100px" align="right">
							 E-mail
						</td>
						<td width="260px">
							<input type="text" class="text" maxlength="50" name="email" />
						</td>
						<td class="ruo">
							<div id="_email" class="infowarn"></div>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<hk:submit value="epp.submit" clazz="btn split-r" res="true"/>
							<a href="<%=path %>/epp/web/user.do?companyId=${companyId}&userId=${loginUser.userId}"><hk:data key="epp.return"/></a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPORG_NAME_ERROR %>={objid:"_orgname"};
var err_code_<%=Err.CMPORGAPPLY_ORGNAME_ERROR %>={objid:"_orgname"};
var err_code_<%=Err.CMPORGAPPLY_USERNAME_ERROR %>={objid:"_userName"};
var err_code_<%=Err.CMPORGAPPLY_TEL_ERROR %>={objid:"_tel"};
var err_code_<%=Err.CMPORGAPPLY_EMAIL_ERROR %>={objid:"_email"};
function subregfrm(frmid){
	setHtml('_orgname','');
	setHtml('_tel','');
	setHtml('_email','');
	setHtml('_userName','');
	showGlass(frmid);
	return true;
}
function applyok(error,error_msg,respValue){
	tourl('<%=path%>/epp/web/user.do?companyId=${companyId}&userId=${loginUser.userId}');
}
function applyerror(error,error_msg,respValue){
	setHtml(getoidparam(error),error_msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>