<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="parent_cid" value="${parent_cid}"/>
	<hk:hide name="cid" value="${cid}"/>
	<c:if test="${parent!=null}"><div>在分类 <span class="b">${parent.name }</span> 中创建</div></c:if>
	<table class="formt" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px" align="right">名称</td>
			<td>
				<input name="name" value="${tbAskCat.name }" class="text"/>
				<div class="infowarn" id="_info"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right"></td>
			<td>
			<hk:submit clazz="btn split-r" value="创建"/>
			<a href="${denc_return_url }">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
function subfrm(frmid){
	setHtml('_info','');
	showGlass(frmid);
	return true;
}
</script>