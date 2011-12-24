<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="徽章管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">徽章管理<br/>
	<hk:a href="/admin/badge_sel.do">创建徽章</hk:a>
	</div>
	<div class="hang">
		<hk:form method="get" action="/admin/badge.do">
			足迹/足迹组/分类名称:<br/>
			<hk:text name="name" value="${name}"/>
			<hk:submit value="查询"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="b" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<div style="width:80px;float: left;">
					<hk:a href="/admin/badge_view.do?badgeId=${b.badgeId}"><img src="${b.pic57}"></hk:a> 
				</div>
				<div style="float: left;width:300px">
					${b.name }<br/>
					${b.companyName }
					${b.groupName }
					${b.kindName }
					${b.parentKindName }
					<c:if test="${b.ruleCycle}">周期:${b.cycle }天</c:if>
					<c:if test="${!b.sysLimit && !b.invite}">${b.num }次</c:if>
					${b.intro }
				</div>
				<div style="float: left;">
					<hk:a href="/admin/badge_edit.do?badgeId=${b.badgeId}">修改</hk:a>
					<hk:a href="/admin/badge_delete.do?badgeId=${b.badgeId}">删除</hk:a>
				</div>
				<div style="clear: both;float: none;line-height: 0px;height: 0px;"></div>
			</div>
		</c:forEach>
		<hk:simplepage2 href="/admin/badge.do?name=${enc_name}"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>