<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="选择收信人 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:a href="/msg/pvtlist.do">我收到的私信</hk:a>|发私信</div>
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
	<c:forEach var="u" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<tr class="${clazz_var }">
			<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${u.head32Pic }"/></td></c:if>
			<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${u.head48Pic }"/></td></c:if>
		<td><hk:a href="/msg/send_tosend2.do?receiverId=${u.userId }">${u.nickName }</hk:a></td></tr>
	</c:forEach>
	</tbody></table>
	<hk:simplepage clazz="page" href="/msg/send_tosend.do?nickName=${enc_nickName}"/>
	<div class="hang">
		<hk:form method="get" action="/msg/send_tosend.do">
			昵称:<hk:text name="nickName" value="${nickName}" maxlength="10"/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>