<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${zone } - ${keyTag.name }最低竞价设置" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">${zone } - ${keyTag.name }最低竞价设置</div>
	<div class="hang">
		<hk:form action="/admin/orderdef_update2.do">
			<hk:hide name="tagId" value="${tagId}"/>
			<hk:hide name="cityId" value="${cityId}"/>
			<hk:hide name="provinceId" value="${provinceId}"/>
			价格:<br/>
			<hk:text name="hkb" value="${o.rmb}" clazz="number" maxlength="10"/>元<br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/admin/orderdef_findkeytag.do?cityId=${cityId}&provinceId=${provinceId }"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>