<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AuthCompany"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.authcompany.mainstatus_${mainStatus}"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form method="get" action="/e/admin/admin_authlist.do">
		<hk:data key="authcompany.checkStatus"/>:<br/>
		<hk:select name="mainStatus" checkedvalue="${mainStatus}">
			<hk:option value="-10" data="view.all" res="true"/>
			<hk:option value="<%=AuthCompany.MAINSTATUS_UNCHECK+"" %>" data="view.authcompany.mainstatus_0" res="true"/>
			<hk:option value="<%=AuthCompany.MAINSTATUS_CHECKED+"" %>" data="view.authcompany.mainstatus_1" res="true"/>
			<hk:option value="<%=AuthCompany.MAINSTATUS_CHECKFAIL+"" %>" data="view.authcompany.mainstatus_-1" res="true"/>
		</hk:select><br/><br/>
		<hk:data key="company.name"/>:<br/>
		<hk:text name="name" value="${name}"/><br/>
		<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(authcompanyvolist)>0}">
		<c:set var="addstr" value="name=${enc_name}&checkStatus=${checkStatus }"/>
		<c:forEach var="vo" items="${authcompanyvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${vo.authCompany.userId}" target="_blank">${vo.authCompany.user.nickName}</hk:a> 
				<hk:a href="/e/cmp.do?companyId=${vo.authCompany.companyId}" target="_blank">${vo.authCompany.company.name}</hk:a> 
				<hk:a href="/e/admin/admin_authcompany.do?sysId=${vo.authCompany.sysId}"><hk:data key="view.detail"/></hk:a>					
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(authcompanyvolist)==0}"><hk:data key="nodataview"/></c:if>
	<hk:simplepage href="/e/admin/admin_authlist.do${addstr }"/>
	<div class="hang"><hk:a href="/more.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>