<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">${o.title } - 新闻管理</c:set>
<c:set var="mgr_content" scope="request">
<div>
	<div class="pad">${o.title }</div>
	<div class="pad">${o.content }</div>
	<div>
		<hk:button oid="edit${o.boardId}" clazz="btn split-r" value="修改" onclick="toedit()"/>
		<hk:button oid="del${o.boardId}" clazz="btn" value="删除 " onclick="del()"/>
		<hk:button clazz="btn" value="返回" onclick="tolist()"/>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPUNIONBOARD_TITLE_ERROR %>={objid:"title"};
var err_code_<%=Err.CMPUNIONBOARD_CONTENT_ERROR %>={objid:"content"};
function tolist(){
	tourl("<%=path%>/cmpunion/op/board.do?uid=${uid}");
}
function subboardfrm(frmid){
	showSubmitDiv(frmid);
	validateClear("title");
	validateClear("content");
	return true;
}
function boardok(error,error_msg,respValue){
	refreshurl();
}
function boarderror(error,error_msg,respValue){
	validateErr(getoidparam(error), error_msg);
	hideSubmitDiv();
}
function del(id){
	if(window.confirm("确实要删除？")){
		showSubmitDivForObj("del"+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/cmpunion/op/board_del.do?uid=${uid}&boardId='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				tourl("<%=path%>/cmpunion/op/board.do?uid=${uid}");
			}
		});
	}
}
function toedit(id){
	showSubmitDivForObj("edit"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/cmpunion/op/board_loadboard.do?uid=${uid}&boardId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("table_update_win",500,500,'修改新闻',data,"hideWindow('table_update_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
</script>
</c:set>
<jsp:include page="../mgr_inc.jsp"></jsp:include>