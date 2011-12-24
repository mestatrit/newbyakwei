<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="地区管理员列表 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">地区管理员列表<br/>
	<hk:a href="/admin/zoneadmin_tocreate.do">创建地区管理员</hk:a><br/>
	<hk:form method="get" action="/admin/zoneadmin.do">
	地区：<hk:text name="zoneName" value="${zoneName}"/><hk:submit value="搜索"/>
	</hk:form>
	</div>
	<c:forEach var="zadmin" items="${list}">
	<div class="hang ${clazz_var }">
		<hk:a href="/home.do?userId=${zadmin.userId}">${zadmin.user.nickName}</hk:a> 
		<hk:a href="/admin/zoneadmin.do?pcityId=${zadmin.pcityId}">${zadmin.pcity.name}</hk:a> 
		<hk:a href="/admin/zoneadmin_delete.do?oid=${zadmin.oid}">删除</hk:a>
	</div>
	</c:forEach>
	<div class="hang">
	<hk:simplepage2 href="/admin/zoneadmin.do?zoneName=${enc_zoneName}"/>
	</div>
	<div class="hang">
	<hk:a href="/more.do">返回</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>