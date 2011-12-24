<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="会员搜索 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form method="get" action="/user/search.do">
			<hk:hide name="sfrom" value="${sfrom}"/>
			昵称:<hk:text name="sw" value="${sw}"/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(uservolist)==0 && not empty sw}">没有数据显示</c:if>
	<c:if test="${fn:length(uservolist)>0}">
		<c:set var="addStr" value="from=usersearch&sw=${enc_sw }&sfrom=${sfrom }"/>
		<jsp:include page="../inc/uservo.jsp"></jsp:include>
		<hk:simplepage href="/user/search.do?sw=${enc_sw}&sfrom=${sfrom }"/>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>