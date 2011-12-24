<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title=" 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
	${company.name }
	</div>
	<div class="hang odd">
	<hk:form action="/e/admin/admin_chgcmpflg.do">
		<hk:hide name="companyId" value="${companyId}"/>
		<hk:hide name="ch" value="1"/>
		企业类型：<br/>
		<hk:select name="cmpflg" checkedvalue="${company.cmpflg}">
			<hk:option value="<%=Company.CMPFLG_NORMAL %>" data="view2.company.cmpflg0" res="true"/>
			<hk:option value="<%=Company.CMPFLG_E_COMMERCE %>" data="view2.company.cmpflg1" res="true"/>
			<hk:option value="<%=Company.CMPFLG_EDU %>" data="view2.company.cmpflg2" res="true"/>
			<hk:option value="<%=Company.CMPFLG_HAIRDRESSING %>" data="view2.company.cmpflg3" res="true"/>
		</hk:select><br/>
		<hk:submit value="提交"/>
	</hk:form>
	</div>
	<div class="hang">
		<hk:a href="/e/admin/admin_viewcmp.do?companyId=${companyId}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>