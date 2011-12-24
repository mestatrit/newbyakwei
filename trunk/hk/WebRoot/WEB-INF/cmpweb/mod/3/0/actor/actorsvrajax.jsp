<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache"%>
<%String path = request.getContextPath();%>
<ul id="mycarousel" class="jcarousel-skin-tango">
<c:forEach var="ref" items="${list}">
<li><a href="javascript:showbigimg('${ref.companyPhoto.pic320 }')"><img src="${ref.companyPhoto.pic60 }" /></a></li>
</c:forEach>
</ul>