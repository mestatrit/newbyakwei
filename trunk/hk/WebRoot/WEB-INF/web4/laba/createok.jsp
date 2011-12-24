<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<html>
<title></title>
<body>
<ul id="newcontent">
<li id="cmt${cmtvo.labaCmt.cmtId }">
	<div class="cmtuser">
		<a href="/user/${cmtvo.labaCmt.userId}/"><img src="${cmtvo.labaCmt.user.head32Pic }"/></a>
	</div>
	<div class="cmtcontent">
		<a href="<%=path %>/home_web.do?userId=${cmtvo.labaCmt.userId}">${cmtvo.labaCmt.user.nickName }</a>：<br/>
		${cmtvo.content } 
		<span class="ruo s"><fmt:formatDate value="${cmtvo.labaCmt.createTime}" pattern="yy-MM-dd HH:mm"/></span>
		<div class="cmtact">
			<a class="func" href="javascript:dellabacmt(${labaId },${cmtvo.labaCmt.cmtId })">删除</a>
			<a class="func" href="javascript:replylabacmt(${labaId },${cmtvo.labaCmt.cmtId },'${cmtvo.labaCmt.user.nickName }')">回复</a>
		</div>
	</div>
	<div class="clr"></div>
</li>
</ul>
<script type="text/javascript">
parent.loadnewcmt(${labaId},document.getElementById("newcontent").innerHTML);
</script>
</body>
</html>