<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.UserNoticeInfo"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.mgr.msgnotice" /></c:set>
<c:set var="mgr_content" scope="request">
	<div>
		<hk:form oid="qfrm" method="post" onsubmit="return subfrm(this.id)" action="/user/set/set_setnoticeinfoweb.do" target="hideframe">
			<table cellpadding="0" cellspacing="0" class="infotable">
				<tr>
					<td>
						<hk:checkbox name="labaReplyNotice" checkedvalue="${info.labaReplyNotice}" value="<%=UserNoticeInfo.NOTICE_Y %>" data="view.user.mgr.msgnotice.labaReplyNotice" res="true"/><br/>
						<hk:checkbox name="labaReplyIMNotice" checkedvalue="${info.labaReplyIMNotice}" value="<%=UserNoticeInfo.NOTICE_Y %>" data="view.user.mgr.msgnotice.labaReplyIMNotice" res="true"/><br/>
						<hk:checkbox name="labaReplySysNotice" checkedvalue="${info.labaReplySysNotice}" value="<%=UserNoticeInfo.NOTICE_Y %>" data="view.user.mgr.msgnotice.labaReplySysNotice" res="true"/><br/>
						<hk:checkbox name="msgNotice" checkedvalue="${info.msgNotice}" value="<%=UserNoticeInfo.NOTICE_Y %>" data="view.user.mgr.msgnotice.msgNotice" res="true"/><br/>
						<hk:checkbox name="followNotice" checkedvalue="${info.followNotice}" value="<%=UserNoticeInfo.NOTICE_Y %>" data="view.user.mgr.msgnotice.followNotice" res="true"/><br/>
						<hk:checkbox name="followIMNotice" checkedvalue="${info.followIMNotice}" value="<%=UserNoticeInfo.NOTICE_Y %>" data="view.user.mgr.msgnotice.followIMNotice" res="true"/><br/>
						<hk:checkbox name="followSysNotice" checkedvalue="${info.followSysNotice}" value="<%=UserNoticeInfo.NOTICE_Y %>" data="view.user.mgr.msgnotice.followSysNotice" res="true"/><br/>
					</td>
				</tr>
				<tr>
					<td><div class="form_btn"><hk:submit value="保存" clazz="btn"/></div></td>
				</tr>
			</table>
		</hk:form>
	</div>
<script type="text/javascript">
function subfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onnoticesuccess(error,error_msg,op_func,obj_id_param,respValue){
	tourl("<%=path %>/user/set/set_tosetnoticeinfoweb.do");
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>