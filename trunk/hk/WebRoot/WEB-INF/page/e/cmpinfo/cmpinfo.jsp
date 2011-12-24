<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%HkWebUtil.loadCompany(request);%>
<hk:wap title="网站域名 - ${company.name}" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		现在的域名为:<br/>
		http://${cmpInfo.domain }<br/><br/>
		语言:<hk:data key="view2.language${cmpInfo.language}"/><br/>
	</div>
	<div class="hang"><hk:a href="/e/op/authcmp/cmpinfo_toedit.do?companyId=${companyId}"><hk:data key="view.update"/></hk:a>
	</div>
	<div class="hang"><hk:a href="/e/op/op_toedit.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>