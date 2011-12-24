<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.bean.CmpUnion"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request">是否允许创建商户</c:set>
<c:set var="mgr_content" scope="request">
<div class="text_14">
<div>请使用此链接创建商户 http://mall.huoku.com/union/createcmp_tocreate.do?uid=${uid }</div>
<hk:form oid="editfrm" onsubmit="return subfrm(this.id)" action="/cmpunion/op/union_setcancmpcreate.do" target="hideframe">
	<hk:hide name="uid" value="${uid}"/>
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<td width="150px">是否允许创建商户</td>
			<td>
				<hk:radioarea name="cmpcreateflg" checkedvalue="${cmpUnion.cmpcreateflg}">
				<hk:radio value="<%=CmpUnion.CMPCREATEFLG_N %>" data="view.cmpunion.cmpcreateflg0" res="true"/>
				<hk:radio value="<%=CmpUnion.CMPCREATEFLG_Y %>" data="view.cmpunion.cmpcreateflg1" res="true"/>
				</hk:radioarea>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="center">
				<hk:submit value="保存" clazz="btn"/>
			</td>
		</tr>
	</table>
</hk:form>
</div>
<script type="text/javascript">
function subfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function cmpcreateflgok(error,error_msg,respValue){
	refreshurl();
}
</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>