<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">与${main.user2.nickName}的对话录</c:set>
<c:set var="html_body_content" scope="request">
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/msg.css" />
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/jquery-ui-1.7.2.custom.min.js"></script>
<div class="hcenter" style="width: 700px">
	<div class="f_l" style="width: 700px">
		<div id="do" class="active_tips_tab">与${main.user2.nickName}的对话录</div>
		<div id="do" class="inactive_tips_tab"><a href="<%=path %>/h4/op/msg.do">所有对话录</a></div>
		<div class="clr"></div>
		<div id="listbox" class="listbox"><a name="msg_top"></a>
			<ul class="msglist">
				<li>
					<div>
						<form id="msgfrm" method="post" onsubmit="return submsgfrm(this.id)" action="<%=path %>/h4/op/msg_send.do" target="hideframe">
							<input type="hidden" name="mainId" value="${mainId }"/>
							<input type="hidden" name="nickName" value="${main.user2.nickName}"/>
							<table class="nt reg" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<div>
										<strong><hk:data key="view2.send_msg_to"/>${main.user2.nickName}：</strong><br/>
										<textarea onkeydown="submsgfrm2(event)" id="_content" name="msg" style="width: 500px;height: 80px;"/></textarea>
										&nbsp;&nbsp;&nbsp;<span id="numcount" class="numcount">500</span>
										</div>
										<div id="contentwarn" class="infowarn"></div>
									</td>
								</tr>
								<tr>
									<td>
										<div style="padding-left: 420px;"><hk:submit value="view2.submit" res="true" clazz="btn split-r"/></div>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</li>
				<li id="msgline" class="hide"></li>
				<c:forEach var="msg" items="${list}">
					<li id="chat${msg.chatId }" onmouseover="this.className='bg2';" onmouseout="this.className='';">
						<div class="msg-body">
							<div class="user">
								<a href="/user/${msg.senderId }/"><img src="${msg.sender.head48Pic }" title="${msg.sender.nickName}"/><br/>
								${msg.sender.nickName }</a>
							</div>
							<div class="content">
								${msg.spHtml }<br/>
								<span class="ruo2"><fmt:formatDate value="${msg.createTime}" pattern="yy-MM-dd HH:mm"/>
								<a class="del split-r" class="ruo2 split-r" href="javascript:delchat(${msg.chatId })"><hk:data key="view2.delete"/></a>
								<c:if test="${msg.senderId!=loginUser.userId}">
								<a class="del" href="javascript:tosend()" class="ruo2">回复</a>
								</c:if>
								</span>
							</div>
							<div class="clr"></div>
						</div>
					</li>
				</c:forEach>
			</ul>
			<c:set var="page_url" scope="request">
			<%=path%>/h4/op/msg_chat.do?mainId=${mainId}</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			<br/>
			<div style="margin-left: 20px;"><a class="b" href="javascript:delmsg(${mainId })"><hk:data key="view2.delete_msgmain"/></a></div>
			<c:if test="${not_follow}"><hk:data key="228"/></c:if>
		</div>
	</div>
</div>
<script type="text/javascript">
function delchat(id){
	if(window.confirm("确实要删除此消息？")){
		showGlass('chat'+id);
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/msg_delchat.do?chatId="+id,
			cache:false,
			dataType:"html",
			success:function(data){
				hideGlass();
				delObj("chat"+id);
			},
			error:function(data){
				alert("服务器出现错误");
				hideGlass();
			}
		});
	}
}
function delmsg(id){
	if(window.confirm("确实要删整个对话？")){
		showGlass('chat'+id);
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/msg_del.do?mainId="+id,
			cache:false,
			dataType:"html",
			success:function(data){
				tourl("<%=path%>/h4/op/msg.do");
			},
			error:function(data){
				alert("服务器出现错误");
				hideGlass();
			}
		});
	}
}

function updateNumCount() {
	setHtml('numcount',(500 - getObj('_content').value.length));
	setTimeout(updateNumCount, 500);
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
function msgok(v,id){
	insertObjAfter(v, "msgline");
	getObj('_content').value='';
	$('#'+id).effect('highlight',{},1000,function(){});
	hideGlass();
}
function tosend(){
	window.location.hash="msg";
	getObj('_content').focus();
}
updateNumCount();
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>