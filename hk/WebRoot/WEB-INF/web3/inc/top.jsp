<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.frame.util.MessageUtil"%><%@page import="com.hk.svr.pub.Err"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Pcity"%><%@page import="com.hk.frame.util.ServletUtil"%><%@page import="com.hk.web.util.HkWebUtil"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
String msg = MessageUtil.getMessage(request);
JspDataUtil.loadCmpZoneInfo(request);%>
<script type="text/javascript">
var path="<%=path%>";
var user_login=false;
var ERR_CODE_USER_NOT_LOGIN=<%=Err.USER_NOT_LOGIN %>
<c:if test="${loginUser!=null}">
user_login=true;
var current_url=document.location;
</c:if>
</script>
<c:if test="${parentId==1}">
	<c:set var="p1">active</c:set>
</c:if>
<c:if test="${parentId==2}">
	<c:set var="p2">active</c:set>
</c:if>
<c:if test="${parentId==3}">
	<c:set var="p3">active</c:set>
</c:if>
<c:if test="${parentId==4}">
	<c:set var="p4">active</c:set>
</c:if>
<div class="user_mod">
	<div class="f_l">
		<c:if test="${loginUser!=null}">
			你好，<a href="<%=path %>/home_web.do?userId=${loginUser.userId }">${loginUser.nickName }！</a> | <a href="<%=path %>/home_web.do?userId=${loginUser.userId }">我的主页</a> | <a href="<%=path %>/msg/pvtlist_web.do">私信</a> | <a href="<%=path %>/user/set/set_web.do">设置</a> | <a href="<%=path %>/logout_web.do">退出</a>
		</c:if>
		<c:if test="${loginUser==null}">
			<a href="<%=path %>/reg_toregweb.do">注册或登录</a> | <a href="<%=path %>/help.do">帮助</a>
		</c:if>
		<span class="splir-all">${sys_zone_pcity.name }</span>[<a href="#" id="id_chg_pcity">切换城市</a>]
		<div class="hovertip cmp_zone" id="id_chg_pcity_hover">
			<div class="mod">
				<div class="cont">
					<ul>
						<c:forEach var="cmp_zone" items="${cmpzonepcitylist}">
						<li><a href="<%=path%>/index_chgcity.do?pcityId=${cmp_zone.cityId}">${cmp_zone.name }</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="f_r">
		<a href="<%=path%>/op/orderform.do">我的订单</a> | 
		<a href="<%=path%>/shoppingcard.do">购物车内有<strong>${httpshoppingcard.totalCount}</strong>件商品</a>
		<c:if test="${httpshoppingcard.totalCount>0}">，<a href="<%=path%>/shoppingcard.do">马上确认</a></c:if>
	</div>
	<div class="clr"></div>
</div>
<div class="top_nav">
	<div class="nav-1">
		<ul>
			<li>
				<a class="logo" href="<%=path%>/index_web.do">火酷</a>
			</li>
			<li class="${p1 }">
				<a href="<%=path%>/cmp_pklist.do?pcityId=${pcityId }&parentId=1&${url_add }">吃喝</a>
			</li>
			<li class="${p2 }">
				<a href="<%=path%>/cmp_pklist.do?pcityId=${pcityId }&parentId=2&${url_add }">玩乐</a>
			</li>
			<li class="${p3 }">
				<a href="<%=path%>/cmp_pklist.do?pcityId=${pcityId }&parentId=3&${url_add }">购物</a>
			</li>
			<li class="${p4 }">
				<a href="<%=path%>/cmp_pklist.do?pcityId=${pcityId }&parentId=4&${url_add }">生活服务</a>
			</li>
			<li>
				<a href="<%=path%>/laba_list.do">喇叭</a>
			</li>
			<li>
				<a href="#">宝贝</a>
			</li>
			<li class="end">
				<div class="search">
					<div class="left">
					</div>
					<div class="mid">
						<form style="margin: 0px; padding: 0px;">
							<input type="text" class="inputfield" name="key" />
						</form>
					</div>
					<div class="right">
					</div>
					<div class="clr">
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="clr"></div>
</div>
<%if (msg != null) { %>
<div class="alert_info">
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="l">信息提示</td>
		<td class="r"><%=msg %></td>
	</tr>
</table>
</div>
<br class="linefix"/>
<%}%>