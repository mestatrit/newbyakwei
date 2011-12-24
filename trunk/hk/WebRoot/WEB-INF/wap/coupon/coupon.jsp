<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.coupon"/></c:set>
<hk:wap title="[${o.name }]${coupon.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<a href="/epp/index.do?companyId=${companyId }">[${o.name }]</a>${coupon.name}<br/>
	</div>
	<div class="hang odd">
		${coupon.content }<br/>
		${coupon.remark }<br/>
	</div>
	<c:if test="${coupon.pic240!=null}"><img src="${coupon.pic240 }"/></c:if>
	<c:if test="${coupon.remain}">
		<div class="hang even">
			绿色通道：<br/>
			直接发送yh${couponId }到1066916025自动下载此优惠券.<br/>
			注：将本优惠券免费发送至手机后或出示手机展示版优惠券才可使用
		</div>
		<div class="hang even">
			<hk:form method="get" action="/epp/coupon_download.do">
				<hk:hide name="companyId" value="${companyId}"/> 
				<hk:hide name="couponId" value="${couponId }"/> 
				<hk:submit name="formobile" value="发到手机"/> 
				<hk:submit name="formail" value="发到邮箱"/>
			</hk:form>
		</div>
	</c:if>
	<c:if test="${!coupon.remain}"><div class="hang">很抱歉，优惠券已经发行完毕</div></c:if>
	<div class="hang even"><hk:a href="/epp/coupon_list.do?companyId=${companyId}">返回优惠券首页</hk:a></div>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>