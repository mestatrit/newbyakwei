<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
<%EppViewUtil.loadCmpModByCompanyId(request); %>
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="blockId" value="${blockId}"/>
	<hk:hide name="pageflg" value="${pageflg}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="150px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpPageBlock.name}"/> 
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				模块：
			</td>
			<td>
				<hk:select name="pageModId" checkedvalue="${cmpPageBlock.pageModId}">
				<hk:option value="0" data="请选择"/>
				<c:forEach var="pmod" items="${cmpPageModList}">
					<hk:option value="${pmod.pageModId}" data="${pmod.name}"/>
				</c:forEach>
				</hk:select>
				<div class="infowarn" id="_pagemodid"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmppageblock.do?companyId=${companyId}&pageflg=${pageflg}">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPPAGEBLOCK_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.CMPPAGEBLOCK_PAGEMODID_ERROR %>={objid:"_pagemodid"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_pagemodid','');
	showGlass(frmid);
	return true;
}
</script>