<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.web.util.HkWebUtil"%><%@page import="com.hk.bean.User"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%User user=HkWebUtil.getLoginUser(request);String menu_top=request.getParameter("menu_top");%><hk:domain domain="<%=HkWebConfig.getWebDomain() %>"/>
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
	<%if(user==null){ %>
	<div id="menu" class="menu">
		<ul id="menu-main">
			<li><hk:a href="/tologin.do">登录</hk:a></li>
		</ul>
	</div>
	<%}else{%>
	<div id="menu" class="menu">
		<ul id="menu-main">
			<li><hk:a href="/index_h.do">城市</hk:a></li>
			<li><hk:a href="/square.do">喇叭</hk:a></li>
			<li><hk:a href="/tag/utlist_mt.do">频道</hk:a></li>
			<li><hk:a href="/e/cmp_nearby.do">足迹</hk:a></li>
			<li><hk:a href="/home.do?userId=${loginUser.userId }">我</hk:a></li>
			<li><a href="#" onclick="return toggleMenu()">更多</a></li>
		</ul>
		<ul id="menu-extras">
			<li><hk:a href="/invite/invite_toinvite.do">邀请</hk:a></li>
			<li><hk:a href="/msg/pvtlist.do">私信</hk:a></li>
			<li><hk:a href="/laba/fav.do?userId=${userId}">收藏</hk:a></li>
			<li><hk:a href="/follow/follow.do?userId=${userId}">好友</hk:a></li>
			<li><hk:a href="/user/list2.do">会员</hk:a></li>
			<li><hk:a href="/s.do">搜索</hk:a></li>
			<li><hk:a href="/user/set/set.do">设置</hk:a></li>
			<li><hk:a href="/help.do">帮助</hk:a></li>
			<li><hk:a href="/more.do">更多功能</hk:a></li>
			<c:if test="${hk_website!=null}">
			<li><a href="${hk_website.url}">返回${hk_website.name}</a></li>
			</c:if>
			<li><hk:a href="/logout.do">退出</hk:a></li>
		</ul>
	</div>
	<%} %>
</c:if>
<c:if test="${!showMode.touchMode}">
	<%if(user==null){ %>
	<div class="menu menu-top"><%if(menu_top!=null){//%><a name="top" href="#bottom">↓</a>|<%}else{//%><a name="bottom" href="#top">↑</a>|<%} %><hk:a href="/tologin.do">登录</hk:a></div>
	<%}else{ %>
	<div class="menu menu-top">
	<%if(menu_top!=null){//%><a name="top" href="#bottom">↓</a>|<%}else{//%><a name="bottom" href="#top">↑</a>|<%} %><hk:a href="/index_h.do">城市</hk:a>|<hk:a href="/square.do">喇叭</hk:a>|<hk:a href="/tag/utlist_mt.do">频道</hk:a>|<hk:a href="/e/cmp_nearby.do">足迹</hk:a>|<b><hk:a href="/home.do?userId=${loginUser.userId}">我</hk:a></b>|<hk:a href="/invite/invite_toinvite.do">邀请</hk:a>|<hk:a href="/msg/pvtlist.do">私信</hk:a>|<hk:a href="/laba/fav.do?userId=${userId}">收藏</hk:a>|<hk:a href="/follow/follow.do?userId=${userId}">好友</hk:a>|<hk:a href="/user/list2.do">会员</hk:a>|<hk:a href="/s.do">搜索</hk:a>|<hk:a href="/user/set/set.do">设置</hk:a>|<hk:a href="/help.do">帮助</hk:a>|<hk:a href="/more.do">更多功能</hk:a><c:if test="${hk_website!=null}">|<a href="${hk_website.url}">返回${hk_website.name}</a></c:if>
	</div>
	<%} %>
</c:if>