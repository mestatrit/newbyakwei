<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.AuthCompany"%>
<%@page import="com.hk.bean.CmpInfo"%>
<%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>

<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">
		<hk:form action="/e/admin/admin_editcheckstatus.do">
			<hk:hide name="sysId" value="${sysId}"/>
			${vo.authCompany.user.nickName } (${vo.authCompany.company.name })<br/><br/>
			<hk:data key="authcompany.content"/>:<br/>
			${vo.authCompany.content }<br/><br/>
			<hk:data key="authcompany.createtime"/>:<br/>
			<fmt:formatDate value="${vo.authCompany.createTime}" pattern="yyyy-MM-dd HH:mm"/><br/><br/>
			<hk:data key="authcompany.checkStatus"/>:<br/>
			<hk:data key="view.authcompany.mainstatus_${vo.authCompany.mainStatus}"/><br/><br/>
			网站类型：<br/>
			<hk:select name="cmpflg" checkedvalue="${vo.authCompany.company.cmpflg}">
				<hk:option value="<%=Company.CMPFLG_NORMAL %>" data="企业网站"/>
				<hk:option value="<%=Company.CMPFLG_E_COMMERCE %>" data="电子商务"/>
			</hk:select><br/><br/>
			<hk:submit name="ok" value="view.checkok" res="true"/> 
			<hk:submit name="fail" value="view.checkfail" res="true"/>
		</hk:form>
	</div>
	<c:set var="tt" value="<%=AuthCompany.MAINSTATUS_UNCHECK %>"></c:set>
	<div class="hang"><hk:a href="/e/admin/admin_authlist.do?mainStatus=-10"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>