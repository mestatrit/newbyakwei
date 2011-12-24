<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="未开宝箱 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">未开宝箱</div>
	<div class="hang">
		<hk:form method="get" action="/box/admin/adminbox_unopenboxlist.do">
			<hk:text name="name" value="${name}" maxlength="11"/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<table class="list" cellpadding="0" cellspacing="0"><tbody>
		<c:forEach var="b" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<tr class="${clazz_var }"><td>
				<hk:a href="/box/op/op_getbox.do?boxId=${b.boxId}&t=4&fromadmin=1" page="true">
					${b.name} &nbsp;&nbsp;${b.totalCount-b.openCount}/${b.totalCount}
				</hk:a>
			</td></tr>
		</c:forEach>
		</tbody></table>
		<hk:simplepage href="/box/admin/adminbox_unopenboxlist.do" />
	</c:if>
	<c:if test="${fn:length(list)==0}">没有数据显示</c:if>
	<div class="hang"><hk:a href="/box/admin/adminbox_admin.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>