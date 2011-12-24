<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="已发布物品列表 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">已发布物品列表</div>
	<div class="blk"></div>
	<table class="list" cellpadding="0" cellspacing="0"><tbody>
		<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
			<tr class="${clazz_var }"><td>${p.name} ${p.pcount} <hk:a href="/box/op/op_delPrize.do?boxId=${boxId}&prizeId=${p.prizeId }">删</hk:a></td></tr>
		</c:forEach>
	</tbody></table>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>