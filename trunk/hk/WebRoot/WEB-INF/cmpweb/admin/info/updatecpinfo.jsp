<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">修改版权和许可信息</div>
		<div class="mod_content">
			<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/epp/web/op/webadmin/info_updatecpinfo.do" target="hideframe">
				<hk:hide name="ch" value="1"/>
				<hk:hide name="companyId" value="${companyId}"/>
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td width="150" align="right">
							版权和许可信息：
						</td>
						<td>
							<hk:textarea name="cpinfo" clazz="text" value="${cmpInfo.cpinfo}" style="width:400px;height:100px;"/><br/>
							<div class="infowarn" id="_cpinfo"></div>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<hk:submit clazz="btn split-r" value="提交"/> 
						</td>
						<td></td>
					</tr>
				</table>
			</form>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPINFO_CPINFO_ERROR %>={objid:"_cpinfo"};
function subfrm(frmid){
	setHtml('_cpinfo','');
	showGlass(frmid);
	return true;
}
function updateerror(error,msg,v){
	setHtml(getoidparam(error),msg);hideGlass();
}
function updateok(error,msg,v){
	refreshurl();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>