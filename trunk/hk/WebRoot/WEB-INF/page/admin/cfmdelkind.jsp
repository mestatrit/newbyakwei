<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="企业活动分类 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/e/admin/cmpact_delkind.do">
		<hk:hide name="kindId" value="${kindId}"/>
			确认删除此分类？<br/>
			<hk:submit value="确定" name="ok"/>
			<hk:submit value="取消" name="cancel"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>