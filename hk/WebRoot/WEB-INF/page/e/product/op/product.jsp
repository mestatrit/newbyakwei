<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${o.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:data key="product.name"/>:<br/>${o.name}<br/><br/>
		<hk:data key="product.sort"/>:<br/>${sort.name }<br/><br/>
		<hk:data key="product.money"/>:<br/>
		${o.money}<hk:data key="product.money.unit"/><br/><br/>
		<hk:data key="product.rebate"/>:<br/>
		${o.rebate}<br/>
		<hk:data key="product.intro"/>:<br/>
		${o.intro}<br/>
	</div>
	<div class="hang">
	<hk:a href="/e/op/product/op_toeditproduct.do?companyId=${companyId}&pid=${pid }"><hk:data key="view.editcmpproduct"/></hk:a><br/>
	<hk:a href="/e/op/product/op_toaddproduct.do?companyId=${companyId}"><hk:data key="view.createcmpproduct"/></hk:a><br/>
	<hk:a href="/e/op/product/op_productlist.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a>
	</div>
	<jsp:include page="../../../inc/foot.jsp"></jsp:include>
</hk:wap>