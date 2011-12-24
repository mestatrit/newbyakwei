<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.table.photoset"/></c:set>
<c:set var="mgr_content" scope="request">
<div class="text_14">
<c:if test="${fn:length(list)<5}">
<hk:button value="创建图集" clazz="btn" onclick="tocreateset()"/> 
</c:if>
<br/><br/>
<c:if test="${fn:length(setlist)==0}">
没有创建图集
</c:if>
<c:if test="${fn:length(setlist)>0}">
<table class="infotable" cellpadding="0" cellspacing="0">
<c:forEach var="set" items="${setlist}">
	<tr onmouseover="this.className='bg2';" onmouseout="this.className='';">
		<td width="90px" style="padding:5px 0px;">
		<a href="<%=path %>/e/op/auth/table/photo_photoset.do?companyId=${companyId}&setId=${set.setId}&tableId=${tableId }">
			<c:if test="${not empty set.path}"><img src="${set.pic60 }"/><br/></c:if>
			${set.title }
		</a>
		</td>
		<td width="100px">
			<a id="del${set.setId }" href="javascript:delset(${set.setId })">删除</a>
		</td>
		<c:if test="${tableId>0}">
			<td width="90px"><hk:button value="选中该图集" clazz="btn split-r" onclick="selset(${set.setId})"/></td>
		</c:if>
	</tr>
</c:forEach>
</table>
</c:if>
<hk:page midcount="10" url="/e/op/auth/table/photo_photosetlist.do?companyId=${companyId}&tableId=${tableId }"/>
<div class="clr"></div>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPTABLEPHOTOSET_TITLE_ERROR %>={objid:"title"};
var err_code_<%=Err.CMPTABLEPHOTOSET_INTRO_ERROR%>={objid:"intro"};
function selset(setId){
	tourl("<%=path%>/e/op/auth/table/photo_selset.do?companyId=${companyId}&tableId=${tableId}&setId="+setId);
}
function totable(){
	tourl('<%=path %>/e/op/auth/table.do?companyId=${companyId }');
}
function tocreateset(){
	createBg();
	var html='<hk:form oid="setfrm" onsubmit="return subsetfrm(this.id)" action="/e/op/auth/table/photo_createphotoset.do" target="hideframe"><hk:hide name="companyId" value="${companyId}"/> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="80px">名称</td> <td> <div class="f_l"> <hk:text name="title" clazz="text"/> <div id="title_error" class="error"></div> </div> <div id="title_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td>描述</td> <td> <div class="f_l"> <textarea name="intro" class="text_area"></textarea> <div id="intro_error" class="error"></div> </div> <div id="intro_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td></td> <td align="center"><hk:submit value="提交" clazz="btn"/> </td> </tr> </table> </hk:form>';
	createCenterWindow("photo_win",500,350,'创建图集',html,"hideWindow('photo_win');clearBg();");
}
function subsetfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function oncreatesetsuccess(error,error_msg,respValue){
	tourl("<%=path%>/e/op/auth/table/photo_photoset.do?companyId=${companyId}&setId="+respValue);
}
function oncreateseterror(error,error_msg,respValue){
	validateErr(getoidparam(error), error_msg);
	hideSubmitDiv();
}
function showbig(url){
	var html='<div><img src="'+url+'"/></div>';
	createCenterWindow('img_win',300,350,'',html,"hideWindow('img_win')");
}
function delset(setId){
	if(window.confirm('确实要删除？')){
		showSubmitDivForObj('del'+setId);
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/auth/table/photo_delset.do?companyId=${companyId}&setId='+setId,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set>
<jsp:include page="../../../inc/mgr_inc.jsp"></jsp:include>