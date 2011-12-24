<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
			<div class="mod_title">选择区块的内容</div>
			<div class="mod_content">
				<div class="divrow b">
					${cmpPageBlock.name }
				</div>
				<c:if test="${not empty cmpPageBlock.expression && !cmpPageBlock.auto}">已选择手动推荐</c:if>
				<c:if test="${cmpArticleTag!=null}">已选择标签：${cmpArticleTag.name }</c:if>
				<c:if test="${cmpNav!=null}">已选择栏目：${cmpNav.name }</c:if>
				<c:if test="${cmpArticleGroup!=null}">已选择组：${group_cmpNav.name } &gt;&gt; ${cmpArticleGroup.name }</c:if>
				<div class="divrow">
					<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_setnotauto.do?companyId=${companyId}&blockId=${blockId}&pageflg=${pageflg}">手动推荐内容</a>
				</div>
				<div class="divrow">
					<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_navlist.do?companyId=${companyId}&blockId=${blockId}&pageflg=${pageflg}&change=${change}">选择栏目以及群组</a>
				</div>
				<div class="divrow">
					<a href="<%=path %>/epp/web/op/webadmin/cmppageblock_taglist.do?companyId=${companyId}&blockId=${blockId}&pageflg=${pageflg}&change=${change}">选择标签</a>
				</div>
				<c:if test="${change==1}">
				<a class="more2" href="<%=path %>/epp/web/op/webadmin/cmppageblock_content.do?companyId=${companyId}&blockId=${blockId}">返回</a>
				</c:if>
				<c:if test="${!(change==1)}">
				<a class="more2" href="<%=path %>/epp/web/op/webadmin/cmppageblock.do?companyId=${companyId}&pageflg=${pageflg}">返回</a>
				</c:if>
			</div>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>