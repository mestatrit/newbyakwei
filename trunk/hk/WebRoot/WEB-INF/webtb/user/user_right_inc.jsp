<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><div class="userinfo">
	<div class="inforow">
		<div class="head">
			<a href="/p/${userid }"><img src="${tbUser.pic_url_80 }" /></a>
		</div>
		<div class="body">
			<a href="/p/${userid }">${tbUser.show_nick }</a><br />
			${tbUser.location }
		</div>
		<div class="clr"></div>
	</div>
	<div class="log">
		<div class="box">
			<a href="${ctx_path }/tb/user_friend?userid=${userid}"><span class="num">${tbUser.friend_count }</span><br /><span class="tit">关注</span></a>
		</div>
		<div class="box">
			<a href="${ctx_path }/tb/user_fans?userid=${userid}"><span class="num">${tbUser.fans_count }</span><br /><span class="tit">粉丝</span></a>
		</div>
		<div class="box end">
			<a href="${ctx_path }/tb/user_holditem?userid=${userid}"><span class="num">${tbUser.item_count }</span><br /><span class="tit">宝贝</span></a>
		</div>
		<div class="clr"></div>
	</div>
</div>