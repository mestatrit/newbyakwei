<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="list" cellpadding="0" cellspacing="0">
<c:forEach var="vo" items="${cmptipvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
<tr class="${clazz_var }">
<c:if test="${!noneedhead}">
	<c:if test="${showMode.showImg && !showMode.showBigImg}"><td class="h0"><img src="${vo.cmpTip.user.head32Pic }"/></td></c:if>
	<c:if test="${showMode.showBigImg}"><td class="h1"><img src="${vo.cmpTip.user.head48Pic }"/></td></c:if>
</c:if>
<td>
	<hk:a href="/home.do?userId=${vo.cmpTip.userId}">${vo.cmpTip.user.nickName}</hk:a>
	<span class="s ruo"><fmt:formatDate value="${vo.cmpTip.createTime}" pattern="yy-MM-dd HH:mm"/></span>
	<br/>
	${vo.cmpTip.content } 
	<hk:a clazz="line s ruo" href="/e/cmp_item.do?tipId=${vo.cmpTip.tipId}">回应</hk:a> 
</td>
</tr>
</c:forEach>
</table>