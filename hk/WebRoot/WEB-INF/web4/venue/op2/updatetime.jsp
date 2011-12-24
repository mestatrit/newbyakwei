<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">修改营业时间</div>
	<div class="mod_content">
	<br/>
	<form id="sfrm" method="post" onsubmit="return subvenuefrm(this.id)" action="<%=path %>/h4/op/venue/cmp_updatetime.do" target="hideframe">
		<hk:hide name="companyId" value="${companyId}"/>
		<hk:hide name="ch" value="1"/>
		<table class="nt reg" cellpadding="0" cellspacing="0">
			<tr>
				<td width="260px;">
					<c:if test="${not empty cmpOtherInfo.beginTime}">
						<c:set var="begintime" value="${cmpOtherInfo.beginTime}"></c:set>
					</c:if>
					<c:if test="${empty cmpOtherInfo.beginTime}">
						<c:set var="begintime" value="09:00"></c:set>
					</c:if>
					<c:if test="${not empty cmpOtherInfo.endTime}">
						<c:set var="endtime" value="${cmpOtherInfo.endTime}"></c:set>
					</c:if>
					<c:if test="${empty cmpOtherInfo.endTime}">
						<c:set var="endtime" value="22:00"></c:set>
					</c:if>
					<div class="b">营业时间只支持正点和半点，例（9:00，9:30）</div>
					<div class="divrow">
						开始时间：<hk:text name="begin" value="${begintime}" maxlength="5" clazz="text2"/> 格式为HH:mm<br/>
					</div>
					<div class="divrow">
						结束时间：<hk:text name="end" value="${endtime}" maxlength="5" clazz="text2"/> 格式为HH:mm<br/>
					</div>
					<div id="info" class="infowarn"></div>
				</td>
			</tr>
			<tr>
				<td>
					<div style="margin-left: 100px">
						<hk:submit clazz="btn split-r" value="view2.submit" res="true"/>
					</div>
				</td>
			</tr>
		</table>
	</form>
	</div>
</div>
<script type="text/javascript">
function subvenuefrm(frmid){
	showGlass(frmid);
	setHtml('info','');
	return true;
}
function updateerr(error,error_msg,v){
	setHtml('info',error_msg);
	hideGlass();
}
function updateok(error,error_msg,v){
	refreshurl();
}
</script>
</c:set>
<jsp:include page="mgr.jsp"></jsp:include>