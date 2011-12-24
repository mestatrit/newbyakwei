<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${company.name }的产品" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang odd">${company.name }的产品</div>
	<jsp:include page="../../inc/productvo_inc.jsp"></jsp:include>
	<div class="hang"><hk:simplepage2 href="/product_listwap.do?companyId=${companyId}"/></div>	
	<div class="hang">
	<hk:a href="/e/cmp.do?companyId=${companyId}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>