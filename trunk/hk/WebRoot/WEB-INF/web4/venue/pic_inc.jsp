<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<div class="pic_other">
	<div class="other_piccon">
		<c:forEach var="p" items="${photolist}">
			<div class="pic_box">
			<a href="/venue/${companyId }/pic/${p.photoId}/"><img src="${p.pic60 }"/></a>
			</div>
		</c:forEach>
	</div>
	<div style="text-align: center;padding-top: 10px;">
		<c:if test="${has_pre}">
			<a class="split-r"href="javascript:loadOtherPic(${pre_id },-1)">上一张</a>
		</c:if>
		<c:if test="${has_next}">
			<a href="javascript:loadOtherPic(${next_id },1)">下一张</a>
		</c:if>
	</div>
</div>