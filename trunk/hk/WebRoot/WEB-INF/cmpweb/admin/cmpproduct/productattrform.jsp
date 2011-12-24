<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="productId" value="${productId}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px" align="right">
			</td>
			<td>
				${cmpProduct.name}
			</td>
			<td>
			</td>
		</tr>
		<c:if test="${not empty cmpProductSortAttrObject.attr1Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr1Name }：
				</td>
				<td>
					<hk:select name="attr1" checkedvalue="${cmpProductAttr.attr1}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==1}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty cmpProductSortAttrObject.attr2Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr2Name }：
				</td>
				<td>
					<hk:select name="attr2" checkedvalue="${cmpProductAttr.attr2}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==2}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty cmpProductSortAttrObject.attr3Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr3Name }：
				</td>
				<td>
					<hk:select name="attr3" checkedvalue="${cmpProductAttr.attr3}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==3}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty cmpProductSortAttrObject.attr4Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr4Name }：
				</td>
				<td>
					<hk:select name="attr4" checkedvalue="${cmpProductAttr.attr4}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==4}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty cmpProductSortAttrObject.attr5Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr5Name }：
				</td>
				<td>
					<hk:select name="attr5" checkedvalue="${cmpProductAttr.attr5}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==5}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty cmpProductSortAttrObject.attr6Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr6Name }：
				</td>
				<td>
					<hk:select name="attr6" checkedvalue="${cmpProductAttr.attr6}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==6}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty cmpProductSortAttrObject.attr7Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr7Name }：
				</td>
				<td>
					<hk:select name="attr7" checkedvalue="${cmpProductAttr.attr7}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==7}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty cmpProductSortAttrObject.attr8Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr8Name }：
				</td>
				<td>
					<hk:select name="attr8" checkedvalue="${cmpProductAttr.attr8}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==8}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<c:if test="${not empty cmpProductSortAttrObject.attr9Name}">
			<tr>
				<td width="120px" align="right">
					${cmpProductSortAttrObject.attr9Name }：
				</td>
				<td>
					<hk:select name="attr9" checkedvalue="${cmpProductAttr.attr9}">
						<hk:option value="0" data="请选择"/>
						<c:forEach var="attr" items="${attrlist}">
							<c:if test="${attr.attrflg==9}">
								<hk:option value="${attr.attrId}" data="${attr.name}"/>
							</c:if>
						</c:forEach>
					</hk:select>
				</td>
			</tr>
		</c:if>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/cmpproduct.do?companyId=${companyId }&navoid=${navoid}">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
</script>