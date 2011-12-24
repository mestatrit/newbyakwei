<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.msg.setrt" /></c:set>
<c:set var="mgr_content" scope="request">
	<div>
		<hk:form oid="qfrm" method="post" onsubmit="return subfrm(this.id)" action="/user/set/set_setlabartflgweb.do" target="hideframe">
			<table cellpadding="0" cellspacing="0" class="infotable">
				<tr>
					<td>
					引用回复符号设置<br/>
					<hk:select name="labartflg" checkedvalue="${userTool.labartflg}">
						<hk:option value="0" data="RT(Retweet)"/>
						<hk:option value="1" data="回复"/>
						<hk:option value="2" data="针对"/>
					</hk:select> 
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
function onlabartsuccess(error,error_msg,op_func,obj_id_param,respValue){
	tourl("<%=path %>/user/set/set_tosetlabartflgweb.do");
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>