<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="用户管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">用户管理</div>
	<div class="hang">
		<hk:form method="get" action="/admin/mgruser.do">
			昵称:<hk:text name="key" value="${key}"/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<c:if test="${search}">
		没有数据显示
	</c:if>
	<c:if test="${search && user!=null}">
	<div class="${clazz_var}">
		${user.nickName } 
		<c:if test="${locked}">
		<hk:a href="/admin/mgruser_unlock.do?userId=${user.userId}&key=${enc_key }">解除封锁</hk:a>
		</c:if>
		<c:if test="${!locked}">
		<hk:a href="/admin/mgruser_lock.do?userId=${user.userId}&key=${enc_key }">封锁</hk:a>
		</c:if>
	</div>
	</c:if>
	<div class="hang"><hk:a href="/more.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>