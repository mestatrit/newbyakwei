<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<table class="infotable cellinfo" cellpadding="0" cellspacing="0">
	<tr class="tr-title">
		<th width="200px">名称</th>
		<th width="100px">单价</th>
		<th width="100px">数量</th>
		<th width="225px"></th>
	</tr>
	<c:forEach var="item" items="${itemlist}">
		<tr class="tr-line" onmouseout="this.className='tr-line';" onmouseover="this.className='tr-line bg2';">
			<td>${item.name }</td>
			<td>￥${item.price }</td>
			<td>${item.pcount }份</td>
			<td>
				<c:if test="${item.pcount>1 }"><a class="split-r" href="javascript:decreaseitem(${item.itemId })">减少一份</a></c:if>
				<a href="javascript:delitem(${item.itemId })">删除</a>
				<span id="itemtip${item.itemId }"></span>
			</td>
		</tr>
	</c:forEach>
</table>