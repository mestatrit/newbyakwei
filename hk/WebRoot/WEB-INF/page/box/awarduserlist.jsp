<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="${box.name}开箱记录 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">${box.name}开箱记录</div>
	<div class="hang even">
	<hk:rmBlankLines rm="true">
	<hk:a href="/box/box_awarduserlist.do?boxId=${boxId}&repage=${repage }" clazz="nn">中奖</hk:a>|
	<hk:a href="/box/box_userlist.do?boxId=${boxId}&repage=${repage }">所有</hk:a>
	</hk:rmBlankLines>
	</div>
	<c:if test="${fn:length(list)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="b" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }">
				<td>
					<c:if test="${b.userId==serviceUserId}">${b.mobile }</c:if>
					<c:if test="${b.userId!=serviceUserId}">
						<c:set var="mobile" value="${b.user.userOtherInfo.mobile}" />
						<c:set var="mobileBind" value="${b.user.userOtherInfo.mobileBind}" />
						<hk:a href="/home.do?userId=${b.userId}">${b.user.nickName}<c:if test="${mobileBind==1}">(${mobile })</c:if></hk:a>
					</c:if> 
				${b.boxPrize.name }
				</td>
			</tr>
			</c:forEach>
		</tbody></table>
	</c:if>
	<c:if test="${fn:length(list)==0}">没有数据显示</c:if>
	<hk:simplepage href="/box/box_awarduserlist.do?boxId=${boxId}" returndata="返回" returnhref="/box/box_list.do?page=${repage}"/>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>