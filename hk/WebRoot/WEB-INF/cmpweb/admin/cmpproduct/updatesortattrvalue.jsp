<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpJoinInApply"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">分类属性值管理
		</div>
		<div class="mod_content">
			<div class="divrow">
			<form method="post" action="<%=path %>/epp/web/op/webadmin/cmpproduct_updatesortattrvalue.do" target="hideframe">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="sortId" value="${sortId}"/>
				<hk:hide name="attrId" value="${attrId}"/>
				<hk:hide name="ch" value="1"/>
				<table class="nt all" cellpadding="0" cellspacing="0">
					<tr>
						<td width="90px" align="right">
							名称
						</td>
						<td>
							<hk:text name="name" clazz="text" value="${cmpProductSortAttr.name }"/>
							<div class="infowarn" id="msg"></div>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td width="90px" align="right">
						</td>
						<td>
							<hk:submit value="提交" clazz="btn split-r"/>
							<a href="<%=path%>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=${attrflg}">返回</a>
						</td>
						<td>
						</td>
					</tr>
				</table>
			</form>
			</div>
		</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function subfrm(frmid){
	setHtml('msg','');
	showGlass(frmid);
	return true;
}
function updateerror(error,msg,v){
	setHtml('msg',msg);
	hideGlass();
}
function updateok(error,msg,v){
	tourl('<%=path%>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=${attrflg}');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>