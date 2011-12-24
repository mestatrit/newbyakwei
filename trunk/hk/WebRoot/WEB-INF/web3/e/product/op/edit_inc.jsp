<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="cmpproductreview_form_action" scope="request">/op/product_editreview.do</c:set>
<c:set var="cmpproductreview_form_foredit" scope="request">true</c:set>
<c:set var="cmpproductreview_form_id_prefix" scope="request">edit</c:set>
<div onkeydown="keydown${cmpproductreview_form_id_prefix }(event)">
<jsp:include page="../../../inc/cmpproductreviewform.jsp"></jsp:include>
</div>