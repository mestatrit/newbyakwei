<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${o.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 820px;">
	<jsp:include page="../inc/mgrleft.jsp"></jsp:include>
	<div class="mgrright">
		<div class="mod">
		<div class="mod_title">修改管理员数据</div>
		<div class="mod_content">
			<div>
				<form method="post" action="<%=path %>/epp/web/op/webadmin/adminbomber_update.do">
					<hk:hide name="ch" value="1"/>
					<hk:hide name="oid" value="${oid}"/>
					<hk:hide name="companyId" value="${companyId}"/>
					<img src="${user.head80Pic }"/><br/>
					${user.nickName }<br/>
					炸弹数量：<hk:text name="bombcount" clazz="text" value="${cmpBomber.bombcount}"/><br/>
					<hk:submit value="提交" clazz="btn"/>
				</form>
			</div>
		</div>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>