<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:form action="${orderformuserinfoform_action }">
	<hk:hide name="oid" value="${oid}"/>
	<hk:hide name="fromorderform" value="${fromorderform}"/>
	标题：<br/>
	<hk:text name="title" value="${o.title}"/><br/>
	联系人：<br/>
	<hk:text name="name" value="${o.name}"/><br/>
	手机：<br/>
	<hk:text name="mobile" value="${o.mobile}"/><br/>
	座机：<br/>
	<hk:text name="tel" value="${o.tel}"/><br/>
	E-mail：<br/>
	<hk:text name="email" value="${o.email}"/><br/>
	<hk:submit value="提交"/>
</hk:form>