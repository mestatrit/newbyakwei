<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.company.cmpwatchlist"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:data key="view.company.cmpwatch.add"/><br/>
		<hk:form action="/e/op/cmpwatch/op_addcmpwatch.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:data key="view.mobile"/>:<br/>
			<hk:text name="mobile" maxlength="11"/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="w" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${w.userId}">${w.user.nickName}</hk:a> 
				<hk:a href="/e/op/cmpwatch/op_delcmpwatch.do?companyId=${companyId}&userId=${w.userId}"><hk:data key="view.delete"/></hk:a>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodatalist"/></c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>