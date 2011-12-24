<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="足迹组管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">足迹组管理</div>
	<div class="hang odd">
		<hk:form action="/admin/cmpadmingroup_creategroup.do">
			<hk:hide name="ch" value="1"/>
			名称:<br/>
			<hk:text name="name" value="${o.name}"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/admin/cmpadmingroup.do">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>