<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<%EppViewUtil.loadCmpPageBlock(request); %>
<c:set scope="request" var="title_value">${cmpNav.name }</c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpNav.name }|${o.name}"/>
</c:set>
<c:set scope="request" var="html_body_content">
<form id="vfrm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/epp/web/cmpusertable_createvalue.do" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="dataId" value="${cmpUserTableData.dataId}"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<c:forEach var="field" items="${fieldlist}">
			<tr>
				<td width="100px" align="right">${field.name }</td>
				<td>
					<c:if test="${field.fieldText}">
						<hk:text name="field_${field.fieldId}" clazz="text" maxlength="20"/>
					</c:if>
					<c:if test="${field.fieldTextArea}">
						<hk:textarea name="field_${field.fieldId}" clazz="text" style="width:280px;height:80px"/>
					</c:if>
					<c:if test="${field.fieldSelect}">
						<hk:select name="field_${field.fieldId}">
							<hk:option value="0" data="请选择"/>
							<c:forEach var="option" items="${field.optionList}">
								<hk:option value="${option.optionId}" data="${option.data}"/>
							</c:forEach>
						</hk:select>
					</c:if>
					<c:if test="${field.fieldRadio}">
						<hk:radioarea name="field_${field.fieldId}">
							<c:forEach var="option" items="${field.optionList}">
								<hk:radio oid="radio_${option.optionId}" value="${option.optionId}"/><label for="radio_${option.optionId}">${option.data}</label>
							</c:forEach>
						</hk:radioarea>
					</c:if>
					<c:if test="${field.fieldCheckbox}">
						<c:forEach var="option" items="${field.optionList}">
							<hk:checkbox oid="checkbox_${option.optionId}" name="field_${field.fieldId}" value="${option.optionId}"/><label for="checkbox_${option.optionId}">${option.data}</label>
						</c:forEach>
					</c:if>
					<div class="infowarn" id="_info_field_${field.fieldId }"></div>
				</td>
			</tr>
		</c:forEach>
			<tr>
				<td width="100px" align="right"></td>
				<td width="100px">
					<hk:submit clazz="btn" value="提交"/>
				</td>
			</tr>
	</table>
</form>
<div class="pl">
</div>
<div class="clr"></div>
<script type="text/javascript">
var fieldid=new Array();
<c:forEach var="field" items="${fieldlist}" varStatus="idx">
fieldid[${idx.index }]=${field.fieldId }
</c:forEach>
var glassid=null;
var submited=false;
function subfrm(frmid){
	if(submited){
		return false;
	}
	glassid=addGlass(frmid,false);
	for(var i=0;i<fieldid.length;i++){
		setHtml("_info_field_"+fieldid[i],'');
	}
	submited=true;
	return true;
}
function createerr(err,err_msg,v){
	for(var i=0;i<fieldid.length;i++){
		setHtml("_info_field_"+fieldid[i],'');
		if(fieldid[i]==parseInt(v)){
			setHtml("_info_field_"+fieldid[i],err_msg);
		}
	}
	submited=false;
	removeGlass(glassid);
}
function createok(){
	tourl("<%=path%>/epp/web/cmpusertable_createok.do?companyId=${companyId}&navId=${navId}");
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>