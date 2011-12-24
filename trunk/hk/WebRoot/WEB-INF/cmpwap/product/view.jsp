<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a> &gt; ${cmpProduct.name }</div>
<c:if test="${not empty cmpProduct.headPath}">
<img src="${cmpProduct.head240 }"/>
</c:if>
<div class="row even">
<c:if test="${cmpProduct.money>0}">
<hk:data key="epp.product.price"/>：${cmpProduct.money } ￥<br/>
</c:if>
<c:if test="${cmpProduct.rebate>0}">
<hk:data key="epp.product.rebate"/>：<fmt:formatNumber value="${cmpProduct.rebate }" pattern="##.#"></fmt:formatNumber> 折<br/>
</c:if>
${cmpProduct.intro }
</div>
<div class="row even">
<a href="<%=path %>/epp/web/product_wap.do?companyId=${companyId}&navId=${navId}"><hk:data key="epp.return"/></a>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>