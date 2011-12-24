<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.laba"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<c:set var="show_reply_link" value="true" scope="request"></c:set>
			<jsp:include page="../inc/labavo_inc.jsp"></jsp:include>
			<div class="active_tips_tab"><hk:data key="view2.laba.replylist"/></div>
			<div class="clr"></div>
			<div class="listbox">
				<c:set var="hide_close_cmt">true</c:set>
				<jsp:include page="../inc/labacmtvo_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<jsp:include page="../inc/rcon_usertip_inc.jsp"></jsp:include>
		</div>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function loadnewcmt(labaId,html){
	insertObjBefore(html,'labacmtcon');
	getObj('labacmtfrm'+labaId).content.value='';
	getObj('labacmtfrm'+labaId).replyCmtId.value='0';
	getObj('labacmtfrm'+labaId).cmtandlaba.checked=false;
	hideGlass();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>