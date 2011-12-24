<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form method="get" action="/e/admin/admin_clist.do">
			<hk:select name="status" checkedvalue="${status}">
				<hk:option value="-100" data="view.companystatus_all" res="true"/>
				<hk:option value="<%=Company.COMPANYSTATUS_CHECKFAIL %>" data="view.companystatus_-1" res="true"/>
				<hk:option value="<%=Company.COMPANYSTATUS_UNCHECK %>" data="view.companystatus_0" res="true"/>
				<hk:option value="<%=Company.COMPANYSTATUS_CHECKED %>" data="view.companystatus_1" res="true"/>
			</hk:select><br/>
			<hk:text name="name" value="${name}"/><br/><br/>
			地区:<br/>
			<hk:text name="zoneName" value="${zoneName}"/><br/>
			<hk:submit value="view.search" res="true"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="c" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/e/admin/admin_viewcmp.do?companyId=${c.companyId}" needreturnurl="true">${c.name}</hk:a> 
				<hk:data key="view.companystatus_${c.companyStatus}"/> 
				<hk:a href="/e/admin/admin_clist.do?cityId=${c.cityId }&provinceId=${c.provinceId }">${c.zoneName}</hk:a>
			</div>
		</c:forEach>
	</c:if>	
	<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
	<hk:simplepage2 href="/e/admin/admin_clist.do?name=${enc_name}&zoneName=${enc_zoneName }&status=${status }&cityId=${cityId }&provinceId=${provinceId }"/>
	<div class="hang"><hk:a href="/more.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>