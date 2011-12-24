<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">添加管理员</div>
		<div class="mod_content">
			<div>
				<form action="<%=path %>/epp/web/op/webadmin/adminbomber_find.do">
					<hk:hide name="ch" value="1"/>
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:text name="nickName" value="${nickName}" clazz="text"/>
					<hk:submit clazz="btn split-r" value="查找"/>
					<a href="<%=path %>/epp/web/op/webadmin/adminbomber.do?companyId=${companyId}">返回</a>
				</form>
			</div>
			<div>
				<c:if test="${user==null}">没有找到此用户</c:if>
				<c:if test="${user!=null}">
					<form method="post" action="<%=path %>/epp/web/op/webadmin/adminbomber_create.do">
						<hk:hide name="ch" value="1"/>
						<hk:hide name="userId" value="${user.userId}"/>
						<hk:hide name="companyId" value="${companyId}"/>
						<img src="${user.head80Pic }"/><br/>
						${user.nickName }<br/>
						<c:if test="${not empty userOtherInfo.mobile }">
						${userOtherInfo.mobile }<br/>
						</c:if>
						<c:if test="${not empty userOtherInfo.email }">
						${userOtherInfo.email }<br/>
						</c:if>
						炸弹数量：<hk:text name="bombcount" clazz="text"/><br/>
						<hk:submit value="提交" clazz="btn"/>
					</form>
				</c:if>
			</div>
		</div>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>