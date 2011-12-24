<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="itemdata_shownum" scope="request" value="true"></c:set>
<jsp:include page="itemdata_inc.jsp"></jsp:include>
<c:if test="${more_itemcmt}">
<a class="more2" href="${ctx_path }/tb/user_itemcmt?userid=${userid}">更多</a>
</c:if>