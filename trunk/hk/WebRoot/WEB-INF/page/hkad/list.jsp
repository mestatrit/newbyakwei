<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="广告管理" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">广告管理</div>
	<c:if test="${fn:length(list)==0}">
		暂时没有广告发布
	</c:if>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="c" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/op/gg.do?oid=${c.oid}">${c.name }</hk:a> 
				${c.totalViewCount }/${c.viewCount } 
				<c:if test="${c.pause}">已暂停</c:if>
				<hk:a href="/op/gg_update.do?oid=${c.oid}">修改</hk:a>
				<c:if test="${c.pause}">
					<hk:a href="/op/gg_dorun.do?oid=${c.oid}&from=list">运行</hk:a>
				</c:if>
				<c:if test="${!c.pause}">
					<hk:a href="/op/gg_pause.do?oid=${c.oid}&from=list">暂停</hk:a>
				</c:if>
				<hk:a href="/op/gg_del.do?oid=${c.oid}">删除</hk:a>
			</div>
		</c:forEach>
	</c:if>
	<div class="hang">
		<hk:simplepage2 href="/op/gg_list.do"/>
	</div>
	<div class="hang even"><hk:a href="/op/gg_create.do">发布广告</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>