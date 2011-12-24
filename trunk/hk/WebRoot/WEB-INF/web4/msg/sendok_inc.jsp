<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<html>
<head></head>
<body>
<ul id="new_chat">
<li id="chat${msg.chatId }" onmouseover="this.className='bg2';" onmouseout="this.className='';">
	<div class="msg-body">
		<div class="user">
			<a href="/user/${msg.senderId }/"><img src="${msg.sender.head48Pic }" title="${msg.sender.nickName}"/><br/>
			${msg.sender.nickName }</a>
		</div>
		<div class="content">
			${msg.spHtml }<br/>
			<span class="ruo2"><fmt:formatDate value="${msg.createTime}" pattern="yy-MM-dd HH:mm"/>
			<a class="ruo2" href="javascript:delchat(${msg.chatId })"><hk:data key="view2.delete"/></a>
			</span>
		</div>
		<div class="clr"></div>
	</div>
</li>
</ul>
<script type="text/javascript">
parent.msgok(document.getElementById("new_chat").innerHTML,'chat${msg.chatId }');
</script>
</body>
</html>