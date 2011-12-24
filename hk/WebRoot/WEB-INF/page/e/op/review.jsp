<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CompanyReview"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="点评管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form method="get" action="/e/op/auth/op_review.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:select name="checkflg" checkedvalue="${checkflg}">
				<hk:option value="<%=CompanyReview.CHECKFLG_NORMAL+"" %>" data="view.company.checkflg_normal" res="true"/>
				<hk:option value="<%=CompanyReview.CHECKFLG_DISSENTIOUS+"" %>" data="view.company.checkflg_dissentious" res="true"/>
			</hk:select><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(reviewvolist)>0}">
		<jsp:include page="../../inc/companyreviewvo_mgr.jsp"></jsp:include>
		<hk:simplepage href="/e/op/auth/op_review.do?companyId=${companyId}&checkflg=${checkflg }"/>
	</c:if>
	<c:if test="${fn:length(reviewvolist)==0}"><hk:data key="nocompanyreviewdata"/></c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>