<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="sortId" value="${sortId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px" align="right">
				分类名称：
			</td>
			<td>
				${cmpProductSort.name}
			</td>
			<td>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称1：
			</td>
			<td>
				<hk:text name="attr1Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr1Name}"/> 
				<hk:checkbox oid="attr1" value="true" name="attr1Pink" checkedvalue="${cmpProductSortAttrObject.attr1Pink}"/>
				<label for="attr1">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr1Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=1">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr1name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称2：
			</td>
			<td>
				<hk:text name="attr2Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr2Name}"/> 
				<hk:checkbox oid="attr2" value="true" name="attr2Pink" checkedvalue="${cmpProductSortAttrObject.attr2Pink}"/>
				<label for="attr2">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr2Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=2">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr2name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称3：
			</td>
			<td>
				<hk:text name="attr3Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr3Name}"/> 
				<hk:checkbox oid="attr3" value="true" name="attr3Pink" checkedvalue="${cmpProductSortAttrObject.attr3Pink}"/>
				<label for="attr3">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr3Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=3">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr3name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称4：
			</td>
			<td>
				<hk:text name="attr4Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr4Name}"/> 
				<hk:checkbox oid="attr4" value="true" name="attr4Pink" checkedvalue="${cmpProductSortAttrObject.attr4Pink}"/>
				<label for="attr4">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr4Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=4">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr4name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称5：
			</td>
			<td>
				<hk:text name="attr5Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr5Name}"/> 
				<hk:checkbox oid="attr5" value="true" name="attr5Pink" checkedvalue="${cmpProductSortAttrObject.attr5Pink}"/>
				<label for="attr5">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr5Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=5">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr5name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称6：
			</td>
			<td>
				<hk:text name="attr6Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr6Name}"/> 
				<hk:checkbox oid="attr6" value="true" name="attr6Pink" checkedvalue="${cmpProductSortAttrObject.attr6Pink}"/>
				<label for="attr6">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr6Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=6">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr6name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称7：
			</td>
			<td>
				<hk:text name="attr7Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr7Name}"/> 
				<hk:checkbox oid="attr7" value="true" name="attr7Pink" checkedvalue="${cmpProductSortAttrObject.attr7Pink}"/>
				<label for="attr7">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr7Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=7">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr7name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称8：
			</td>
			<td>
				<hk:text name="attr8Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr8Name}"/> 
				<hk:checkbox oid="attr8" value="true" name="attr8Pink" checkedvalue="${cmpProductSortAttrObject.attr8Pink}"/>
				<label for="attr8">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr8Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=8">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr8name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				属性名称9：
			</td>
			<td>
				<hk:text name="attr9Name" maxlength="20" clazz="text" value="${cmpProductSortAttrObject.attr9Name}"/> 
				<hk:checkbox oid="attr9" value="true" name="attr9Pink" checkedvalue="${cmpProductSortAttrObject.attr9Pink}"/>
				<label for="attr9">推荐</label>
				<c:if test="${not empty cmpProductSortAttrObject.attr9Name}">
				<div><a href="<%=path %>/epp/web/op/webadmin/cmpproduct_sortattrvalue.do?companyId=${companyId}&sortId=${sortId}&parentId=${parentId}&attrflg=9">属性值管理</a></div>
				</c:if>
			</td>
			<td>
				<div class="infowarn" id="_attr9name"></div>
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
function subfrm(frmid){
	for(var i=1;i<=9;i++){
		setHtml('_attr'+i+'name','');
	}
	showGlass(frmid);
	return true;
}
</script>