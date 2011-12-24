<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${empty odd}"><c:set var="odd" value="odd"/></c:if>
<c:if test="${empty even}"><c:set var="even" value="even"/></c:if>
<c:if test="${fn:length(uservolist)>0}">
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
		<c:forEach var="vo" items="${uservolist}" varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="${odd}" /></c:if>
		<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="${even}" /></c:if>
			<tr class="${clazz_var }">
				<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.user.head32Pic }"/></td></c:if>
				<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.user.head48Pic }"/></td></c:if>
				<td>
					<hk:a href="/home.do?userId=${vo.user.userId}">${vo.user.nickName}</hk:a> 
					<c:if test="${uservobuilder.needFansCount && u.followedCount>0}"><span class="s">${u.followedCount }个粉丝</span> </c:if>
					<c:if test="${loginUser!=null && vo.user.userId!=loginUser.userId}">
						<c:if test="${vo.follow}">
						<hk:a name="user${vo.user.userId}" clazz="s" href="/follow/op/op_del.do?userId=${vo.user.userId}&${addStr }" page="true">取消关注</hk:a>
						</c:if>
						<c:if test="${!vo.follow}">
						<hk:a name="user${vo.user.userId}" clazz="s" href="/follow/op/op_add.do?userId=${vo.user.userId}&${addStr }" page="true">马上关注</hk:a>
						</c:if>
					</c:if>
					<c:if test="${uservobuilder.needLaba}"><br/><span class="s">${vo.labaVo.content }</span></c:if>
				</td>
			</tr>
		</c:forEach>
		<c:remove var="hk_request_css" scope="request"/>
	</tbody></table>
</c:if>