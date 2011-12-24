<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">修改用户预约数量</div>
	<div class="mod_content">
	<br/>
	<form id="sfrm" method="post" onsubmit="return subvenuefrm(this.id)" action="<%=path %>/h4/op/venue/cmp_updatesvrrate.do" target="hideframe">
		<hk:hide name="companyId" value="${companyId}"/>
		<hk:hide name="ch" value="1"/>
		用户可创建的未处理预约数量：
		<c:if test="${cmpOtherInfo!=null && cmpOtherInfo.svrrate>0}">
			<c:set var="svrrate" value="${cmpOtherInfo.svrrate}"/>
		</c:if>
		<c:if test="${cmpOtherInfo==null || cmpOtherInfo.svrrate==0}">
			<c:set var="svrrate" value="1"/>
		</c:if>
		<hk:text name="svrrate" value="${svrrate}" maxlength="5" clazz="text2"/>
		<hk:submit clazz="btn split-r" value="view2.submit" res="true"/>
		<div class="infowarn" id="_svrrate"></div>
	</form>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPOTHERINFO_SVRRATE_ERR %>={objid:"_svrrate"};
function subvenuefrm(frmid){
	showGlass(frmid);
	setHtml('_svrrate','');
	return true;
}
function updateerr(error,error_msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,error_msg,v){
	refreshurl();
}
</script>
</c:set>
<jsp:include page="mgr.jsp"></jsp:include>