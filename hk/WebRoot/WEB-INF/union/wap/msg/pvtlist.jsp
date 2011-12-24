<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">私信对话录</c:set>
<c:set var="html_main_content" scope="request">
	<div class="nav2"><hk:a href="/union/op/msg/pvtlist.do?uid=${uid }">所有对话录</hk:a>|<hk:a href="/union/op/msg/send_tosend.do?uid=${uid }">发送私信</hk:a></div>
	<c:if test="${fn:length(list)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
		<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${p.newMsg}"><c:set var="clazz_var" value="reply" /></c:if>
			<tr class="${clazz_var }"><td>
				与<hk:a clazz="n" href="/union/home.do?uid=${uid }&userId=${p.user2.userId}">${p.user2.nickName}</hk:a>的对话录 <span><fmt:formatDate value="${p.createTime}" pattern="yy-MM-dd HH:mm"/></span><br/> 
				 ${p.msg } 
				 <c:if test="${p.noReadCount>0}"> <span style="font-size:xx-large;color:#138">${p.noReadCount}</span>个未读</c:if> 
				 <hk:a clazz="s" href="/union/op/msg/pvt.do?mainId=${p.mainId}&uid=${uid }">展开</hk:a> 
			</td></tr>
		</c:forEach>
		</tbody></table>
		<hk:simplepage2 clazz="page" href="/union/op/msg/pvtlist.do"/>
	</c:if>
	<c:if test="${fn:length(list)==0}">
		没有数据显示
	</c:if>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>