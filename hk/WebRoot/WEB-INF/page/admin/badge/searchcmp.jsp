<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="查询足迹 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">查询足迹<br/>
	<hk:form method="get" action="/admin/badge_searchcmp.do">
		<hk:hide name="ch" value="1"/>
		<hk:hide name="method" value="${method}"/>
		名称:<br/>
		<hk:text name="name" value="${name}"/>
		<hk:submit value="查询"/>
	</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="c" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/e/cmp.do?companyId=${c.companyId}" target="_blank">${c.name}</hk:a>
				<hk:a href="/admin/badge_op.do?companyId=${c.companyId}&method=${method }">选择</hk:a>
			</div>
		</c:forEach>
		<hk:simplepage2 href="/admin/badge_searchcmp.do?name=${enc_name }&method=${method }"/>
	</c:if>
	<c:if test="${fn:length(list)==0 && ch==1}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/admin/badge_sel.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>