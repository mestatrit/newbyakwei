<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.coupon"/></c:set>
<hk:wap title="[${o.name }]${coupon.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">发送[${o.name }]${coupon.name}到您的邮箱<br/></div>
	<div class="hang even">
		<hk:form action="/epp/coupon_saveemail.do?companyId=${companyId}&couponId=${couponId }">
			输入您的E-mail地址:<br/>
			<hk:text name="email" maxlength="50"/><br/>
			<hk:submit name="formail" value="发到邮箱"/> 
		</hk:form>
	</div>
	<div class="hang even"><hk:a href="/epp/coupon_list.do?companyId=${companyId}">返回优惠券首页</hk:a></div>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>