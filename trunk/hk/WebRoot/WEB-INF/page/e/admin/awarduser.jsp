<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CompanyAward"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.admin.addbizcle.title"/></c:set>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:data key="view.company.awarduser"/>
		<hk:form method="get" action="/e/admin/admin_awarduser.do">
			<hk:select name="status" checkedvalue="${status}">
				<hk:option value="<%=CompanyAward.AWARDSTATUS_N+"" %>" data="view.companyaward.awardstatus_0" res="true"/>
				<hk:option value="<%=CompanyAward.AWARDSTATUS_Y+"" %>" data="view.companyaward.awardstatus_1" res="true"/>
			</hk:select><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="a" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${a.createrId}">${a.creater.nickName}</hk:a> 
				<hk:a href="/e/cmp.do?companyId=${a.companyId}">${a.company.name}</hk:a> 
				<c:if test="${a.awarded}">
					<hk:data key="view.company.award.companymoney" arg0="${a.money}"/> 
					<hk:data key="view.company.award.awardmoney" arg0="${a.awardhkb}"/>
				</c:if>
				<c:if test="${!a.awarded}">
					<hk:a href="/e/admin/admin_award.do?companyId=${a.companyId}&status=${status}"><hk:data key="view.companyaward.funcaward"/></hk:a>
				</c:if>
			</div>
		</c:forEach>
		<hk:simplepage href="/e/admin/admin_awarduser.do"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>