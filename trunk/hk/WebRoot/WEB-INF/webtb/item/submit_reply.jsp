<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%>
<ul id="data">
<li id="reply_${tbItemCmtReply.replyid }">
	<div class="reply_head">
		<a href="/p/${tbItemCmtReply.userid }"><img src="${tbItemCmtReply.tbUser.pic_url_48 }"/></a>
	</div>
	<div class="reply_body">
		<a href="/p/${tbItemCmtReply.userid }">${tbItemCmtReply.tbUser.show_nick }</a>：
		${tbItemCmtReply.content }
		<span class="split-l ruo"><fmt:formatDate value="${tbItemCmtReply.create_time}" pattern="yy-MM-dd HH:mm"/></span>
		<div align="right">
			<c:if test="${login_user.userid==tbItemCmtReply.userid}"><a class="split-r" href="javascript:delreply(${tbItemCmtReply.replyid })">删除</a></c:if>
			<c:if test="${login_user.userid!=tbItemCmtReply.userid}"><a class="split-r" href="javascript:replyuser(${tbItemCmtReply.userid },'${tbItemCmtReply.tbUser.show_nick }')">回复</a></c:if>
		</div>
	</div>
</li>
</ul>
<script type="text/javascript">
parent.putnewreply(document.getElementById('data').innerHTML,${cmtid});
</script>