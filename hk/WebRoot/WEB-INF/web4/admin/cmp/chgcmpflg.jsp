<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.AuthCompany"%><%@page import="com.hk.bean.Company"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="mgr_body_content" scope="request">
<div class="mod">
	<div class="mod_title">更改企业网站类型</div>
	<div class="mod_content">
		${company.name }
		<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="<%=path %>/h4/admin/cmp_chgcmpflg.do" target="hideframe">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="ch" value="1"/>
			企业网站类型：
			<hk:select name="cmpflg" checkedvalue="${company.cmpflg}">
				<hk:option value="<%=Company.CMPFLG_NORMAL %>" data="view2.company.cmpflg0" res="true"/>
				<hk:option value="<%=Company.CMPFLG_E_COMMERCE %>" data="view2.company.cmpflg1" res="true"/>
				<hk:option value="<%=Company.CMPFLG_EDU %>" data="view2.company.cmpflg2" res="true"/>
				<hk:option value="<%=Company.CMPFLG_HAIRDRESSING %>" data="view2.company.cmpflg3" res="true"/>
			</hk:select>
			<hk:submit value="提交" clazz="btn split-r"/>
			<a href="<%=path %>/h4/admin/cmp_view.do?companyId=${companyId}">返回</a>
		</form>
	</div>
</div>
<script type="text/javascript">
function subfrm(frmid){
	showGlass(frmid);
	return true;
}
function updateok(e,m,v){
	refreshurl();
}
</script>
</c:set>
<jsp:include page="../mgr.jsp"></jsp:include>