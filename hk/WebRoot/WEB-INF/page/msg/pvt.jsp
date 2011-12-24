<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="与${main.user2.nickName}的对话录 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">与<hk:a href="/home.do?userId=${main.user2.userId}">${main.user2.nickName}</hk:a>|<hk:a href="/msg/pvtlist.do">所有对话录</hk:a>|<hk:a href="/msg/send_tosend.do">发送私信</hk:a></div>
	<c:if test="${fn:length(volist)>0}">
		<div class="hang" onkeydown="submitMsg(event)">
			<hk:form name="msgfrm" action="/msg/send.do">
				<hk:hide name="receiverId" value="${main.user2Id}"/>
				<hk:textarea name="msg" rows="2" cols="30" value="${msg}"/><br/>
				<hk:submit name="msg_submit" value="发送私信"/> 
				<c:if test="${canSms}"><hk:submit name="msgandsms_submit" value="发送短信" /></c:if>
			</hk:form>
			<c:if test="${notFriend}"><br/>
				<c:set var="follow_url" value="/follow/op/op_add.do?userId=${main.user2Id}"/>
				<hk:data key="view.msg.pvtandchat_jsp_notfriendtip" arg0="${follow_url}"/>
			</c:if>
			<script type="text/javascript">
			function submitMsg(event){
				if((event.ctrlKey)&&(event.keyCode==13)){
					document.msgfrm.submit();
				}
			}
			</script>
		</div>
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="vo" items="${volist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
				<tr class="${clazz_var}">
					<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.pvtChat.sender.head32Pic }"/></td></c:if>
					<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.pvtChat.sender.head48Pic }"/></td></c:if>
					<td>
						<hk:a href="/home.do?userId=${vo.pvtChat.sender.userId}">${vo.pvtChat.sender.nickName}</hk:a> 
						<span class="ruo s"><fmt:formatDate value="${vo.pvtChat.createTime}" pattern="yyyy-MM-dd HH:mm"/></span><br/>
						${vo.pvtChat.spHtml } 
						<c:if test="${vo.pvtChat.smsSend}"><hk:a clazz="s" href="/msg/chat.do?mainId=${mainId }&sms=1&simplestate=${vo.userSms.sendState }">(<hk:data key="view.sms.simplestate${vo.userSms.sendState}"/> <c:if test="${vo.userSms.sendFail}"><hk:a href="/msg/send_resendsms.do?mainId=${mainId}&smsmsgId=${vo.userSms.msgId }"><hk:data key="view.resendsms"/></hk:a></c:if>)</hk:a></c:if> 
						<hk:a href="/msg/pvt_delchat.do?chatId=${vo.pvtChat.chatId}&mainId=${mainId }&from=pvt" page="true">删</hk:a>
					</td>
				</tr>
			</c:forEach>
		</tbody></table>
		<div class="blk"></div>
		<div class="hang"><hk:a href="/msg/chat.do?mainId=${main.mainId}&f=1">更多</hk:a></div>
		<div class="hang"><hk:a href="/msg/pvt_del.do?mainId=${main.mainId}">删除本对话录</hk:a></div>
	</c:if>
	<c:if test="${fn:length(main.msgList)==0}">没有私信数据显示</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>