<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="省/直辖市列表- 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">省/直辖市列表</div>
	<div class="hang">
		<hk:form action="/admin/zone_createprovince.do">
			<hk:hide name="countryId" value="${countryId}"/>
			<hk:hide name="ch" value="1"/>
			名称:
			<hk:text name="province"/>
			<hk:submit value="添加省/直辖市"/>
		</hk:form>
	</div>
	<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/admin/zone_citylist.do?provinceId=${p.provinceId}">${p.province}</hk:a>
			<hk:a href="/admin/zone_editprovince.do?provinceId=${p.provinceId }">修改</hk:a>
			<hk:a href="/admin/zone_delete.do?provinceId=${p.provinceId }&method=province">删除</hk:a>
		</div>
	</c:forEach>
	<div class="hang">
	<hk:a href="/admin/zone.do">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>