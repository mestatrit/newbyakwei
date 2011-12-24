<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath();%>
<hk:form oid="kindeditfrm" onsubmit="return subkindeditfrm(this.id)" action="/cmpunion/op/union_editkind.do" target="hideframe">
	<hk:hide name="uid" value="${uid}"/>
	<hk:hide name="kindId" value="${kindId}"/>
	名称：<hk:text name="name" clazz="text_short_2" value="${cmpUnionKind.name}"/>
	<hk:submit value="提交" clazz="btn"/>
</hk:form>