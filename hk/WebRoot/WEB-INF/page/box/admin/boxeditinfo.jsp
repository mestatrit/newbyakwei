<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改意见 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">修改意见</div>
	<div class="hang">
		<hk:form action="/box/admin/adminbox_subeditinfo.do">
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="repage" value="${repage}"/>
			<hk:textarea name="content" rows="5" cols="10"/>
			<hk:submit value="提交修改意见"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/box/admin/adminbox.do?page=${repage}">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>