<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbUser.show_nick }评价的宝贝
</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="${tbUser.show_nick }评价的宝贝" />
<meta name="keywords"  content="${tbUser.show_nick }评价的宝贝|顾问家" />
</c:set><c:set var="html_body_content" scope="request">
	<div class="pl">
		<div class="mod">
			<div class="mod_title">${tbUser.show_nick }评价的宝贝
			<a class="more" href="/p/${userid }">返回</a>
			</div>
			<div class="mod_content">
				<jsp:include page="itemdata2_inc.jsp"></jsp:include>
				<div>
				<c:set var="page_url" scope="request">${ctx_path}/tb/user_itemcmt?userid=${userid}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="pr">
	<jsp:include page="user_right_inc.jsp"></jsp:include>
	</div>
	<div class="clr"></div>
<script type="text/javascript" src="${ctx_path }/webtb/js/itemcmt.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.ulblock2 li').bind('mouseenter', function(){
		$(this).addClass('enter');
		$('#action_' + $(this).attr('idx')).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
		$('#action_' + $(this).attr('idx')).css('display', 'none');
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>