<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="iteraotrmsg">
	<c:forEach var="vo" items="${volist}"><c:set var="chatId" value="${vo.pvtChat.chatId}"></c:set>
		<li id="msg${chatId }" class="pvt" onmouseout="this.className='pvt';" onmouseover="this.className='pvt show';">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="head">
						<a href="<%=path %>/home_web.do?userId=${vo.pvtChat.senderId }"><img src="${vo.pvtChat.sender.head48Pic }" title="${vo.pvtChat.sender.nickName}"/><br/>${vo.pvtChat.sender.nickName}</a>
					</td>
					<td class="content content-all">
						<div class="msg-body">
							${vo.pvtChat.spHtml } 
							<span class="text_12">
							<c:if test="${vo.pvtChat.smsSend}"><a class="s" href="<%=path %>/msg/chat_web.do?mainId=${mainId }&sms=1&simplestate=${vo.userSms.sendState }">(<hk:data key="view.sms.simplestate${vo.userSms.sendState}"/>)</a>
							<c:if test="${vo.userSms.sendFail}"><a href="#"><hk:data key="view.resendsms"/></a></c:if>
							</c:if>
							</span>
							<div class="ruo"><fmt:formatDate value="${vo.pvtChat.createTime}"/></div>
						</div>
						<ul id="msgaction${chatId }" class="action">
							<li>
								<a href="#send" class="reply">回复</a>
							</li>
							<li>
								<a href="javascript:delchat(${chatId })" class="delete">删除</a>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</li>
	</c:forEach>
</c:set>
<ul class="msglist" id="msglist">
	<c:if test="${fn:length(volist)>0}">${iteraotrmsg }</c:if>
</ul>
<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
<div class="msgform" onkeydown="keydown(event)"><a name="send"></a>
	<span class="text_14">至 ${main.user2.nickName }<br/></span>
	<hk:form oid="msgfrm" target="hideframe" onsubmit="return submsgfrm(this.id)" action="/msg/send_sendweb.do">
		<hk:hide name="mainId" value="${mainId}"/>
		<hk:hide name="sendtype" value="2"/>
		<hk:hide name="receiverId" value="${main.user2Id}"/>
		<div><textarea name="msg"></textarea></div>
		<div>
		<div id="msg_error" class="error"></div>
		<div class="clr"></div>
		</div>
		<input type="submit" value="发送私信" class="btn"/>
		<input type="submit" name="msgandsms_submit" value="发送短信" class="btn"/>
	</hk:form>
</div>
<script type="text/javascript">
function keydown(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(submsgfrm("msgfrm")){
			getObj("msgfrm").submit();
		}
	}
}
function submsgfrm(frmid){
	validateClear("msg");
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!="0"){
		validateErr("msg",error_msg);
		hideSubmitDiv();
	}
	else{
		tourl('<%=path %>/msg/chat_web.do?mainId=${mainId}');
	}
}
function getNewMsg(content){
	var o=$('#msglist li:eq(0)');
	insertObjBefore(content,o.id);
}
function replymsg(mainId){
	tourl("<%=path %>/msg/");
}
function delchat(chatId){
	if(!window.confirm("确实要删除此信息?")){
		return;
	}
	var h=getHtml("msgaction"+chatId);
	setopt(chatId);
	setHtml("msgaction"+chatId,'<li>操作提交中 ... ...</li>');
	setopt(chatId);
	$.ajax({
		type:"POST",
		url:"<%=path%>/msg/pvt_delchatweb.do?chatId="+chatId,
		cache:false,
		dataType:"html",
		success:function(data){
			delObj("msg"+chatId);
		},
		error:function(data){
			alert("操作出现错误");
			clearopt(chatId);
			setHtml("msgaction"+chatId,h);
		}
	});
}
function setopt(chatId){
	getObj("msg"+chatId).className="pvt opt";
	getObj("msg"+chatId).onmouseover="";
	getObj("msg"+chatId).onmouseout="";
}
function clearopt(chatId){
	getObj("msg"+chatId).className="pvt";
	getObj("msg"+chatId).onmouseout=lionmouseout;
	getObj("msg"+chatId).onmouseover=lionmouseover;
}
function lionmouseout(obj){
	this.className='pvt';
}
function lionmouseover(obj){
	this.className='pvt show';
}
</script>