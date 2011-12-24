<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><hk:actioninvoke mappinguri="/tb/ask_noresolved"/>
<c:if test="${fn:length(noresolvedasklist)>0}">
<div class="mod">
	<div class="mod_title">等待回答的问题</div>
	<div class="mod_content">
		<ul class="rowlist">
		<c:forEach var="ask" items="${noresolvedasklist}">
			<li>
			<a class="nick" href="/p/${ask.userid }"><img src="${ask.tbUser.pic_url_48 }" width="16"/>${ask.tbUser.show_nick }：</a><a href="${ctx_path }/tb/ask?aid=${ask.aid}">${ask.title }</a>
			</li>
		</c:forEach>
		</ul>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.rowlist li').bind('mouseenter', function(){
		$(this).addClass('enter');
	}).bind('mouseleave', function(){
		$(this).removeClass('enter');
	});
});
</script>
</c:if>