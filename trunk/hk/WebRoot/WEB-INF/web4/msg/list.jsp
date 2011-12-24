<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">私信对话录</c:set>
<c:set var="html_body_content" scope="request">
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/msg.css" />
<div class="hcenter" style="width: 700px">
	<div class="f_l" style="width: 700px">
		<div id="do" class="active_tips_tab"><a href="#">所有对话录</a></div>
		<div class="clr"></div>
		<div id="listbox" class="listbox">
			<ul class="msglist">
				<li>
					<div>
						<form id="msgfrm" method="post" onsubmit="return submsgfrm(this.id)" action="<%=path %>/h4/op/msg_send2.do" target="hideframe">
							<input type="hidden" name="mainId" value="${mainId }"/>
							<table class="nt reg" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<div>
										<strong><hk:data key="view2.send_msg_to"/>：</strong> <hk:text name="nickName" clazz="text"/> <span class="ruo2">填写收信人昵称</span>
										<textarea onkeydown="submsgfrm2(event)" id="_content" name="msg" style="width: 500px;height: 80px;"/></textarea>
										&nbsp;&nbsp;&nbsp;<span id="numcount" class="numcount">500</span>
										</div>
										<div id="contentwarn" class="infowarn"></div>
									</td>
								</tr>
								<tr>
									<td>
										<div style="padding-left: 420px;"><hk:submit value="view2.submit" res="true" clazz="btn split-r"/>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</li>
			<c:if test="${fn:length(list)==0}">
				<div style="padding-left: 20px;font-size: 16px;font-weight: bold;">
					<hk:data key="view2.nomsglist"/>
				</div>
			</c:if>
			<c:if test="${fn:length(list)>0}">
				<c:forEach var="msg" items="${list}">
					<li id="main${msg.mainId }" class="<c:if test="${msg.newMsg}">unread</c:if>" onmouseover="this.className='bg2';" onmouseout="this.className='<c:if test="${msg.newMsg}">unread</c:if>';">
						<div class="msg-body <c:if test="${msg.newMsg}">body-unread</c:if>">
							<div class="user">
								<a href="/user/${msg.user2Id }/"><img src="${msg.user2.head48Pic }" title="${msg.user2.nickName}"/><br/>
								${msg.user2.nickName }</a>
							</div>
							<div class="content" style="cursor: pointer;" onclick="viewchat(${msg.mainId })">
								${msg.msg } 
								<br/>
								<span class="ruo"><fmt:formatDate value="${msg.createTime}" pattern="yy-MM-dd HH:mm"/></span>
							</div>
							<div class="del_act">
								<div style="margin-bottom: 20px">
									<a href="javascript:viewchat(${msg.mainId });">回复</a><br/>
								</div>
								<a class="del" href="javascript:delmsg(${msg.mainId })"><hk:data key="view2.delete"/></a>
							</div>
							<div class="clr"></div>
						</div>
					</li>
				</c:forEach>
			</c:if>
			</ul>
			<c:set var="page_url" scope="request"><%=path%>/h4/op/msg.do?v=1</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<script type="text/javascript">
function viewchat(id){
	tourl("<%=path %>/h4/op/msg_chat.do?mainId="+id);
}
function delmsg(id){
	if(window.confirm("确实要删整个对话？")){
		showGlass('main'+id);
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/msg_del.do?mainId="+id,
			cache:false,
			dataType:"html",
			success:function(data){
				delObj("main"+id);
				hideGlass();
			},
			error:function(data){
				alert("服务器出现错误");
				hideGlass();
			}
		});
	}
}
function submsgfrm(frmid){
	setHtml('contentwarn','');
	showGlass(frmid);
	return true;
}
function submsgfrm2(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(submsgfrm("msgfrm")){
			getObj("msgfrm").submit();
		}
	}
}
function msgerror(error,msg,v){
	setHtml('contentwarn',msg);
	hideGlass();
}
function msgok(error,msg,v){
	tourl("<%=path %>/h4/op/msg_chat.do?mainId="+v);
}
function updateNumCount() {
	setHtml('numcount',(500 - getObj('_content').value.length));
	setTimeout(updateNumCount, 500);
}
updateNumCount();
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>