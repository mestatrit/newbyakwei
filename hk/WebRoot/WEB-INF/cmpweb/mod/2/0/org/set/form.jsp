<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="articlefrm" method="post" enctype="multipart/form-data" onsubmit="return subfrm(this.id)" action="${cmpnav_form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="orgId" value="${orgId}"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				图片：
				<input type="file" name="f" size="50"/>
				<div class="b">图片最合适宽度为980px 高度为110px</div>
				<div class="infowarn" id="_info"></div>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">
				<hk:submit clazz="btn split-r" value="提交"/>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.UPLOAD_ERROR %>={objid:"_info"};
var err_code_<%=Err.CMPORGARTICLE_CONTENT_ERROR %>={objid:"_info"};
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
</script>