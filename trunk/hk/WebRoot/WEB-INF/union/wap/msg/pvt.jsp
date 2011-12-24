<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">与${main.user2.nickName}的对话录</c:set>
<c:set var="html_main_content" scope="request">
<div class="nav2">与<hk:a href="/union/home.do?userId=${main.user2.userId}&uid=${uid }">${main.user2.nickName}</hk:a>|<hk:a href="/union/op/msg/pvtlist.do?uid=${uid }">所有对话录</hk:a>|<hk:a href="/msg/send_tosend.do?uid=${uid }">发送私信</hk:a></div>
	<c:if test="${fn:length(volist)>0}">
		<div class="row" onkeydown="submitMsg(event)">
			<hk:form name="msgfrm" action="/union/op/msg/send.do">
				<hk:hide name="uid" value="${uid}"/>
				<hk:hide name="receiverId" value="${main.user2Id}"/>
				<hk:textarea name="msg" rows="2" cols="30" value="${msg}"/><br/>
				<hk:submit name="msg_submit" value="发送私信"/> 
			</hk:form>
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
					<td class="h0"><img src="${vo.pvtChat.sender.head32Pic }"/></td>
					<td>
						<hk:a href="/union/home.do?uid=${uid }&userId=${vo.pvtChat.sender.userId}">${vo.pvtChat.sender.nickName}</hk:a> 
						<span class="ruo s"><fmt:formatDate value="${vo.pvtChat.createTime}" pattern="yyyy-MM-dd HH:mm"/></span><br/>
						${vo.pvtChat.msg } 
						<hk:a href="/union/op/msg/pvt_delchat.do?chatId=${vo.pvtChat.chatId}&mainId=${mainId }&from=pvt&uid=${uid }" page="true">删</hk:a>
					</td>
				</tr>
			</c:forEach>
		</tbody></table>
		<div class="blk"></div>
		<div class="row"><hk:a href="/union/op/msg/chat.do?mainId=${main.mainId}&f=1&uid=${uid }">更多</hk:a></div>
		<div class="row"><hk:a href="/union/op/msg/pvt_del.do?mainId=${main.mainId}&uid=${uid }">删除本对话录</hk:a></div>
	</c:if>
	<c:if test="${fn:length(main.msgList)==0}">没有私信数据显示</c:if>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>