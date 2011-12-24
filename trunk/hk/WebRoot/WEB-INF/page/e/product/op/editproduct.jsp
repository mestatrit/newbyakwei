<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.editcmpproduct"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<c:set var="form_action" value="/e/op/product/op_editproduct.do" scope="request"/>
		<jsp:include page="productform.jsp"></jsp:include>
	</div>
	<div class="hang"><hk:a href="/e/op/product/op_productlist.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>