<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">友情链接管理</c:set>
<c:set var="mgr_content" scope="request">
<div>
	<hk:button value="创建友情链接" clazz="btn2" onclick="tocreate();"/><br/><br/>
	<ul class="orderlist">
		<li class="bg1">
			<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td width="200px">名称</td>
				<td width="300px">地址</td>
				<td></td>
			</tr>
			</table>
		</li>
		<c:if test="${fn:length(list)==0}">
			<li>
				<div class="heavy" align="center"><hk:data key="nodataview"/></div>
			</li>
		</c:if>
		<c:if test="${fn:length(list)>0}">
			<c:forEach var="l" items="${list}">
				<li onmouseover="this.className='bg1';" onmouseout="this.className='';">
					<table class="infotable" cellpadding="0" cellspacing="0">
					<tr>
						<td width="200px">${l.title }</td>
						<td width="300px">
							<a href="${l.url }" target="_blank">${l.url }</a>
						</td>
						<td>
							<a id="edit${l.linkId }" href="javascript:toedit(${l.linkId })">修改</a>
							/
							<a id="del${l.linkId }" href="javascript:del(${l.linkId })">删除</a>
						</td>
					</tr>
					</table>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div>
		<hk:page midcount="10" url="/cmpunion/op/link.do?uid=${uid}"/>
		<div class="clr"></div>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPUNIONLINK_TITLE_ERROR %>={objid:"title"};
var err_code_<%=Err.CMPUNIONLINK_URL_ERROR %>={objid:"url"};
function sublinkfrm(frmid){
	showSubmitDiv(frmid);
	validateClear("title");
	validateClear("url");
	return true;
}
function linkok(error,error_msg,respValue){
	refreshurl();
}
function linkerror(error,error_msg,respValue){
	validateErr(getoidparam(error), error_msg);
	hideSubmitDiv();
}
function tocreate(){
	var html='<hk:form oid="editfrm" onsubmit="return sublinkfrm(this.id)" action="/cmpunion/op/link_create.do" target="hideframe"> <hk:hide name="uid" value="${uid}"/> <table> <tr> <td width="90px">名称</td> <td> <div class="f_l"> <hk:text name="title" clazz="text"/> <div id="title_error" class="error"></div> </div> <div class="clr"></div> </td> </tr> <tr> <td>链接地址</td> <td> <div class="f_l"> <hk:text name="url" clazz="text" value="http://"/> <div id="url_error" class="error"></div> </div> <div class="clr"></div> </td> </tr> <tr> <td></td> <td align="center"> <hk:submit value="提交" clazz="btn"/> </td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow("link_win",500,260,'创建友情链接',html,"hideWindow('link_win');clearBg();");
}
function del(id){
	if(window.confirm("确实要删除？")){
		showSubmitDivForObj("del"+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/cmpunion/op/link_del.do?uid=${uid}&linkId='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function toedit(id){
	showSubmitDivForObj("edit"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/cmpunion/op/link_loadlink.do?uid=${uid}&linkId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("table_update_win",500,260,'修改链接',data,"hideWindow('table_update_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
</script>
</c:set>
<jsp:include page="../mgr_inc.jsp"></jsp:include>