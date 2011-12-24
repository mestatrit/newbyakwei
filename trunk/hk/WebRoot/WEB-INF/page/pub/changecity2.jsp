<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.inputcityname"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
	当前城市：${sys_zone_pcity.city.city }
	</div>
	<div class="hang even">
		<hk:form action="/index_changecity2.do" needreturnurl="true">
			<hk:hide name="ch" value="1"/>
			<hk:hide name="forsel" value="${forsel}"/>
			输入城市名称：<br/>
			<hk:text name="zoneName"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(cmpzoneinfoList)>0}">
	<div class="hang">
		<c:forEach var="z" items="${cmpzoneinfoList}">
			<hk:a href="/index_selcity2.do?cityId=${z.pcityId}" needreturnurl="true">${z.name}</hk:a> 
		</c:forEach>
	</div>
	</c:if>
	<c:if test="${not empty return_url}">
		<div class="hang"><hk:a href="${denc_return_url}">返回</hk:a></div>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>