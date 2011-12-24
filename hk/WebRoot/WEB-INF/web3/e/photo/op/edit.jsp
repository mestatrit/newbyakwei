<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.uploadimage"/></c:set>
<c:set var="body_hk_content" scope="request">
<style>
body{background:#DEDFE1;}
</style>
<div class="mod-8">
	<%=Hkcss2Util.rd_bg %>
	<div class="cont">
		<div style="padding: 20px;">
			<h2><hk:data key="view.uploadimage"/></h2>
			<div>
				<hk:form oid="uploadfrm" onsubmit="return subphotofrm(this.id)" action="/op/uploadcmpphoto_edit.do" target="hideframe">
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
		</div>
	</div>
	<%=Hkcss2Util.rd_bg_bottom %>
</div>
<script type="text/javascript">
function subphotofrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_para){
	tourl("<%=path%>/cmp.do?companyId=${companyId}");
}
</script>
</c:set>
<jsp:include page="../../../inc/frame.jsp"></jsp:include>