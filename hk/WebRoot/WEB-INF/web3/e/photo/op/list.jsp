<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.photo.mgr"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
<hk:button value="上传图片" clazz="btn" onclick="toupload()"/><br/><br/>
<table class="infotable" cellpadding="0" cellspacing="0">
<c:forEach var="p" items="${list}">
	<tr>
		<td width="90px" style="padding:5px 0px;"><a href="javascript:showbig('${p.pic240 }','${p.name }')"><img src="${p.pic60 }"/></a></td>
		<td>
			<c:if test="${!(headPath eq p.path)}"><a href="javascript:settop(${p.photoId })">设为头图</a>/</c:if>
			<c:if test="${p.pink}"><a href="javascript:delpink(${p.photoId })">取消精华</a></c:if>
			<c:if test="${!p.pink}"><a href="javascript:setpink(${p.photoId })">加精华</a></c:if>
			/
			<a href="javascript:delphoto(${p.photoId })">删除</a>
		</td>
	</tr>
</c:forEach>
</table>
</div>
<script type="text/javascript">
function toupload(){
	tourl("<%=path%>/e/op/photo/photo_mgr.do?companyId=${companyId}");
}
function showbig(url,name){
	var html='<div><img src="'+url+'"/></div>';
	createCenterWindow('img_win',300,300,name,html,"hideWindow('img_win')");
}
function delphoto(pid){
	if(window.confirm('确实要删除？')){
		tourl('<%=path%>/e/op/photo/photo_delweb.do?companyId=${companyId}&photoId='+pid);
	}
}
function settop(pid){
	tourl('<%=path%>/e/op/photo/photo_setheadweb.do?companyId=${companyId}&photoId='+pid);
}
function setpink(pid){
	tourl('<%=path%>/e/op/photo/photo_setpinkflg.do?companyId=${companyId}&photoId='+pid);
}
function delpink(pid){
	tourl('<%=path%>/e/op/photo/photo_delpinkflg.do?companyId=${companyId}&photoId='+pid);
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>