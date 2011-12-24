<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="sortId" value="${sortId}"/>
	<hk:hide name="parentId" value="${parentId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<c:if test="${parent!=null}">
			<tr>
				<td width="90px" align="right">
					上级分类
				</td>
				<td>
					${parent.name }
				</td>
				<td>
				</td>
			</tr>
		</c:if>
		<tr>
			<td width="90px" align="right">
				分类名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpProductSort.name}"/> 
			</td>
			<td>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmpproduct_kindlist.do?companyId=${companyId}&parentId=${parentId}">返回</a>
			</td>
			<td>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPPRODUCTSORT_NAME_DUPLICATE %>={objid:"_name"};
var err_code_<%=Err.CMPPRODUCTSORT_NAME_ERROR %>={objid:"_name"};
function subfrm(frmid){
	setHtml('_name','');
	showGlass(frmid);
	return true;
}
</script>