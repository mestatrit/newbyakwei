<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="title"><hk:data key="view.companylaba" arg0="${company.name}"/></c:set>
<hk:wap title="${title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">${title }</div>
	<div class="hang" onkeydown="submitLaba(event)">
		<hk:form name="labafrm" action="/e/op/op_addcmt.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="return_url" value="/e/cmp.do?companyId=${companyId}"/>
			<input type="hidden" name="lastUrl" value="/e/cmp.do?companyId=${companyId}"/>
			<hk:textarea oid="status" name="content" clazz="ipt2" rows="2"/>
			<hk:submit value="view.companylaba.submit.value" res="true"/> <span id="remaining" class="ruo s">140</span><span class="ruo s"><hk:data key="view.char"/></span>
		</hk:form>
		<jsp:include page="../inc/labainputjs.jsp"></jsp:include>
	</div>
	<c:set var="addcmtstr" value="companyId=${companyId}&f=1" scope="request"/>
	<jsp:include page="../inc/cmpcommentvo.jsp"></jsp:include>
	<c:if test="${fn:length(cmtvolist)>0}">
		<hk:simplepage href="/e/cmp_cmt.do?companyId=${companyId}"/>
	</c:if>
	<c:if test="${fn:length(cmtvolist)==0}"><div class="hang"><hk:data key="nodataview"/></div></c:if>
	<div class="hang"><hk:a href="/e/cmp.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>