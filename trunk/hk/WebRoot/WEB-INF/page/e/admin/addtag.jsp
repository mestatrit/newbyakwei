<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="在${companyKind.name }中创建标签 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">在${companyKind.name }中创建标签</div>
	<div class="hang">
		<hk:form action="/e/admin/admin_addtag.do">
			<hk:hide name="s_parentId" value="${s_parentId}"/>
			<hk:hide name="kindId" value="${kindId}"/>
			<hk:text name="name" value="${name}"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/admin/admin_tlist.do?s_parentId=${s_parentId}&kindId=${kindId }">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>