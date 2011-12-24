<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpTable"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:select name="tableId">
	<hk:option value="0" data="选择餐桌"/>
	<c:forEach var="table" items="${tablelist}">
		<hk:option value="${table.tableId}" data="${table.tableNum}"/>
	</c:forEach>
</hk:select>