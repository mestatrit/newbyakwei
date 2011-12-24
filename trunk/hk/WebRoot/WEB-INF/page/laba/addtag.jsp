<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="添加频道 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">添加标签</div>
	<div class="hang odd">
		<hk:form action="/laba/op/op_addtag.do">
			<hk:hide name="labaId" value="${labaId}"/>
			<hk:hide name="queryString" value="${queryString}"/>
			<hk:text maxlength="15" name="name"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div><hk:a href="/laba/laba.do?${queryString}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>