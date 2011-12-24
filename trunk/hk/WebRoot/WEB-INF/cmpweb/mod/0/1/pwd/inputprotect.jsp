<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="title_value" scope="request">输入密码保护答案</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 500px;">
<form id="frm" method="post" onsubmit="return subprotectfrm(this.id)" action="<%=path %>/epp/web/pwd_inputprotect.do" target="hideframe">
<hk:hide name="ch" value="1"/>
<hk:hide name="companyId" value="${companyId}"/>
<hk:hide name="userId" value="${userId}"/>
<div class="mod">
	<div class="mod_title">输入密码保护答案</div>
	<div class="mod_content">
		${question.name }<br/>
		<hk:text name="pvalue" clazz="text"/>
		<hk:submit value="epp.submit" res="true" clazz="btn"/> <br/>
		<div id="info" class="infowarn"></div>
	</div>
</div>
</form>
</div>
<script type="text/javascript">
function subprotectfrm(frmid){
	setHtml('info','');
	showGlass(frmid);
	return true;
}
function pok(error,error_msg,respValue){
	hideGlass();
	tourl('<%=path %>/epp/web/pwd_savenewpwd.do?companyId=${companyId}');
}
function perror(error,error_msg,respValue){
	setHtml('info',error_msg);
	hideGlass();
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>