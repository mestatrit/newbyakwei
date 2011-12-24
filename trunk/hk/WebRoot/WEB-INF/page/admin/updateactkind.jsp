<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="企业活动分类 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		修改企业活动分类
	</div>
	<div class="hang even">
		<hk:form action="/e/admin/cmpact_updatekind.do">
		<hk:hide name="kindId" value="${kindId}"/>
			分类名称：<br/>
			<hk:text name="name" value="${o.name}"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang">
		<hk:a href="/e/admin/cmpact_kindlist.do">返回</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>