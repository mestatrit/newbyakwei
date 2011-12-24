<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="发送私信 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:a href="/msg/pvtlist.do">我收到的私信</hk:a>|发私信</div>
	<div class="hang" onkeydown="submitMsg(event)">
		<hk:form name="msgfrm" action="/msg/send.do">
		<hk:hide name="from" value="${from}"/>
		<hk:hide name="receiverId" value="${receiverId}"/>
			至 <hk:a href="/home.do?userId=${receiver.userId}">${receiver.nickName }</hk:a><br/>
			<hk:textarea name="msg" rows="3" cols="30" value="${msg}"/><br/>
			<hk:submit name="msg_submit" value="发送私信"/> 
			<hk:submit name="msgandsms_submit" value="发送短信"/> 
			
		</hk:form>
		<script type="text/javascript">
			function submitMsg(event){
				if((event.ctrlKey)&&(event.keyCode==13)){
					document.msgfrm.submit();
				}
			}
		</script>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>