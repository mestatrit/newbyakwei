<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.HkAd"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="删除广告" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">确定要删除此广告？</div>
	<div class="hang">
		<hk:form action="/op/gg_del.do">
			<hk:hide name="oid" value="${oid}"/>
			<hk:hide name="ch" value="1"/>
			<hk:submit name="ok" value="确定"/> 
			<hk:submit name="cancel" value="取消"/> 
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>