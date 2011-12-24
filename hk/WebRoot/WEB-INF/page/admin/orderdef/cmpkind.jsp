<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${zone } - ${companyKind.name }最低竞价设置" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">${zone } - ${companyKind.name }最低竞价设置</div>
	<div class="hang">
		<hk:form action="/admin/orderdef_update.do">
			<hk:hide name="kindId" value="${kindId}"/>
			<hk:hide name="cityId" value="${cityId}"/>
			<hk:hide name="provinceId" value="${provinceId}"/>
			一级导航价格:<br/>
			<hk:text name="money1" value="${o1.money}" clazz="number" maxlength="10"/>元<br/><br/>
			二级导航价格:<br/>
			<hk:text name="money2" value="${o2.money}" clazz="number" maxlength="10"/>元<br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/admin/orderdef_cmpkindlist.do?cityId=${cityId}&provinceId=${provinceId }"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>