<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="待审宝箱 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">待审宝箱</div>
	<c:if test="${fn:length(list)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
			<c:forEach var="b" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
				<tr class="${clazz_var }"><td><hk:a href="/box/op/op_getbox.do?boxId=${b.boxId}&t=1" page="true">${b.name}</hk:a></td></tr>
			</c:forEach>
		</tbody></table>
		<hk:simplepage href="/box/op/op_preboxlist.do"/>
	</c:if>
	<c:if test="${fn:length(list)==0}">没有数据显示</c:if>
	<div class="hang"><hk:a href="/box/op/op_admin.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>