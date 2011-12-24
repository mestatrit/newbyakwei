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
			<c:set var="all_css"><c:if test="${w=='all'}">active_tips_tab</c:if><c:if test="${w!='all'}">inactive_tips_tab</c:if></c:set>
			<c:set var="city_css"><c:if test="${w=='city'}">active_tips_tab</c:if><c:if test="${w!='city'}">inactive_tips_tab</c:if></c:set>
			<c:set var="range_css"><c:if test="${w=='range'}">active_tips_tab</c:if><c:if test="${w!='range'}">inactive_tips_tab</c:if></c:set>
			<c:set var="ip_css"><c:if test="${w=='ip'}">active_tips_tab</c:if><c:if test="${w!='ip'}">inactive_tips_tab</c:if></c:set>
			<c:set var="follow_css"><c:if test="${w=='follow'}">active_tips_tab</c:if><c:if test="${w!='follow'}">inactive_tips_tab</c:if></c:set>
			<div id="laba_all" class="${all_css }"><a href="javascript:loadlabadata('all')"><hk:data key="view2.laba.all"/></a></div>
			<div id="laba_city" class="${city_css }"><a href="javascript:loadlabadata('city')"><hk:data key="view2.laba.city"/></a></div>
			<div id="laba_range" class="${range_css }"><a href="javascript:loadlabadata('range')"><hk:data key="view2.laba.range"/></a></div>
			<div id="laba_ip" class="${ip_css }"><a href="javascript:loadlabadata('ip')"><hk:data key="view2.laba.ip"/></a></div>
			<c:if test="${userLogin}"><div id="laba_follow" class="${follow_css }"><a href="javascript:loadlabadata('follow')"><hk:data key="view2.laba.follow"/></a></div></c:if>
			<div class="clr"></div>
			<div id="laba_content">
				<div class="listbox">
					<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
				</div>
				<%request.setAttribute("url_rewrite", true);%>
				<c:set var="page_url" scope="request">/laba/${w}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<div class="mod" align="center">
				<div class="divrow bdtm">
				<input type="button" value="<hk:data key="view2.addvenueandtip"/>" onclick="tourl('/venue/search')" class="btn"/>
				</div>
			</div>
			<div class="mod">
				<div class="mod_content divrow bdtm" align="center">
					<form method="get" action="<%=path %>/laba_webs.do">
						<hk:text name="sw" value="${sw}" clazz="text" style="width:200px;"/>
						<hk:submit value="喇叭搜索" clazz="btn"/>
					</form>
				</div>
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