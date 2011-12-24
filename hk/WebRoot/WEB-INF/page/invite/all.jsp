<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="邀请记录 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:a href="/invite/invite.do">成功的邀请</hk:a>|<hk:a href="/invite/invite_all.do" clazz="nn">邀请记录</hk:a></div>
	<c:if test="${user!=null}">
		<div class="hang">
		<span class="orange">您邀请的好友已经在火酷注册:</span>
		<hk:a href="/home.do?userId=${user.userId}">${user.nickName}</hk:a>
		</div>
	</c:if>
	<c:if test="${fn:length(list)==0}">没有邀请记录显示</c:if>
	<c:if test="${fn:length(list)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
		<c:forEach var="i" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }"><td>
			<c:if test="${i.friendId>0}">
				<hk:a href="/home.do?userId=${i.friend.userId}">${i.friend.nickName}</hk:a>
			</c:if>
			<c:if test="${i.friendId==0}">
				${i.email } <hk:a href="/invite/invite_resend.do?inviteId=${i.inviteId}" page="true">重发</hk:a>
			</c:if>
			</td></tr>
		</c:forEach>
		</tbody></table>
		<hk:simplepage href="/invite/invite_all.do" />
	</c:if>
	<div class="hang"><hk:a domain="<%=HkWebConfig.getWebDomain() %>" href="/invite/invite_toinvite.do">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>