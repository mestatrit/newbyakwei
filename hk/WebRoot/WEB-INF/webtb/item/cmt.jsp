<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbItem.title }
</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="${tbItem.title }" />
<meta name="keywords"  content="${tbItem.title }|${tbItemCat.name }|${tbUser.location }|顾问家" />
<script type="text/javascript" src="${ctx_path }/webtb/js/hovertip.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  window.setTimeout(hovertipInit, 1);
   });
</script>
</c:set><c:set var="html_body_content" scope="request">
<hk:actioninvoke mappinguri="/tb/item_checkusersysadmin"/>
<div class="pl">
	<div class="mod">
		<div class="mod_content">
			<ul class="itemcmtlist">
				<li id="li_${cmt.cmtid }" idx="${cmt.cmtid }" status="0">
					<div class="head">
						<a href="/p/${cmt.userid }"><img src="${cmt.tbUser.pic_url_80 }" width="60" height="60"/></a>
						<c:if test="${cmt.tbUser.sinaVerified}"><br/><a target="_blank" href="http://t.sina.com.cn/pub/verified"><img src="${ctx_path }/webtb/img/sina_vip.gif" title="新浪微博认证用户"/></a></c:if>
					</div>
					<div class="body">
						<div class="body_head">
							<div class="nick"><a href="/p/${cmt.userid }">${cmt.tbUser.show_nick }</a></div>
							<c:if test="${cmt.score>0 && cmt.score<6}">
							<div class="score">
							<img src="${ctx_path }/webtb/img/star/star${cmt.score }.gif"/>
							</div>
							</c:if>
							<div class="time"><fmt:formatDate value="${cmt.create_time}" pattern="yyyy-MM-dd HH:mm"/> </div>
							<div class="clr"></div>
						</div>
						<div>${cmt.content }</div>
						<div id="cmt_op_${cmt.cmtid }" class="op">
							<c:if test="${login_user!=null}">
								<c:if test="${cmt.userid==login_user.userid}">
								<a class="opa" href="javascript:delcmt(${cmt.cmtid })">删除</a>
								<a class="opa" href="javascript:updatecmt(${cmt.cmtid })">修改</a>
								</c:if>
								回复<c:if test="${cmt.reply_count>0}">(${cmt.reply_count })</c:if>
							</c:if>
						</div>
						<div id="reply_area${cmt.cmtid }" status="0" class="row" style="display: none;"></div>
					</div>
					<div class="clr"></div>
				</li>
			</ul>
			<div id="cmt_reply_area_${cmtid }">
				<c:if test="${login_user!=null}">
					我的回复：<span class="ruo">最多500字</span><a name="create_cmt_area"></a><br />
					<form id="replyfrm_${cmtid }" onsubmit="return subreplyfrm(this.id)" method="post" action="${ctx_path }/tb/itemcmt_prvcreatereply" target="hideframe">
						<hk:hide name="cmtid" value="${cmtid}"/>
						<div class="row">
							<textarea id="reply_content_${cmtid }" name="content" onkeyup="keysubreply(${cmtid },event)" style="width: 560px; height: 80px;"></textarea>
							<div class="infowarn" id="info"></div>
						</div>
						<div class="row" align="right" style="width:560px;">
							<input type="submit" class="btn" value="提交回复" />
						</div>
					</form>
				</c:if>
				<c:if test="${login_user==null}">
				</c:if>
			</div>
		</div>
	</div>
	<div class="mod">
		<div class="mod_title">大家的回复</div>
		<div class="mod_content">
			<ul id="ul_replylist_${cmtid }" class="replylist row">
			<li id="beginpos_${cmtid }" class="beginpos"></li>
			<c:forEach var="reply" items="${replylist}">
			<li id="reply_${reply.replyid }">
				<div class="reply_head">
					<a href="/p/${reply.userid }"><img src="${reply.tbUser.pic_url_48 }"/></a>
				</div>
				<div class="reply_body">
					<a href="/p/${reply.userid }">${reply.tbUser.show_nick }</a>：
					${reply.content }
					<span class="split-l ruo"><fmt:formatDate value="${reply.create_time}" pattern="yy-MM-dd HH:mm"/></span>
					<div align="right">
						<c:if test="${login_user.userid==reply.userid}"><a class="split-r" href="javascript:delreply(${reply.replyid })">删除</a></c:if>
						<c:if test="${login_user.userid!=reply.userid}"><a class="split-r" href="javascript:replyuser(${cmtid},${reply.userid },'${reply.tbUser.show_nick }')">回复</a></c:if>
					</div>
				</div>
				<div class="clr"></div>
			</li>
			</c:forEach>
			</ul>
			<div>
			<c:set var="page_url" scope="request">${ctx_path}/tb/itemcmt?cmtid=${cmtid}</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<div class="pr">
	<div class="mod">
		<div class="fr">
		<script type="text/javascript">
		obj_width=265;
		</script>
		<jsp:include page="../inc/share.jsp"></jsp:include>
		</div>
		<div class="clr"></div>
	</div>
	<jsp:include page="loaditemdata_inc.jsp"></jsp:include>
</div>
<div class="clr"></div>
<script type="text/javascript" src="${ctx_path }/webtb/js/item.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.replylist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>