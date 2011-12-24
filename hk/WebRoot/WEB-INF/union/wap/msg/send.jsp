<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">选择收信人</c:set>
<c:set var="html_main_content" scope="request">
<div class="nav2"><hk:a href="/union/op/msg/pvtlist.do?uid=${uid }">我收到的私信</hk:a>|发私信</div>
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<c:forEach var="u" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<tr class="${clazz_var }">
		<td class="h0"><img src="${u.head32Pic }"/></td>
		<td><hk:a href="/union/op/msg/send_tosend2.do?uid=${uid }&receiverId=${u.userId }">${u.nickName }</hk:a></td></tr>
	</c:forEach>
	</tbody></table>
	<hk:simplepage2 clazz="page" href="/union/op/msg/send_tosend.do?nickName=${enc_nickName}&uid=${uid }"/>
	<div class="row">
		<hk:form method="get" action="/union/op/msg/send_tosend.do">
			昵称:<hk:text name="nickName" value="${nickName}" maxlength="10"/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>