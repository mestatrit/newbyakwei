<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbUser.show_nick }关注的人
</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="${tbUser.show_nick }关注的人" />
<meta name="keywords"  content="${tbUser.show_nick }|顾问家" />
</c:set><c:set var="html_body_content" scope="request">
	<div class="pl">
		<div class="mod">
			<div class="mod_title">${tbUser.show_nick }关注的人</div>
			<div class="mod_content">
				<ul class="friendlist">
					<c:forEach var="friend" items="${list}" varStatus="idx">
						<li <c:if test="${idx.index%2!=0}">class="even"</c:if>>
							<div class="head"><a href="/p/${friend.friendid }"><img src="${friend.friend.pic_url_80 }" /></a></div>
							<div class="body">
								<a href="/p/${friend.friendid }">${friend.friend.show_nick }</a><br />
								${friend.friend.location }<br />
								${friend.friend.intro }
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
				<div>
				<c:set var="page_url" scope="request">${ctx_path}/tb/user_friend?userid=${userid}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="pr">
		<jsp:include page="user_right_inc.jsp"></jsp:include>
	</div>
	<div class="clr"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.friendlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>