<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title=" 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
	转移${company.name }
	</div>
	<div class="hang odd">
	<hk:form action="/e/admin/admin_chgcmpuser.do">
		<hk:hide name="companyId" value="${companyId}"/>
		<hk:hide name="ch" value="1"/>
		<div class="hang">昵称：<hk:text name="nickName"/></div>
		<div class="hang">E-mail：<hk:text name="email"/></div>
		<div class="hang">手机号：<hk:text name="mobile"/></div>
		<div class="hang"><hk:submit value="查询"/></div>
	</hk:form>
	</div>
	<c:if test="${user!=null}">
		<div class="hang">
			<img src="${user.head48Pic }"><br/>
			${user.nickName }<br/>
			<c:if test="${not empty info.email}">
			${info.email }<br/>
			</c:if>
			<c:if test="${not empty info.mobile}">
			${info.mobile }<br/>
			</c:if>
			<hk:form action="/e/admin/admin_cfmchgcmpuser.do">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="userId" value="${user.userId}"/>
				<hk:submit value="确认转移"/>
			</hk:form>
		</div>
	</c:if>
	<div class="hang">
		<hk:a href="/e/admin/admin_viewcmp.do?companyId=${companyId}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>