<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${zone } - 设置关键词排名竞价" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">${zone } - 设置关键词排名竞价</div>
	<div class="hang">
		<hk:form action="/admin/orderdef_findkeytag.do">
			<hk:hide name="cityId" value="${cityId}"/>
			<hk:hide name="provinceId" value="${provinceId}"/>
			查询关键词:<br/>
			<hk:text name="name" value="${name}"/><br/>
			<hk:submit value="搜索"/>
		</hk:form><br/>
		<c:if test="${not empty name && keyTag==null}">
			目前还没有 ${name} 这个关键词<br/>
			<hk:form action="/admin/orderdef_addkeytag.do">
				<hk:hide name="kindId" value="${kindId}"/>
				<hk:hide name="cityId" value="${cityId}"/>
				<hk:hide name="name" value="${name}"/>
				<hk:submit value="创建${name}"/>
			</hk:form>
		</c:if>
	</div>
	<div class="hang"><hk:a href="/index_tosearchcity.do?fn=4&show_country=true&hide_all=1"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>