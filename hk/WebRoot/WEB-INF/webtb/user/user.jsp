<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbUser.show_nick }
</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="${tbUser.introRow }" />
<meta name="keywords"  content="${tbUser.show_nick }|${tbUser.location }|顾问家" />
</c:set><c:set var="html_body_content" scope="request">
<div class="user_info">
	<div class="f_l">
		<div>
			<div class="f_l" style="margin-right:20px;">
				<c:if test="${tbUser.loginFromSina}">
					<a target="_blank" href="http://t.sina.com.cn/${sina_tbUserApi.uid }"><img src="${tbUser.pic_url_80 }"/></a>
				</c:if>
				<c:if test="${!tbUser.loginFromSina}">
					<img src="${tbUser.pic_url_80 }"/>
				</c:if>
			</div>
			<div class="f_l">
				<c:if test="${tbUser.loginFromSina}">
					<h1 style="display: inline;"><a target="_blank" href="http://t.sina.com.cn/${sina_tbUserApi.uid }">${tbUser.show_nick }</a></h1>
					<c:if test="${tbFollow!=null && login_user.userid!=userid}">√ 已关注<span class="split-l"><a id="_delfollow" href="javascript:delfollow(${userid })">取消</a></span></c:if><br/>
					${tbUser.sinaLocation }<br/>
				</c:if>
				<c:if test="${!tbUser.loginFromSina}">
					<h1 style="display: inline;">${tbUser.show_nick }</h1>
					<c:if test="${tbFollow!=null && login_user.userid!=userid}">√ 已关注<span class="split-l"><a id="_delfollow" href="javascript:delfollow(${userid })">取消</a></span></c:if><br/>
					${tbUser.location }<br/>
				</c:if>
				<c:if test="${tbFollow==null && login_user!=null && login_user.userid!=userid}">
				<input id="_addfollow" type="button" class="btn" value="加关注" onclick="addfollow(${userid })"/>
				</c:if>
			</div>
			<c:if test="${tbUser.sinaVerified}"><div class="f_l split-l">
			<a target="_blank" href="http://t.sina.com.cn/pub/verified"><img src="${ctx_path }/webtb/img/sina_vip.gif"/></a>
			</div></c:if>
			<div class="clr"></div>
		</div>
		<div>
			<c:if test="${tbUser.loginFromSina}">
				<div class="log2">
					<div class="box">
						<a target="_blank" href="http://t.sina.com.cn/${sina_tbUserApi.uid }/follow"><span class="num">${tbUser.sinaFriend_count }</span>
						<span class="tit">关注</span></a>
					</div>
					<div class="box">
						<a target="_blank" href="http://t.sina.com.cn/${sina_tbUserApi.uid }/fans"><span class="num">${tbUser.sinaFans_count }</span>
						<span class="tit">粉丝</span></a>
					</div>
					<div class="box">
						<a target="_blank" href="http://t.sina.com.cn/${sina_tbUserApi.uid }"><span class="num">${tbUser.sinaWeibo_count }</span>
						<span class="tit">微博</span></a>
					</div>
					<div class="clr"></div>
				</div>
			</c:if>
			<c:if test="${!tbUser.loginFromSina}">
				<div class="log2">
					<div class="box">
						<a href="${ctx_path }/tb/user_friend?userid=${userid}"><span class="num">${tbUser.friend_count }</span><br/>
						<span class="tit">关注</span></a>
					</div>
					<div class="box">
						<a href="${ctx_path }/tb/user_fans?userid=${userid}"><span class="num">${tbUser.fans_count }</span><br/>
						<span class="tit">粉丝</span></a>
					</div>
					<div class="clr"></div>
				</div>
			</c:if>
		</div>
	</div>
	<div class="f_r" style="width: 400px">
		<div class="statbox">
			<div class="title">已有</div>
			<div class="content"><a href="${ctx_path }/tb/user_holditem?userid=${userid}">${tbUser.item_hold_count }</a></div>
		</div>
		<div class="statbox">
			<div class="title">想买</div>
			<div class="content"><a href="${ctx_path }/tb/user_wantitem?userid=${userid}">${tbUser.item_want_count }</a></div>
		</div>
		<div class="statbox">
			<div class="title">评价</div>
			<div class="content"><a href="${ctx_path }/tb/user_itemcmt?userid=${userid}">${tbUser.item_cmt_count }</a></div>
		</div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
</div>
<div class="u_l">
	<div id="cmt" class="inactive_tips_tab">
		<a href="${ctx_path }/tb/user_itemcmt?userid=${userid}" onclick="loaditemcmt('cmt');return false;">评价的宝贝</a>
	</div>
	<div id="want" class="inactive_tips_tab">
		<a href="${ctx_path }/tb/user_wantitem?userid=${userid}" onclick="loadwantitem('want');return false;">想买的宝贝</a>
	</div>
	<div id="hold" class="inactive_tips_tab">
		<a href="${ctx_path }/tb/user_holditem?userid=${userid}" onclick="loadholditem('hold');return false;">拥有的宝贝</a>
	</div>
	<div class="clr"></div>
	<div id="user_item_area" class="listbox"></div>
</div>
<div class="u_r">
<c:if test="${login_user.userid==userid}">
	<hk:actioninvoke mappinguri="/tb/user_getfriendnews"/>
	<c:if test="${fn:length(newsvolist)>0}">
		<div class="mod">
			<div class="mod_title">好友动态
				<c:if test="${more_news}"><a class="more" href="${ctx_path }/tb/user_friendnews">更多</a></c:if>
			</div>
			<div class="mod_content">
				<ul class="rowlist">
					<c:forEach var="newsvo" items="${newsvolist}"><c:set var="vo" value="${newsvo}" scope="request"/>
						<li><jsp:include page="../news/new_inc_${newsvo.last.ntype}.jsp"></jsp:include></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</c:if>
</c:if>
<c:if test="${login_user.userid!=userid}">
	<hk:actioninvoke mappinguri="/tb/user_getusernews"/>
	<c:if test="${fn:length(newsvolist)>0}">
		<div class="mod">
			<div class="mod_title">${tbUser.show_nick }的动态
				<c:if test="${more_news}"><a class="more" href="${ctx_path }/tb/user_news?userid=${userid}">更多</a></c:if>
			</div>
			<div class="mod_content">
				<ul class="rowlist">
					<c:forEach var="newsvo" items="${newsvolist}"><c:set var="vo" value="${newsvo}" scope="request"/>
						<li><jsp:include page="../news/new_inc_${newsvo.last.ntype}.jsp"></jsp:include></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</c:if>
</c:if>
	<c:if test="${fn:length(followlist)>0}">
		<div class="mod">
			<div class="mod_title">关注的朋友</div>
			<div class="mod_content">
			<ul class="headlist3">
				<c:forEach var="f" items="${followlist}">
					<li><a href="/p/${f.friendid }"><img src="${f.friend.pic_url_80 }" title="${f.friend.show_nick }"/></a></li>
				</c:forEach>
			</ul>
			<div class="clr"></div>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(askedlist)>0}">
		<div class="mod">
			<div class="mod_title">提出的问题
			<a class="more" href="${ctx_path }/tb/user_asked?userid=${userid}">更多</a>
			</div>
			<div class="mod_content">
			<ul class="rowlist">
				<c:forEach var="user_ask" items="${askedlist}">
					<li><a href="${ctx_path }/tb/ask?aid=${user_ask.aid}">${user_ask.tbAsk.title }</a></li>
				</c:forEach>
			</ul>
			<div class="clr"></div>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(answeredlist)>0}">
		<div class="mod">
			<div class="mod_title">回答的问题
			<a class="more" href="${ctx_path }/tb/user_answered?userid=${userid}">更多</a>
			</div>
			<div class="mod_content">
			<ul class="rowlist">
				<c:forEach var="user_ask" items="${answeredlist}">
					<li><a href="${ctx_path }/tb/ask?aid=${user_ask.aid}">${user_ask.tbAsk.title }</a></li>
				</c:forEach>
			</ul>
			</div>
		</div>
	</c:if>
</div>
<div class="clr"></div>
<script type="text/javascript" src="${ctx_path }/webtb/js/itemcmt.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.ulblock li').bind('mouseenter', function(){
		$(this).addClass('enter');
		$('#action_' + $(this).attr('idx')).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
		$('#action_' + $(this).attr('idx')).css('display', 'none');
	});
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
var base_url = '${ctx_path}/tb/user_';
function activetab(id){
	getObj('cmt').className='inactive_tips_tab';
	getObj('hold').className='inactive_tips_tab';
	getObj('want').className='inactive_tips_tab';
	getObj(id).className='active_tips_tab';
}
function loaditemcmt(){
	activetab('cmt');
	setHtml('user_item_area', "数据加载中 ... ...");
	doAjax(base_url + 'loaditemcmt?userid=${userid}', function(data){
		setHtml('user_item_area', data);
		$('ul.ulblock li').bind('mouseenter', function(){
			$(this).addClass('enter');
			$('#action_' + $(this).attr('idx')).css('display', 'block');
		}).bind('mouseleave', function(){
			$(this).removeClass('enter');
			$('#action_' + $(this).attr('idx')).css('display', 'none');
		});
	});
}

function loadholditem(){
	activetab('hold');
	setHtml('user_item_area', "数据加载中 ... ...");
	doAjax(base_url + 'loadholditem?userid=${userid}', function(data){
		setHtml('user_item_area', data);
		$('ul.ulblock li').bind('mouseenter', function(){
			$(this).addClass('enter');
			$('#action_' + $(this).attr('idx')).css('display', 'block');
		}).bind('mouseleave', function(){
			$(this).removeClass('enter');
			$('#action_' + $(this).attr('idx')).css('display', 'none');
		});
	});
}

function loadwantitem(){
	activetab('want');
	setHtml('user_item_area', "数据加载中 ... ...");
	doAjax(base_url + 'loadwantitem?userid=${userid}', function(data){
		setHtml('user_item_area', data);
	});
}
var follow_to_sina="false";
<c:if test="${tbUser.loginFromSina}">
follow_to_sina="true";
</c:if>
function addfollow(userid){
	addGlass('_addfollow', true);
	doAjax('${ctx_path}/tb/user_prvaddfollow?friendid=' + userid+"&follow_to_sina="+follow_to_sina, function(data){
		refreshurl();
	});
}

function delfollow(userid){
	addGlass('_delfollow', true);
	doAjax('${ctx_path}/tb/user_prvdelfollow?friendid=' + userid, function(data){
		refreshurl();
	});
}
loaditemcmt('cmt');
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>