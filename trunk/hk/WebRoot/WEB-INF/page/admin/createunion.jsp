<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="创建联盟 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">创建联盟</div>
	<div class="hang even">
		<hk:form action="/admin/union_create.do">
			名称:<br/>
			<hk:text name="name" value="${o.name}"/><br/><br/>
			地区：<br/>
			<hk:text name="zoneName" value="${zoneName}"/><br/><br/>
			网站域名:<br/>
			http://<hk:text name="domain" value="${o.domain}"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang">
		<hk:a href="/admin/union.do">返回</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>