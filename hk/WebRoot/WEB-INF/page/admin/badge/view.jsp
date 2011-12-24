<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="徽章 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<img src="${o.pic300 }"/><br/>
		<c:if test="${o.companyId>0}">
		足迹:${o.company.name }<br/>
		</c:if>
		<c:if test="${o.groupId>0}">
		足迹组:${o.cmpAdminGroup.name }<br/>
		</c:if>
		<c:if test="${o.parentKindId>0}">
		足迹分类:${o.parentKind.name }<br/>
		</c:if>
		<c:if test="${o.kindId>0}">
		足迹子分类:${o.companyKind.name }<br/>
		</c:if>
		名称:${o.name }<br/>
		<c:if test="${not empty o.intro}">说明:<br/>${o.intro }<br/></c:if>
		<c:if test="${o.noLimit}">报到次数:${o.num }</c:if>
		<c:if test="${o.limit}">
			<c:if test="${o.ruleCycle}">周期:${o.cycle }天<br/></c:if>
			报到次数:${o.num }
		</c:if>
		<c:if test="${o.sysLimit}">
			规则:<hk:data key="view2.badge.syslimit.ruleflg${o.ruleflg}"/>
		</c:if>
	</div>
	<div class="hang">
		<hk:a href="/admin/badge_edit.do?badgeId=${badgeId}">修改</hk:a>
	</div>
	<c:if test="${continue==1}">
	<div class="hang">
		<hk:a href="/admin/badge_create.do?limitflg=${limitflg}&ruleflg=${ruleflg }&parentId=${o.companyKind.parentId }">继续添加</hk:a>
	</div>
	</c:if>
	<div class="hang">
		<hk:a href="/admin/badge.do">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>