<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/e/admin/admin_editcompanystatus.do">
			<hk:hide name="return_url" value="${return_url}" decode="true"/>
			<hk:hide name="companyId" value="${companyId}"/>
			${o.name }<br/>
			<hk:select name="status" checkedvalue="${o.companyStatus}">
				<hk:option value="<%=Company.COMPANYSTATUS_CHECKFAIL+"" %>" data="view.companystatus_-1" res="true"/>
				<hk:option value="<%=Company.COMPANYSTATUS_CHECKED+"" %>" data="view.companystatus_1" res="true"/>
			</hk:select><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/admin/admin_viewcmp.do?companyId=${companyId}" needreturnurl="true"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>