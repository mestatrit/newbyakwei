<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="国家列表 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">国家列表</div>
	<div class="hang">
		<hk:form action="/admin/zone_createcountry.do">
			<hk:hide name="ch" value="1"/>
			名称:
			<hk:text name="country"/>
			<hk:submit value="添加国家"/>
		</hk:form>
	</div>
	<c:forEach var="c" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/admin/zone_provincelist.do?countryId=${c.countryId}">${c.country}</hk:a>
			<hk:a href="/admin/zone_editcountry.do?countryId=${c.countryId}">修改</hk:a>
			<hk:a href="/admin/zone_delete.do?countryId=${c.countryId}&method=country">删除</hk:a>
		</div>
	</c:forEach>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>