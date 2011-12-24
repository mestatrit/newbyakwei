<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="${company.name}" rm="false">
	<jsp:include page="inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:if test="${not empty company.tel}">
		${company.tel }<br/>
		</c:if>
		<c:if test="${not empty company.addr}">
		${company.addr }<br/>
		</c:if>
	</div>
	<c:if test="${not empty company.head}">
		<div><img src="${company.head}"/><br/></div>
	</c:if>
	<c:if test="${loginUser==null}">
	<div class="hang">
		<hk:form action="/epp/login_validate.do">
			<div class="ha2">
			用户名(手机号、昵称):<br/>
			<hk:text name="input" value="${input}"/>
			</div>
			<div class="ha2">
				密码:<br/>
				<hk:pwd name="password" value="${password}"/>
			</div>
			<hk:submit name="login" value="登录"/> 
			<hk:submit name="reg" value="会员注册"/>
		</hk:form>
	</div>
	</c:if>
	<c:forEach var="cm" items="${cmpmoduleList}">
		<div class="hang odd">
			<hk:a href="${cm.funcurl}?companyId=${companyId }">${cm.title }</hk:a><br/>
			<c:if test="${not empty cm.intro}">
				${cm.intro }
			</c:if>
		</div>
		<div class="hang even"><br/>
		</div>
	</c:forEach>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>