<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">
	与${main.user2.nickName}的对话录
</c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l">
				<jsp:include page="../inc/userleftnav_inc.jsp"></jsp:include>
			</td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod">
						<c:set var="nav_2_short_content" scope="request">
							<a href="<%=path%>/msg/chat_web.do?mainId=${mainId }" class="nav-a">与${main.user2.nickName}的对话录</a>
						</c:set>
						<jsp:include page="../inc/nav-2-short-msg.jsp"></jsp:include>
					</div>
					<div class="mod">
						<div class="text_14"><a href="<%=path %>/msg/send_tosendweb.do"><hk:data key="view.msg.sendmsg.title"/></a></div>
						<c:set var="page_url" scope="request"><%=path%>/msg/chat_web.do?mainId=${mainId }</c:set>
						<jsp:include page="chat_inc.jsp"></jsp:include>
					</div>
				</div>
			</td>
			<td class="r">
				<div class="f_r">
				</div>
			</td>
		</tr>
	</table>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>