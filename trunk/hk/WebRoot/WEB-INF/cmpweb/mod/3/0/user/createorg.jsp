<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="epp.login"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width:650px">
	<div class="mod">
		<div class="content">
			<form id="regfrm" method="post" onsubmit="return subregfrm(this.id)" action="<%=path %>/epp/web/user_createorg.do" target="hideframe">
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
function subregfrm(frmid){
	setHtml('_orgname','');
	showGlass(frmid);
	return true;
}
function createok(error,error_msg,respValue){
	tourl('<%=path%>/epp/web/user.do?companyId=${companyId}&userId=${loginUser.userId}');
}
function createerror(error,error_msg,respValue){
	setHtml(getoidparam(error),error_msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>