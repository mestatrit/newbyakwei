<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache" %><%String path = request.getContextPath();%>
<c:set scope="request" var="epp_other_value">
<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/pub/css/jiaodian.css" />
<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jiaodian.js"></script>
</c:set>
<c:set scope="request" var="html_body_content">
<c:set scope="request" var="needRefresh" value="true"></c:set>
<cache:cache key="company_home_cache_${o.companyId }_${o.cmpflg}" time="1800" refresh="${needRefresh}">
	<div class="">
		<div class="hkd0">
			<div class="l">
				<jsp:include page="index_mod_12.jsp"></jsp:include>
			</div>
			<div class="mid">
				<div class="top">
					<jsp:include page="index_mod_14.jsp"></jsp:include>
				</div>
				<div class="middle">
					<jsp:include page="index_mod_1.jsp"></jsp:include>
				</div>
				<div class="bottom">
					<jsp:include page="index_mod_15.jsp"></jsp:include>
				</div>
			</div>
			<div class="r">
				<jsp:include page="index_mod_13.jsp"></jsp:include>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<jsp:include page="index_mod_16.jsp"></jsp:include>
	<div class="p_l2">
		<div class="p_l">
			<jsp:include page="index_mod_2.jsp"></jsp:include>
			<!-- 焦点图下的文章 -->
			<jsp:include page="index_mod_3.jsp"></jsp:include>
		</div>
		<div class="p_mid">
			<div class="inner">
				<jsp:include page="index_mod_4.jsp"></jsp:include>
				<jsp:include page="index_mod_5.jsp"></jsp:include>
				<jsp:include page="index_mod_6.jsp"></jsp:include>
			</div>
		</div>
		<div class="clr"></div>
		<jsp:include page="index_mod_9.jsp"></jsp:include>
	</div>
	<div class="p_r">
		<jsp:include page="index_mod_7.jsp"></jsp:include>
		<jsp:include page="index_mod_8.jsp"></jsp:include>
	</div>
	<div class="clr"></div>
	<jsp:include page="index_mod_11.jsp"></jsp:include>
</cache:cache>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>