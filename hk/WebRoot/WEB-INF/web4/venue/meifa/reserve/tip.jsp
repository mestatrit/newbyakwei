<%@ page language="java" pageEncoding="UTF-8"%><%@page import="java.util.Date"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">预约提示 </c:set>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="mod_title">预约提示</div>
	<div class="mod_content" style="padding: 20px">
		<c:if test="${reserve_limit}">
		<span style="font-size: 18px;" class="infowarn">您的未处理预约已经超过本店的限制，不能再进行创建预约了</span>
		</c:if>
		<c:if test="${!reserve_limit}">
			您还有其他未处理的预约，您是要继续<input type="button" value="创建新预约" class="btn" onclick="tourl('<%=path %>/h4/op/reserve_sel.do?companyId=${companyId }&actorId=${actorId }&repeat_reserveId=${repeat_reserveId }')"/>
			还是<input type="button" value="修改最后一次预约" class="btn" onclick="tourl('<%=path %>/h4/op/reserve_updatelast.do?companyId=${companyId }')"/><br/>
		</c:if>
		<div align="center" style="padding: 20px;">
			<a class="split-r" href="/cmp/${companyId }/actor/list">返回服务人员列表</a> 
			<a href="<%=path %>/h4/op/reserve_myreserve.do">回到我的预约</a>
		</div>
	</div>
</div>
</c:set><jsp:include page="../../../inc/frame.jsp"></jsp:include>