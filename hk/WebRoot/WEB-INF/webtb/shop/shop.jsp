<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><c:set var="html_head_title" scope="request">${tbShop.title }
</c:set><c:set var="html_head_value">
<meta name="description" content="${tbShop.title }" />
<meta name="keywords"  content="${tbShop.title }|顾问家" />
</c:set><c:set var="html_body_content" scope="request">
<div class="shop_l">
	<div class="mod">
		<div class="mod_content">
			<p class="b">${tbShop.title }</p>
			<div class="shop_logo"><img src="${tbShop.pic_url }" /></div>
			<div style="width: 200px;overflow: hidden;">${tbShop.intro }</div>
		</div>
	</div>
</div>
<div class="shop_r">
	<div class="mod">
		<div class="mod_title">大家分享的本店铺商品</div>
		<div class="mod_content">
			<div id="items" style="height: 350px;overflow: hidden;">
				<c:forEach var="cmt" items="${cmtlist}">
					<div class="itemcmt">
						<div class="i_head">
							<a href="/p/${cmt.userid }"><img src="${cmt.tbUser.pic_url_48 }" /></a>
						</div>
						<div class="i_img">
							<a href="${ctx_path }/tb/item?itemid=${cmt.itemid}"><img src="${cmt.tbItem.pic_url }_60x60.jpg" /></a>
						</div>
						<div class="body"><a href="/p/${cmt.userid }">${cmt.tbUser.show_nick }</a> <span class="split-l"><fmt:formatDate value="${cmt.create_time}" pattern="yyyy-MM-dd HH:mm"/></span>
						<c:if test="${cmt.score>0}"><span class="split-l"><img src="${ctx_path }/webtb/img/star/star${cmt.score}.gif"/></span></c:if><br/>
						<hk:limitlen len="70" value="${cmt.content }" tohtml="true"/></div>
						<div class="clr"></div>
					</div>
				</c:forEach>
			</div>
			<a class="more2" href="http://shop${sid }.taobao.com" target="_blank">直接进入淘宝原店铺浏览更多商品</a>
		</div>
	</div>
</div>
<div class="clr"></div>
<c:if test="${fn:length(cmtlist)>5}">
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