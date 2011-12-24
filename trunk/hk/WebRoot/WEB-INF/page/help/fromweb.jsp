<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="通过电脑浏览器访问火酷 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
	通过电脑浏览器访问火酷<br/>
	通过电脑的浏览器访问<%=HkWebConfig.getWebDomain() %>,可以随时更新火酷的小喇叭等信息.<br/>
	贴士:请在浏览器中建立火酷书签,以便下次访问.
	</div>
	<div class="hang"><hk:a href="/help_back.do?${query}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>