<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><hk:wap title="${o.name}" rm="false">
	<jsp:include page="inc/top.jsp"></jsp:include>
	<div class="hang reply">
		${o.name }<br/>
		<c:if test="${not empty o.tel}">
		${o.tel }<br/>
		</c:if>
		<c:if test="${not empty o.addr}">
		${o.addr }
		<br/>
		</c:if>
		<c:if test="${not empty o.traffic}">
		${o.traffic }<br/>
		</c:if>
		<c:if test="${not empty o.intro}">
			${o.intro }<br/>
		</c:if>
	</div>
	<c:if test="${not empty o.headPath}">
		<div class="reply"><img src="${o.head240}"/><br/></div>
	</c:if>
	<c:if test="${loginUser==null}">
	<div class="hang">
		<hk:form action="/epp/login_validate.do" needreturnurl="true">
			<hk:hide name="companyId" value="${companyId}"/>
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
	<c:forEach var="cm" items="${cmpmoduleList}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="${cm.tmlModule.funcurl}?companyId=${companyId }">${cm.title }</hk:a><br/>
			<c:if test="${not empty cm.intro}"><span class="s">${cm.intro }</span></c:if>
		</div>
	</c:forEach>
	<c:if test="${o.userId==loginUser.userId}">
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.mgrsite"/></hk:a></div>
	</c:if>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>