<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.AuthCompany"%><%@page import="com.hk.bean.Company"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">转移${company.name }</div>
	<div class="mod_content">
		<div class="divrow">
			<hk:form action="/h4/admin/cmp_chgcmpuser.do">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="ch" value="1"/>
				<div class="divrow">昵称：<hk:text name="nickName" clazz="text"/></div>
				<div class="divrow">E-mail：<hk:text name="email" clazz="text"/></div>
				<div class="divrow">手机号：<hk:text name="mobile" clazz="text"/></div>
				<div class="divrow"><hk:submit value="查询" clazz="btn split-r"/>
				<a href="<%=path %>/h4/admin/cmp_view.do?companyId=${companyId}">返回</a>
				</div>
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
				<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/h4/admin/cmp_cfmchgcmpuser.do" target="hideframe">
					<hk:hide name="companyId" value="${companyId}"/>
					<hk:hide name="userId" value="${user.userId}"/>
					<hk:submit value="确认转移" clazz="btn split-r"/>
					<a href="<%=path %>/h4/admin/cmp_view.do?companyId=${companyId}">返回</a>
				</form>
			</div>
		</c:if>
	</div>
</div>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function updateok(e,m,v){
	tourl('<%=path %>/h4/admin/cmp_view.do?companyId=${companyId}');
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>