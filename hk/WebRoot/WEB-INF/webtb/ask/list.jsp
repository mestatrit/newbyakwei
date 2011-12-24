<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">${w }
</c:set><c:set var="html_head_value">
<meta name="description" content="${w }" />
<meta name="keywords"  content="${w }|顾问家" />
</c:set><c:set var="html_body_content" scope="request">
<div class="pl">
	<div class="mod">
		<div class="mod_content">
			<form id="askfrm" method="get" action="${ctx_path }/tb/ask_q">
				输入要搜索的关键词，多个关键词用空格区分<br/>
				<input type="text" class="text" name="w" value="${w }" style="width:420px"/>
				<input type="submit" class="btn split-r" value="搜索答案" name="search"/>
			</form>
		</div>
	</div>
	<div class="mod">
	<c:if test="${search_show}">
		<div class="mod_title">搜索${w }的结果</div>
	</c:if>
	<c:if test="${pub_show}">
		<div class="mod_title">所有问题</div>
	</c:if>
		<div class="mod_content">
			<ul class="itemcmtlist">
				<c:if test="${fn:length(list)==0 && page==1}"><p class="b align_c">暂时还没有人提问</p></c:if>
				<c:if test="${fn:length(list)==0 && page>1}"><p class="b align_c">本页没有数据</p></c:if>
				<c:forEach var="ask" items="${list}">
				<li>
					<div class="head">
						<a href="/p/${ask.userid }"><img src="${ask.tbUser.pic_url_48 }"/><br/>${ask.tbUser.show_nick }</a>
					</div>
					<div class="body">
						<p><c:if test="${ask.resolved}"><span class="b">(已解决)</span></c:if><a class="b" href="${ctx_path }/tb/ask?aid=${ask.aid}">${ask.title }</a> <c:if test="${ask.answer_num>0}"><span class="ruo">${ask.answer_num }个回答</span></c:if></p>
						<c:if test="${ask.resolved}">
							<div><span class="b">最佳答案</span>：<hk:limitlen len="100" onerow="true" tohtml="true" value="${ask.tbAnswer.content }"/> </div>
							<c:if test="${not empty ask.tbAnswer.resolve_content}">
								<c:forEach var="item" items="${ask.tbAnswer.tbAnswerItemList}">
									<div class="picbox">
									<a target="_blank" href="${ctx_path }/tb/ask_item?ansid=${ask.tbAnswer.ansid}&id=${item.id}"><img src="${item.pic_url }_60x60.jpg"/></a>
									<p>￥${item.price }</p>
									</div>
								</c:forEach>
								<div class="clr"></div>
								<div class="row">合计：${ask.tbAnswer.expression } 元</div>
							</c:if>
						</c:if>
						<c:if test="${!ask.resolved}"><hk:limitlen len="100" value="${ask.content }" tohtml="true" onerow="true"/></c:if>
					</div>
					<div class="clr"></div>
				</li>
				</c:forEach>
			</ul>
			<div>
			<c:if test="${search_show}">
			<c:set var="page_url" scope="request">${ctx_path}/tb/ask_list?w=${enc_w}</c:set>
			</c:if>
			<c:if test="${pub_show}">
			<c:set var="page_url" scope="request">${ctx_path}/tb/ask_list</c:set>
			</c:if>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
			<c:if test="${not empty w}">
				<div class="row b" style="font-size: 16px">
				我要提问：<a href="${ctx_path }/tb/ask_prvask?title=${enc_w}">创建关于${w }的问题</a>
				</div>
			</c:if>
		</div>
	</div>
</div>
<div class="pr"><jsp:include page="../inc/ask_right_inc.jsp"></jsp:include>
</div>
<div class="clr"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.itemcmtlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>