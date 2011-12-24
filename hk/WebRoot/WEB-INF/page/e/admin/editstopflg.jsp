<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="设置营业状态 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		设置营业状态<br/>
		<hk:form action="/e/admin/admin_editstopflg.do">
			<hk:hide name="return_url" value="${return_url}" decode="true"/>
			<hk:hide name="companyId" value="${companyId}"/>
			${o.name }<br/>
			<hk:select name="stopflg" checkedvalue="${o.stopflg}">
				<hk:option value="<%=Company.STOPFLG_N+"" %>" data="company.stopflg_0" res="true"/>
				<hk:option value="<%=Company.STOPFLG_Y+"" %>" data="company.stopflg_1" res="true"/>
			</hk:select><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/e/admin/admin_viewcmp.do?companyId=${companyId}" needreturnurl="true"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>