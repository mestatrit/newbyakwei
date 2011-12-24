<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbUser.show_nick }的粉丝
</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="${tbUser.show_nick }的粉丝" />
<meta name="keywords"  content="${tbUser.show_nick }|顾问家" />
</c:set><c:set var="html_body_content" scope="request">
	<div class="pl">
		<div class="mod">
			<div class="mod_title">${tbUser.show_nick }的粉丝</div>
			<div class="mod_content">
				<ul class="friendlist">
					<c:forEach var="fans" items="${list}" varStatus="idx">
						<li <c:if test="${idx.index%2!=0}">class="even"</c:if>>
							<div class="head"><a href="/p/${fans.fansid }"><img src="${fans.fans.pic_url_80 }" /></a></div>
							<div class="body">
								<a href="/p/${fans.fansid }">${fans.fans.show_nick }</a><br />
								${fans.fans.location }<br />
								${fans.fans.intro }
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
				<div>
				<c:set var="page_url" scope="request">${ctx_path}/tb/user_fans?userid=${userid}</c:set>
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