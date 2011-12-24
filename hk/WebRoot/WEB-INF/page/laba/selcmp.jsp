<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="选择相关足迹[${name }] - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">选择相关足迹[${name }]</div>
	<c:forEach var="c" items="${list}" varStatus="idx">
	<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/laba/op/op_selcmp.do?content=${enc_content}&name=${enc_name }&companyId=${c.companyId }">${c.name}</hk:a> 
		</div>
	</c:forEach>
	<div class="hang"><hk:simplepage2 href="/laba/op/op_selcmp.do?content=${enc_content}&name=${enc_name }"/></div>
	<div class="hang">
		列表中如果没有你需要的足迹，请<hk:a href="/laba/op/op_create.do?content=${enc_content}&noparsecmp=1">直接提交喇叭</hk:a>
	</div>
	<div><hk:a href="/laba/laba.do?${queryString}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>