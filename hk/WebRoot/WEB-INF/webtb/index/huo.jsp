<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">啥最火</c:set><c:set var="html_head_value" scope="request">
<meta name="description" content="顾问家的目的通过有公信力和影响力的人来让那些货真价实的淘宝商家和商品浮出水平，并对推荐者给与荣誉和奖励" />
<meta name="keywords"  content="顾问家|淘宝|商品|问答" />
</c:set><c:set var="html_body_content" scope="request">
<div class="pl">
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
</div>
<div class="pr">
	<jsp:include page="index_right_inc.jsp"></jsp:include>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>