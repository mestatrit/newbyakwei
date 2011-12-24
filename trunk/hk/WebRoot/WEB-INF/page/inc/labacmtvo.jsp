<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${fn:length(labacmtvolist)>0}">
	<table class="list" cellpadding="0" cellspacing="0">
		<c:forEach var="vo" items="${labacmtvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }">
				<c:if test="${!noneedhead}">
					<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.labaCmt.user.head32Pic }"/></td></c:if>
					<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.labaCmt.user.head48Pic }"/></td></c:if>
				</c:if>
				<td>
					<hk:a href="/home.do?userId=${vo.labaCmt.userId}">${vo.labaCmt.user.nickName}</hk:a> <c:if test="${!showMode.showImg}">&nbsp;</c:if>
					<c:if test="${loginUser.userId==vo.labaCmt.userId}">
						<hk:a page="true" clazz="line" href="/op/labacmt_del.do?cmtId=${vo.labaCmt.cmtId}&labaId=${labaId }"><%=HkWebUtil.getDelLabel(request) %></hk:a> 
					</c:if>
					<c:if test="${loginUser.userId!=vo.labaCmt.userId}">
						<hk:a clazz="line" href="/op/labacmt_tocreate.do?labaId=${labaId}&cmtId=${vo.labaCmt.cmtId }">回复</hk:a> 
					</c:if>
					<br/>
					${vo.content } 
					<span class="s ruo"><fmt:formatDate value="${vo.labaCmt.createTime}" pattern="yy-MM-dd HH:mm"/></span>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>