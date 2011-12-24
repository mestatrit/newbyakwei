<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改联系信息" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">修改联系信息</div>
	<div class="hang odd">
	<c:set var="orderformuserinfoform_action" scope="request">/op/orderformuser_updatewap.do</c:set>
	<jsp:include page="orderformuserinfoform.jsp"></jsp:include>
	</div>
	<div class="hang">
	<c:if test="${fromorderform==1}"><hk:a href="/op/orderform_cfmwap.do">返回</hk:a></c:if>
	<c:if test="${fromorderform==null}"><hk:a href="/op/orderformuser_wap.do">返回</hk:a></c:if>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>