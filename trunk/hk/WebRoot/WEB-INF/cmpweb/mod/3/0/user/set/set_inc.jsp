<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<div class="useropnav">
	<a href="<%=path %>/epp/web/op/user/set_setinfo.do?companyId=${companyId}" <c:if test="${op_func==1}">class="active"</c:if>><hk:data key="epp.user.edit.userinfo"/></a>
	<a href="<%=path %>/epp/web/op/user/set_sethead.do?companyId=${companyId}" <c:if test="${op_func==2}">class="active"</c:if>><hk:data key="epp.user.edit.head"/></a>
	<a href="<%=path %>/epp/web/op/user/set_setpwd.do?companyId=${companyId}" <c:if test="${op_func==3}">class="active"</c:if>><hk:data key="epp.user.edit.pwd"/></a>
</div>