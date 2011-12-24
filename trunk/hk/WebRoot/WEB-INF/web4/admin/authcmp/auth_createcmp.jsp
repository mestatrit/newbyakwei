<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AuthCompany"%>
<%@page import="com.hk.web.util.HkWebUtil"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
HkWebUtil.loadCmpKindInfo(request);
%>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">创建足迹并审核通过</div>
	<div class="mod_content">
		<div class="divrow">
			<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/h4/admin/authcmp_checkokforcreate.do" target="hideframe">
				<hk:hide name="sysId" value="${sysId}"/>
				<hk:hide name="ch" value="1"/>
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td width="90px;" align="right">名称：</td>
						<td>
							<hk:text name="name" clazz="text" maxlength="30" value="${authCompany.name}"/>
						</td>
					</tr>
					<tr>
						<td width="90px;" align="right">分类：</td>
						<td>
							<jsp:include page="../../inc/cmpkindsel.jsp"></jsp:include>
							<div class="infowarn" id="_kindId"></div>
						</td>
					</tr>
					<tr>
						<td width="90px;" align="right">地区：</td>
						<td>
							<jsp:include page="../../inc/zonesel.jsp"></jsp:include>
							<div class="infowarn" id="_cityId"></div>
							<script type="text/javascript">initselected(0);</script>
						</td>
					</tr>
					<tr>
						<td width="90px;" align="right">开启功能：</td>
						<td><%HkWebUtil.loadCmpFuncList(request); %>
							<c:forEach var="func" items="${cmpfunclist}">
								<hk:checkbox name="funcoid" oid="func_${func.oid}" value="${func.oid}"/>
								<label for="func_${func.oid}">${func.name }</label><br/>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td width="90px;" align="right"></td>
						<td>
							<hk:submit clazz="btn split-r" value="审核通过"/>
							<a href="<%=path %>/h4/admin/authcmp.do">返回</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div>
			<c:set var="page_url" scope="request"><%=path%>/h4/admin/authcmp.do?mainStatus=${mainStatus}</c:set>
			<jsp:include page="../../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.COMPANY_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.COMPANY_KIND_ERROR %>={objid:"_kindId"};
var err_code_<%=Err.COMPANY_ZONE_ERROR %>={objid:"_zone"};
function subfrm(frmid){
	showGlass(frmid);
	setHtml('_kindId','');
	return true;
}
function createerr(json,errorlist){
	var errorcode_arr=errorlist.split(',');
	for(var i=0;i<errorcode_arr.length;i++){
		setHtml(getoidparam(errorcode_arr[i]),getmsgfromlist(errorcode_arr[i],json));
	}
	hideGlass();
}
function createok(e,msg,v){
	tourl('<%=path %>/h4/admin/authcmp.do');
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>