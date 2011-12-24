<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request">设置默认的搜索产品方式</c:set>
<c:set var="mgr_content" scope="request">
<style>

</style>
<div style="padding: 20px;">
	<h3>设置默认的搜索产品方式</h3>
	<div class="bdbtm"></div>
	<hk:form oid="typefrm" action="/e/op/op_setsearchtype.do" onsubmit="return subfrm(this.id)" target="hideframe">
		<hk:hide name="companyId" value="${companyId}"/>
		<hk:radioarea name="psearchType" checkedvalue="${o.psearchType}">
			<div class="text_14"><hk:radio value="<%=Company.PSEARCHTYPE_NAME %>" data="view.company.searchproduct_type0" res="true"/></div>
			<div class="text_14"><hk:radio value="<%=Company.PSEARCHTYPE_PNUM %>" data="view.company.searchproduct_type1" res="true"/></div>
			<div class="text_14"><hk:radio value="<%=Company.PSEARCHTYPE_SHORTNAME %>" data="view.company.searchproduct_type2" res="true"/></div>
		</hk:radioarea><br/>
		<hk:submit value="view.submit" res="true" clazz="btn"/>
	</hk:form>
</div>
<script type="text/javascript">
function subfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error=="0"){
		refreshurl();
	}
	else{
		hideSubmitDiv();
		alert(error_msg);
	}
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>