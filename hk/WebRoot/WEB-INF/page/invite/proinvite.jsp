<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="红地毯 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:if test="${user!=null}">
		<div class="hang">
		<span class="orange">您邀请的好友已经在火酷注册:</span>
		<hk:a href="/home.do?userId=${user.userId}">${user.nickName}</hk:a>
		</div>
	</c:if>
	<div class="hang">
		输入对方的信息:<br/><br/>
		<hk:form action="/invite/invite_addproinvite.do">
			昵称或姓名:<br/>
			<hk:text name="nickName" value="${o.nickName}" maxlength="20"/><br/><br/>
			E-mail:<br/>
			<hk:text name="input" value="${o.input}" maxlength="50"/><br/><br/>
			你对他/她的评价:<br/>
			<hk:textarea name="intro" value="${o.intro}"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<div class="hang">为已经在火酷的用户创建红地毯</div>
	<div class="hang">
		<hk:form action="/invite/invite_finduser.do">
			输入昵称进行查找:<br/><a name="#finduser"></a>
			<hk:text name="nickName" value="${nickName}" maxlength="20"/><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/invite/invite_toinvite.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>