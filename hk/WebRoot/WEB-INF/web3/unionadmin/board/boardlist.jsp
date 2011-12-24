<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">新闻管理</c:set>
<c:set var="mgr_content" scope="request">
<div>
	<hk:button value="发布新闻" clazz="btn2" onclick="tocreate();"/><br/><br/>
	<ul class="orderlist">
		<li class="bg1">
			<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td width="400px">标题</td>
				<td width="150px">创建时间</td>
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
			<c:forEach var="b" items="${list}">
				<li onmouseover="this.className='bg1';" onmouseout="this.className='';">
					<table class="infotable" cellpadding="0" cellspacing="0">
					<tr>
						<td width="400px"><a href="<%=path %>/cmpunion/op/board_board.do?uid=${uid}&boardId=${b.boardId}">${b.title }</a></td>
						<td width="150px">
							<fmt:formatDate var="date" value="${b.createTime }" pattern="yy-MM-dd HH:mm"/>
							${date }
						</td>
						<td>
							<a id="edit${b.boardId }" href="javascript:toedit(${b.boardId })">修改</a>
							/
							<a id="del${b.boardId }" href="javascript:del(${b.boardId })">删除</a>
						</td>
					</tr>
					</table>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div>
		<hk:page midcount="10" url="/cmpunion/op/board.do?uid=${uid}"/>
		<div class="clr"></div>
	</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPUNIONBOARD_TITLE_ERROR %>={objid:"title"};
var err_code_<%=Err.CMPUNIONBOARD_CONTENT_ERROR %>={objid:"content"};
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
function tocreate(){
	var html='<hk:form oid="editfrm" onsubmit="return subboardfrm(this.id)" action="/cmpunion/op/board_create.do" target="hideframe"> <hk:hide name="uid" value="${uid}"/> <table> <tr> <td width="90px">标题</td> <td> <div class="f_l"> <hk:text name="title" clazz="text" value="${o.title}"/> <div id="title_error" class="error"></div> </div> <div class="clr"></div> </td> </tr> <tr> <td>内容</td> <td> <div class="f_l"> <textarea name="content" class="text_areabig"></textarea> <div id="content_error" class="error"></div> </div> <div class="clr"></div> </td> </tr> <tr> <td></td> <td align="center"> <hk:submit value="提交" clazz="btn"/> </td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow("board_win",500,500,'发布新闻',html,"hideWindow('board_win');clearBg();");
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
				refreshurl();
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