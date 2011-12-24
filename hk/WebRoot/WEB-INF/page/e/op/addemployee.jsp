<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Employee"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.op.addemployee.title"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<hk:form action="/e/op/op_addemployee.do">
		<hk:hide name="cid" value="${cid}"/>
		<div class="hang even"><hk:data key="user.nickname"/></div>
		<div class="hang odd"><hk:text name="nickName"/></div>
		<div class="hang even"><hk:data key="employee.level"/></div>
		<div class="hang odd">
		<hk:select name="level">
			<c:if test="${superadmin}">
			<hk:option value="<%=Employee.LEVEL_ADMIN+"" %>" data="employee.level_admin" res="true"/>
			</c:if>
			<hk:option value="<%=Employee.LEVEL_NORMAL+"" %>" data="employee.level_normal" res="true"/>
		</hk:select>
		</div>
		<div class="hang"><hk:submit value="e.op.addemployee.sbumit" res="true"/></div>
	</hk:form>
	<div class="hang"><hk:a href="/e/op/op_employee.do?cid=${cid}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>