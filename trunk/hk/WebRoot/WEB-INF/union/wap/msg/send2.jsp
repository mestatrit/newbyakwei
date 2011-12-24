<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">发送私信 </c:set>
<c:set var="html_main_content" scope="request">
<div class="nav2"><hk:a href="/union/op/msg/pvtlist.do?uid=${uid }">我收到的私信</hk:a>|发私信</div>
	<div class="row" onkeydown="submitMsg(event)">
		<hk:form name="msgfrm" action="/union/op/msg/send.do">
			<hk:hide name="uid" value="${uid}"/>
			<hk:hide name="receiverId" value="${receiverId}"/>
			至 <hk:a href="/union/home.do?uid=${uid }&userId=${receiver.userId}">${receiver.nickName }</hk:a><br/>
			<hk:textarea name="msg" rows="3" cols="30" value="${msg}"/><br/>
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
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>