<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.web.util.HkWebUtil"%><%@page import="com.hk.bean.User"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%User user=HkWebUtil.getLoginUser(request);%>
<c:if test="${!showMode.touchMode}">
	<%if(user==null){ %>
	<div class="menu menu-top"><a name="bottom" href="#top">↑</a>|<hk:a href="/index_h.do">首页</hk:a>|<hk:a href="/tologin.do">登录</hk:a></div>
	<%}else{ %>
	<hr/>
	<div class="menu2 menu-top">
		<div class="hang"><a name="bottom" href="#top">↑</a>|<hk:a href="/box/market.do?cityId=${sys_zone_pcityId}&auto=1">开宝箱</hk:a></div>
		<div class="hang"><hk:a href="/op/user_findcmp.do">报到</hk:a><span class="ruo s">(告诉我们你在哪里）</span></div>
		<div class="hang"><hk:a href="/op/user_friendcheckin.do">大家在哪</hk:a><span class="ruo s">(你的朋友在哪)</span></div>
		<div class="hang"><hk:a href="/index_h.do">首页</hk:a></div>
		<c:if test="${userLogin}"><div class="hang"><hk:a href="/op/award_prize.do">宝库</hk:a></div></c:if>
		<div class="hang"><hk:a href="/square.do">喇叭</hk:a><span class="ruo s">(一句话分享你和你的世界)</span></div>
		<div class="hang"><hk:a href="/e/cmp_list.do?cityId=${sys_zone_pcityId}">搜索足迹</hk:a><span class="ruo s">(搜索并分享你去过的地方)</span></div>
		<div class="hang"><hk:a href="/user/list2.do">找朋友</hk:a><span class="ruo s">(关注朋友获得他们的踪迹)</span></div>
		<div class="hang"><hk:a href="/index_changecity2.do">切换地区</hk:a><span class="ruo s">(当前位置是${sys_zone_pcity.city.city })</span></div>
		<div class="hang"><hk:a href="/invite/invite_toinvite.do">邀请</hk:a><span class="ruo s">(邀请人将获得特殊徽章)</span></div>
		<div class="hang"><hk:a href="/msg/pvtlist.do">站内私信</hk:a></div>
		<div class="hang"><hk:a href="/home.do?userId=${loginUser.userId}">我的主页</hk:a></div>
		<div class="hang"><hk:a href="/user/set/set.do">设置</hk:a><span class="ruo s">(修改个人信息、密码等)</span></div>
		<div class="hang"><hk:a href="/more.do">更多功能</hk:a><span class="ruo s">(更多功能等待你发现)</span></div>
		<div class="hang"><a href="/index">切换到电脑标准版</a></div>
	</div>
	<%} %>
</c:if>