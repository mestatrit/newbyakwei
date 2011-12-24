<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.laba"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<c:if test="${userLogin}">
				<div class="laba_input">
					<jsp:include page="../inc/labainput.jsp"></jsp:include>
				</div>
			</c:if>
			<div id="laba_all" class="active_tips_tab"><a href="<%=path %>/laba/taglabalist_web.do?tagId=${tagId}">${tag.name }</a></div>
			<div class="clr"></div>
			<div id="laba_content">
				<div class="listbox">
					<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
				</div>
				<c:set var="page_url" scope="request"><%=path %>/laba/taglabalist_web.do?tagId=${tagId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<div class="mod">
				<input type="button" value="<hk:data key="view2.addvenueandtip"/>" onclick="tourl('/venue/search')" class="btn"/>
			</div>
			<jsp:include page="../inc/rcon_usertip_inc.jsp"></jsp:include>
		</div>
	</div>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function loadlabadata(w){
	if(getObj('laba_'+w).className=='active_tips_tab'){
		return;
	}
	getObj('laba_all').className='inactive_tips_tab';
	getObj('laba_city').className='inactive_tips_tab';
	getObj('laba_range').className='inactive_tips_tab';
	getObj('laba_ip').className='inactive_tips_tab';
	if(getObj('laba_follow')!=null){
		getObj('laba_follow').className='inactive_tips_tab';
	}
	getObj('laba_'+w).className='active_tips_tab';
	setHtml('laba_content','<hk:data key="view2.data_loading"/>');
	$.ajax({
		type:"POST",
		url:"/laba/"+w+"/inc",
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('laba_content',data);
		}
	});
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>