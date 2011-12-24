<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.coupon"/></c:set>
<hk:wap title="${coupon.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">发送${coupon.name}到您的邮箱<br/></div>
	<div class="hang even">
		<hk:form action="/op/coupon_inputemail.do">
			<hk:hide name="couponId" value="${couponId}"/>
			<hk:hide name="ch" value="1"/>
			输入您的E-mail地址:<br/>
			<hk:text name="email" maxlength="50"/><br/>
			<hk:submit value="发到邮箱"/> 
		</hk:form>
	</div>
	<div class="hang even"><hk:a href="/coupon.do?couponId=${couponId}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>