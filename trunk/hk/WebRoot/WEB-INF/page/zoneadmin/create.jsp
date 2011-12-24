<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="地区管理员列表 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">创建地区管理员<br/>
	<hk:form action="/admin/zoneadmin_create.do">
		地区：<br/>
		<hk:text name="zoneName" value="${zoneName}"/><br/>
		昵称：<br/>
		<hk:text name="nickName" value="${nickName}"/><br/>
		<hk:submit value="提交"/>
	</hk:form>
	</div>
	<div class="hang"><hk:a href="/admin/zoneadmin.do">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>