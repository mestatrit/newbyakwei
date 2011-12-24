<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${tbUser.show_nick }提出的问题
</c:set><c:set var="html_head_value">
<meta name="description" content="${tbUser.show_nick }提出的问题" />
<meta name="keywords"  content="${tbUser.show_nick }提出的问题|顾问家" />
</c:set><c:set var="html_body_content" scope="request">
<div class="pl">
	<div class="mod">
		<div class="mod_title">${tbUser.show_nick }提出的问题
		<a class="more" href="/p/${userid}">返回</a>
		</div>
		<div class="mod_content">
			<ul class="rowlist">
				<c:if test="${fn:length(list)==0 && page==1}"><p class="b align_c">暂时还没有提问</p></c:if>
				<c:if test="${fn:length(list)==0 && page>1}"><p class="b align_c">本页没有数据</p></c:if>
				<c:forEach var="user_ask" items="${list}">
				<li>
					<div class="body">
						<p><c:if test="${user_ask.tbAsk.resolved}"><span class="b">(已解决)</span></c:if><a class="b" href="${ctx_path }/tb/ask?aid=${user_ask.tbAsk.aid}">${user_ask.tbAsk.title }</a> <c:if test="${user_ask.tbAsk.answer_num>0}"><span class="ruo">${user_ask.tbAsk.answer_num }个回答</span></c:if></p>
						<c:if test="${user_ask.tbAsk.resolved}">
							<div><span class="b">最佳答案</span>：<hk:limitlen len="100" onerow="true" tohtml="true" value="${user_ask.tbAsk.tbAnswer.content }"/> </div>
							<c:if test="${not empty user_ask.tbAsk.tbAnswer.resolve_content}">
								<c:forEach var="item" items="${user_ask.tbAsk.tbAnswer.tbAnswerItemList}">
									<div class="picbox">
									<a target="_blank" href="${ctx_path }/tb/ask_item?ansid=${user_ask.tbAsk.tbAnswer.ansid}&id=${item.id}"><img src="${item.pic_url }_60x60.jpg"/></a>
									<p>￥${item.price }</p>
									</div>
								</c:forEach>
								<div class="clr"></div>
								<div class="row">合计：${user_ask.tbAsk.tbAnswer.expression } 元</div>
							</c:if>
						</c:if>
						<c:if test="${!user_ask.tbAsk.resolved}"><hk:limitlen len="100" value="${user_ask.tbAsk.content }" tohtml="true" onerow="true"/></c:if>
					</div>
					<div class="clr"></div>
				</li>
				</c:forEach>
			</ul>
			<div>
			<c:set var="page_url" scope="request">${ctx_path}/tb/user_answered?userid=${userid}</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<div class="pr"><jsp:include page="../inc/ask_right_inc.jsp"></jsp:include>
</div>
<div class="clr"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>