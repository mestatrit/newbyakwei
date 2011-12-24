<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">首页</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="顾问家的目的通过有公信力和影响力的人来让那些货真价实的淘宝商家和商品浮出水平，并对推荐者给与荣誉和奖励" />
<meta name="keywords"  content="顾问家|淘宝|商品|问答" />
</c:set><c:set var="html_body_content" scope="request">
	<div class="pl">
		<div class="mod kuactor">
			<div class="kuimg">
				<c:forEach var="item" items="${indexlist}">
					<div id="bigimg_${item.itemid }" style="display: none;">
						<a href="${ctx_path }/tb/item?itemid=${item.itemid}"><img src="${item.pic_url }_310x310.jpg" /></a>
					</div>
				</c:forEach>
			</div>
			<ul class="kulist">
				<c:forEach var="item" items="${indexlist}" varStatus="idx">
					<li idx="${item.itemid }" <c:if test="${idx.last}">class="end"</c:if>>
						<div id="arrow_${item.itemid }" class="arrow"></div>
						<div class="inpad">
							<div class="head">
								<a href="${ctx_path }/tb/item?itemid=${item.itemid}"><img src="${item.pic_url }_60x60.jpg" /> </a>
							</div>
							<div class="info">
								<a href="${ctx_path }/tb/item?itemid=${item.itemid}">${item.title }</a>
								<br />
								价格：${item.price }元
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
			<div class="clr"></div>
		</div>
		<c:if test="${fn:length(huolist)>0}">
			<div class="mod">
				<div class="mod_title">啥最火</div>
				<div class="mod_content">
					<ul class="headlist3">
						<c:forEach var="item" items="${huolist}">
						<li><a href="/tb/item?itemid=${item.itemid }"><img src="${item.pic_url }_60x60.jpg" title="${item.title }"/></a></li>
						</c:forEach>
					</ul>
					<div class="clr"></div>
				</div>
			</div>
		</c:if>
		<c:if test="${fn:length(kulist)>0}">
			<div class="mod">
				<div class="mod_title">啥最酷</div>
				<div class="mod_content">
					<ul class="headlist3">
						<c:forEach var="item" items="${kulist}">
						<li><a href="/tb/item?itemid=${item.itemid }"><img src="${item.pic_url }_60x60.jpg" title="${item.title }"/></a></li>
						</c:forEach>
					</ul>
					<div class="clr"></div>
				</div>
			</div>
		</c:if>
		<c:if test="${fn:length(newcmtlist)>0}">
			<div class="mod">
				<div class="mod_title">最新点评</div>
				<div class="mod_content">
					<div id="items" style="height: 850px;overflow: hidden;">
						<c:forEach var="cmt" items="${newcmtlist}">
							<div class="itemcmt">
								<div class="i_head"><a href="/p/${cmt.userid }"><img src="${cmt.tbUser.pic_url_48 }"/></a></div>
								<div class="i_img"><a href="${ctx_path }/tb/item?itemid=${cmt.itemid }"><img src="${cmt.tbItem.pic_url }_60x60.jpg"/></a></div>
								<div class="body"><a href="/p/${cmt.userid }">${cmt.tbUser.show_nick}</a><span class="split-l"><fmt:formatDate value="${cmt.create_time}" pattern="yyyy-MM-dd HH:mm"/></span>
								<c:if test="${cmt.score>0}"><span class="split-l"><img src="${ctx_path }/webtb/img/star/star${cmt.score}.gif"/></span></c:if><br/>
								<hk:limitlen len="63" value="${cmt.content }" tohtml="true" onerow="true"/></div>
								<div class="clr"></div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<div class="pr">
		<jsp:include page="index_right_inc.jsp"></jsp:include>
	</div>
	<div class="clr"></div>
	<script type="text/javascript">
	$(document).ready(function(){
		var kulist=$('ul.kulist li');
		$('ul.kulist li').bind('mouseenter', function(){
			showbigimg(this);
		});
		function showbigimg(li_obj){
			kulist.each(function(i){
				$(this).css('background-color', '#ffffff');
				var idx = $(this).attr('idx');
				$('#arrow_' + idx).css('display', 'none');
				$('#bigimg_' + idx).css('display', 'none');
			});
			$(li_obj).css('background-color', '#e4e4e4');
			var idx = $(li_obj).attr('idx');
			$('#bigimg_' + idx).css('display', 'block');
			$('#arrow_' + idx).css('display', 'block');
		}
		showbigimg($("ul.kulist li:first-child"));
	});
</script>
<c:if test="${fn:length(newcmtlist)>5}">
<script type="text/javascript">
var delay = 5000;
function shift() {
	$('.itemcmt:last-child').remove().css('display', 'none').prependTo('#items');
	$('.itemcmt:first-child').slideDown(1000);
	$('.itemcmt:last-child').css('display', 'none');
	setTimeout('shift()', delay);
}
$(document).ready(function() {
	setTimeout('shift()', delay);
});
</script>
</c:if>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>