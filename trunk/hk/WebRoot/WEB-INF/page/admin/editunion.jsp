<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改联盟数据- 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">修改联盟数据</div>
	<div class="hang even">
		<hk:form action="/admin/union_edit.do">
			<hk:hide name="uid" value="${uid}"/>
			名称:<br/>
			<hk:text name="name" value="${o.name}"/><br/><br/>
			地区：<br/>
			<hk:text name="zoneName" value="${zoneName}"/><br/><br/>
			网站域名:<br/>
			http://<hk:text name="domain" value="${o.domain}"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang even">
		<hk:form action="/admin/union_chgstatus.do">
		<hk:hide name="uid" value="${uid}"/>
		<c:if test="${!o.run}"><hk:submit name="run" value="启用"/> </c:if>
		<c:if test="${!o.limit}"><hk:submit name="limit" value="限制访问"/> </c:if>
		<c:if test="${!o.stop}"><hk:submit name="stop" value="完全关闭"/> </c:if>
		</hk:form>
	</div>
	<div class="hang">
		<hk:a href="/admin/union.do">返回</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>