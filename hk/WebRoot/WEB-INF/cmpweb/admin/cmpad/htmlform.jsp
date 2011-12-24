<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="adid" value="${adid}"/>
	<hk:hide name="htmlflg" value="1"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="150px" align="right">
				广告名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${cmpAd.name}"/> 
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				广告代码：
			</td>
			<td>
				<hk:textarea name="html" clazz="text" value="${cmpAd.html}" style="width:400px;height:200px;"/> 
				<div class="infowarn" id="_html"></div>
			</td>
		</tr>
		<c:if test="${o.cmpEdu}">
			<tr>
			<td width="90px" align="right"></td>
			<td><hk:checkbox oid="_refflg" name="refflg" value="1" checkedvalue="${refflg}" /><label for="_refflg">推荐到二级页面左侧空白区</label><br/>
			二级栏目中宽度为200px
			</td>
			</tr>
		</c:if>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/>
				<c:if test="${blockId>0}">
				<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_content.do?companyId=${companyId}&blockId=${blockId}">返回</a>
				</c:if> 
				<c:if test="${!(blockId>0)}">
				<a href="<%=path %>/epp/web/op/webadmin/cmpad.do?companyId=${companyId}">返回</a>
				</c:if> 
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPAD_HTML_ERROR %>={objid:"_html"};
var err_code_<%=Err.CMPAD_NAME_ERROR%>={objid:"_name"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_html','');
	showGlass(frmid);
	return true;
}
</script>