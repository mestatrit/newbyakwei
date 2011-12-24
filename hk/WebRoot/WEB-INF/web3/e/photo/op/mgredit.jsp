<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.uploadimage"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
	<hk:form oid="uploadfrm" onsubmit="return subphotofrm(this.id)" action="/e/op/photo/photo_mgredit.do" target="hideframe">
		<hk:hide name="companyId" value="${companyId}"/>
		<table class="infotable" cellpadding="0" cellspacing="0">
		<c:forEach var="p" items="${list}">
			<tr>
				<td width="80px">名称：</td>
				<td>
					<div class="split3">
					<hk:hide name="pid" value="${p.photoId}"/>
					<hk:text name="name${p.photoId}" value="" clazz="text" maxlength="20"/><br/>
					<img src="${p.pic240 }" class="split-top"/>
					</div>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td>
				<div class="form_btn"><hk:submit value="view.submit" res="true" clazz="btn"/></div>
			</td>
		</tr>
		</table>
	</hk:form>
</div>
<script type="text/javascript">
function subphotofrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_para){
	tourl("<%=path%>/e/op/photo/photo_list.do?companyId=${companyId}");
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>