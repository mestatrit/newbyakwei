<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.frame.util.MessageUtil"%><%@page import="com.hk.web.util.HkWebUtil"%><%@page import="com.hk.bean.DelInfo"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:domain domain="<%=HkWebConfig.getWebDomain() %>"/>
<%HkWebUtil.initBrowser(request); %>
<jsp:include page="css.jsp"></jsp:include>
<c:if test="${!showMode.touchMode}">
<div class="menu menu-top"><hk:a name="top" href="/index_h.do">火酷 huoku.com</hk:a><a href="#bottom">(内测)↓</a></div>
</c:if>
<c:if test="${showMode.touchMode}">
<script type="text/javascript">
function toggleMenu() {
	var body = document.getElementById("thepage");
	body.className = body.className == "" ? "show-menu" :"";
	body.innerHTML += "<!-- weird bug fix -->";
	return false;
}
</script>
<hk:importcss href="/st/css/touch.css"/>
	<c:if test="${!userLogin}">
		<div id="menu" class="menu">
			<ul id="menu-main">
				<li><hk:a href="/tologin.do">登录</hk:a></li>
			</ul>
		</div>
	</c:if>
	<c:if test="${userLogin}">
		<div id="menu" class="menu">
			<ul id="menu-main">
				<li><hk:a href="/op/user_findcmp.do">报到</hk:a></li>
				<li><hk:a href="/box/market.do">开宝箱</hk:a></li>
				<li><hk:a href="/home.do?userId=${loginUser.userId}">我</hk:a></li>
				<li><a href="#" onclick="return toggleMenu()">更多</a></li>
			</ul>
			<ul id="menu-extras">
				<li><hk:a href="/index_h.do">首页</hk:a></li>
				<li><hk:a href="/op/user_friendcheckin.do">大家在哪</hk:a></li>
				<li><hk:a href="/square.do">喇叭</hk:a></li>
				<li><hk:a href="/e/cmp_list.do?cityId=${sys_zone_pcityId}">搜索足迹</hk:a></li>
				<li><hk:a href="#">徽章</hk:a></li>
				<li><hk:a href="/user/list2.do">找朋友</hk:a></li>
				<li><hk:a href="/index_changecity2.do">切换地区 (${sys_zone_pcity.city.city })</hk:a></li>
				<li><hk:a href="/invite/invite_toinvite.do">邀请</hk:a></li>
				<li><hk:a href="/msg/pvtlist.do">站内私信</hk:a></li>
				<li><hk:a href="/user/set/set.do">设置</hk:a></li>
				<li><hk:a href="/more.do">更多功能</hk:a></li>
				<li><hk:a href="/help.do">帮助</hk:a></li>
				<li><hk:a href="/logout.do">退出</hk:a></li>
			</ul>
		</div>
	</c:if>
</c:if>
<%String msg = MessageUtil.getMessage(request);
DelInfo delInfo=HkWebUtil.getDelInfo(request);
Long newLabaId=(Long)HkWebUtil.getSessionValue(session,"newLabaId");
HkWebUtil.removeSessionValue(session,"newLabaId");
Boolean lesschar=(Boolean)HkWebUtil.getSessionValue(session,"lesschar");
HkWebUtil.removeSessionValue(session,"lesschar");
if(delInfo!=null){
	request.setAttribute("delInfo",delInfo);
}
int count =0;
int noticeCount=0;%>
<c:if test="${userLogin}">
<%count=HkWebUtil.getNewMsgCount(request);
noticeCount=HkWebUtil.getNoReadNoticeCount(request);%>
</c:if>
<c:set var="sysmsg" value="<%=msg%>" /><c:set var="newmsgcount" value="<%=count%>" />
<c:set var="noticeCount" value="<%=noticeCount%>" /><c:set var="newLabaId" value="<%=newLabaId%>" />
<c:if test="${not empty sysmsg || newmsgcount>0 || noticeCount>0}">
	<div class="warn">
		<c:if test="${not empty sysmsg}">
			<div>${sysmsg}
			<%if(delInfo!=null){%> <hk:a href="/undo.do?type=${delInfo.type }&opuserId=${delInfo.opuserId}&optime=${delInfo.optime }&${delInfo.otherParam }">撤销</hk:a><%} %>
			<%if(newLabaId!=null){ %> <hk:a href="/laba/laba.do?labaId=${newLabaId}">查看</hk:a>
			<%if(lesschar!=null && lesschar){ %> <hk:a href="/user/set/set_lesschar.do">8个字符的秘密</hk:a><%} %>
			<%} %>
			</div>
		</c:if>
		<c:if test="${newmsgcount>0}"><div>您有${newmsgcount }封未读私信,<hk:a href="/msg/pvt_last.do">点击查看</hk:a></div></c:if>
		<c:if test="${noticeCount>0}"><div>您有${noticeCount }个新通知,<hk:a href="/notice/notice.do">点击查看</hk:a></div></c:if>
	</div>
</c:if>